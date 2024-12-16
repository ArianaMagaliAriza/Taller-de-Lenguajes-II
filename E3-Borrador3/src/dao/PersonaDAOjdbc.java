package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.Persona;

public class PersonaDAOjdbc implements PersonaDAO{

	private Connection con;
	
	public PersonaDAOjdbc(Connection con) {
		this.con=con;
	}
	
	// Método para cargar Persona en BD y devolver su ID
	public int cargarPersona(Persona persona) {
        int idPersona=0;
		try {
			String query = "INSERT INTO PERSONA(nombres,apellidos) VALUES(?,?)";
			PreparedStatement st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			st.clearParameters();
			st.setString(1, persona.getNombres());
			st.setString(2, persona.getApellidos());
			st.executeUpdate();
			ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()){ 
                    idPersona = generatedKeys.getInt(1);}
		} catch (SQLException e) {
			System.out.println("Error de SQL(cargarPersona()): " + e.getMessage());
		}
		return idPersona;
		
	}
	public Persona obtenerPersona(int id) {
		Persona persona=null;
			try {
				String query = "SELECT * FROM PERSONA WHERE ID=?";
				PreparedStatement st = con.prepareStatement(query);
				st.setInt(1,id);
				ResultSet res = st.executeQuery();
				persona = new Persona(res.getString("NOMBRES"),res.getString("APELLIDOS"));

				st.close();
			} catch (SQLException e) {
	            System.out.print("Error de SQL(obtenerPersona):"+e.getMessage());
	        }
			return persona;
	}

		
}
