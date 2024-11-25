package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {// SINGLETON DE INSTANCIA TEMPRANA
	private static Connection con = null;
	
	private Conexion() {}
	
	static {	
		try {
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
		} catch (SQLException e) {
			System.out.print("Error en la conexión con la BD: " + e.getMessage());
		}
	}
	
	public static Connection getCon() {
		return con;
	}
	public static void discon() {
		//Cierre de conexión
		try {
			con.close();
		} catch (SQLException e) {
			System.out.print("Error en la desconexión con la BD: " + e.getMessage());
		}
	}
}	

