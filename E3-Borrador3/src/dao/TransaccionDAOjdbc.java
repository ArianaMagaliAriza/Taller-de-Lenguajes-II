package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Transaccion;

public class TransaccionDAOjdbc implements TransaccionDAO{
	Connection con;
	
	public TransaccionDAOjdbc(Connection con) {
		this.con = con;
	}
	
	public void cargarTransaccion(Transaccion t) {
		try {
			String query = "INSERT INTO transaccion(resumen,fecha_hora,id_usuario) VALUES(?,?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, t.getResumen());
			st.setString(2, t.getFecha());
			st.setInt(3, t.getIdUsuario());
			st.executeUpdate();
			st.close();
		}catch(SQLException e) {
			System.out.println("Error de SQL: "+e.getMessage());
		}
	}
	public List<Transaccion> listarTransacciones(int idUser){
		List<Transaccion> transacciones = new ArrayList<>();
		try {
			String query = "SELECT * FROM transaccion WHERE id_usuario=?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, idUser);
			ResultSet res=st.executeQuery();
			while(res.next()) {
				Transaccion transaccion = new Transaccion(res.getString("RESUMEN"),res.getString("FECHA_HORA"),res.getInt("ID_USUARIO"));
				transacciones.add(transaccion);
			}
			st.close();
		} catch (SQLException e) {
            System.out.print("Error de SQL:"+e.getMessage());
        }
		return transacciones;
	}
}
