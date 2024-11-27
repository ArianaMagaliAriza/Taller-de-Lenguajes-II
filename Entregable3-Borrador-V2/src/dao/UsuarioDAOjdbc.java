package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Usuario;

public class UsuarioDAOjdbc implements UsuarioDAO{
	 Connection con;

	public UsuarioDAOjdbc(Connection con) {
		this.con = con;
	}
	
	public void cargarUsuario(Usuario usuario) {
		try {
			String query = "INSERT INTO USUARIO(nombres, apellidos, email, password, aceptaTerminos) VALUES(?,?,?,?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, usuario.getNombres());
			st.setString(2, usuario.getApellidos());
			st.setString(3, usuario.getEmail());
			st.setString(4, usuario.getPassword());
			st.setBoolean(5, usuario.getAceptaTerminos());
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error de SQL(cargarUsuario()): " + e.getMessage());
		}
	}
}

