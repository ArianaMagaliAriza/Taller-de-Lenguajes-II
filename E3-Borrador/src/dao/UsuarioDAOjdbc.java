package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.Moneda;
import modelo.Usuario;
import servicios.Conexion;

public class UsuarioDAOjdbc implements UsuarioDAO{
	 Connection con;

	public UsuarioDAOjdbc() {
		this.con = Conexion.getCon();
	}
	
	public boolean usuarioEnBD(String email, String password) {
		boolean existe=false;
		try{
			String query = "SELECT * FROM USUARIO";
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				if((res.getString("email").equals(email))&& (res.getString("password").equals(password))){
					existe=true;
					}
			}
		}catch(SQLException e) {
			System.out.print("Error de SQL: "+e.getMessage());
		}
		return existe;
	}
	
	public void cargarUsuario(Usuario usuario) {
		try {
			String query = "INSERT INTO USUARIO (ID_PERSONA, EMAIL, PASSWORD, ACEPTA_TERMINOS) VALUES(?,?,?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, usuario.getIdPer());
			st.setString(2, usuario.getEmail());
			st.setString(3, usuario.getPassword());
			st.setBoolean(4, usuario.getAceptaTerminos());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
	            int generatedId = rs.getInt(1);
	            Usuario u = new Usuario(usuario.getIdPer(), usuario.getEmail(), usuario.getPassword(), usuario.getAceptaTerminos());
	            u.setId(generatedId);
			}
			st.close();
		} catch (SQLException e) {
			System.out.println("Error de SQL(cargarUsuario()): " + e.getMessage());
		}
	}
	
	public Usuario obtenerUsuario(String email) {
		Usuario us=null;
		try {
			String query = "SELECT * FROM USUARIO";
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				if(res.getString("email").equals(email)){
					us = new Usuario(res.getInt("idPer"),res.getString("email"),res.getString("password"),res.getBoolean("aceptaTerminos"));	
				}
			}
			st.close();
		} catch (SQLException e) {
            System.out.print("Error de SQL:"+e.getMessage());
        }
		return us;
	}
}

