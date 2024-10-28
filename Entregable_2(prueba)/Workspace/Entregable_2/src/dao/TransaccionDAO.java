package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.Transaccion;

public class TransaccionDAO {
	Connection con;
	
	public TransaccionDAO(Connection con) {
		this.con = con;
	}
	
	public void cargarTransaccion(Transaccion t) {
		try {
			String query = "INSERT INTO transaccion(resumen,fecha_hora) VALUES(?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, t.getResumen());
			st.setString(2, t.getFecha());
			st.executeUpdate();
			st.close();
		}catch(SQLException e) {
			System.out.println("Error de SQL: "+e.getMessage());
		}
	}
}
