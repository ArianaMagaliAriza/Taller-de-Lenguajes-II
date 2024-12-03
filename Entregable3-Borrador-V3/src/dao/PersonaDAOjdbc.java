package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Persona;
import servicios.Conexion;

public class PersonaDAOjdbc implements PersonaDAO{
	Connection con;

	public PersonaDAOjdbc() {
		this.con = Conexion.getCon();
	}
	
	public void cargarPersona(Persona persona) {
		try {
			String query = "INSERT INTO USUARIO(nombres, apellidos) VALUES(?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, persona.getNombres());
			st.setString(2, persona.getApellidos());
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error de SQL(cargarUsuario()): " + e.getMessage());
		}
	}
}
