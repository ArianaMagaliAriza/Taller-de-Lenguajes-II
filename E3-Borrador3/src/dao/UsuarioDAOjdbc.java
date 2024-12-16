package dao;

import java.sql.*;

import modelo.Usuario;

public class UsuarioDAOjdbc implements UsuarioDAO{ 
	 Connection con;

	public UsuarioDAOjdbc(Connection con) {
		this.con = con;
	}
	
	public void cargarUsuario(Usuario usuario) {
		try {
			String query = "INSERT INTO USUARIO(id_persona,email, password, acepta_terminos) VALUES(?,?,?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, usuario.getId());
			st.setString(2, usuario.getEmail());
			st.setString(3, usuario.getPassword());
			st.setBoolean(4, usuario.getAceptaTerminos());
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error de SQL(cargarUsuario()): " + e.getMessage());
		}
	}
	//chequea si el usuario está en la base de datos
		public boolean usuarioEnBD(String email) {
			boolean existe=false;
			try{
				String query = "SELECT * FROM usuario";
				Statement st = con.createStatement();
				ResultSet res = st.executeQuery(query);
				while(res.next()) {
					if(res.getString("email").equals(email)) {
						existe=true;
						}
				}
			}catch(SQLException e) {
				System.out.print("Error de SQL: "+e.getMessage());
			}
			return existe;
		}
		

		public Usuario obtenerUsuario(String email) {
			Usuario usuario=null;
			try {
				String query = "SELECT * FROM usuario";
				Statement st = con.createStatement();
				ResultSet res = st.executeQuery(query);
				while(res.next()) {
					if(res.getString("email").equals(email)) {
						usuario = new Usuario(res.getInt("id_persona"),res.getString("email"),res.getString("password"),res.getBoolean("acepta_terminos"));
					}
				}
				st.close();
			} catch (SQLException e) {
	            System.out.print("Error de SQL:"+e.getMessage());
	        }
			return usuario;
		}
}
