package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Transaccion;
import modelo.Usuario;
import servicios.Conexion;

public class TransaccionDAOjdbc implements TransaccionDAO{
	Connection con;
	
	public TransaccionDAOjdbc() {
		this.con = Conexion.getCon();
	}
	
	public void cargarTransaccion(Transaccion t) {
		try {
			String query = "INSERT INTO transaccion(resumen,fecha_hora, id_usuario) VALUES(?,?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, t.getResumen());
			st.setString(2, t.getFecha());
			st.setInt(3, t.getIdUser());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
	            int generatedId = rs.getInt(1);
	            Transaccion tr = new Transaccion(t.getResumen(), t.getFecha(), t.getIdUser());
	            tr.setId(generatedId);}
			st.close();
		}catch(SQLException e) {
			System.out.println("Error de SQL: "+e.getMessage());
		}
	}
}
