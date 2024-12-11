package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Persona;
import servicios.Conexion;

public class PersonaDAOjdbc implements PersonaDAO{
	Connection con;

	public PersonaDAOjdbc() {
		this.con = Conexion.getCon();
	}
	
	public Persona cargarPersona(Persona persona){
		Persona p = null;
		try {
			String query = "INSERT INTO PERSONA (nombres, apellidos) VALUES(?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, persona.getNombres());
			st.setString(2, persona.getApellidos());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
	            int generatedId = rs.getInt(1);
	            p = new Persona(persona.getNombres(),persona.getApellidos());
	            p.setId(generatedId);
			}
			return p;
		} catch (SQLException e){
			System.out.println("Error de SQL(cargarPersona()): " + e.getMessage());
		}
		return null;
	}
}
