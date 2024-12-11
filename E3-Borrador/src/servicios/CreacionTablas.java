package servicios;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreacionTablas {
	
	/**
	* Este método se encarga de la creación de las tablas.
	*
	* @param connection objeto conexion a la base de datos SQLite
	* @throws SQLException
	*/
	
	public static void creacionDeTablasEnBD(Connection connection) throws
	SQLException {
		Statement stmt;
		stmt = connection.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS PERSONA "
				+ "("
				+ " ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
				+ " NOMBRES VARCHAR(50) NOT NULL, "
				+ " APELLIDOS VARCHAR(50) NOT NULL "
				+ ")";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS USUARIO " 
				+ "(" 
				+ " ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ " ID_PERSONA INTEGER NOT NULL, "
				+ " EMAIL VARCHAR(50) NOT NULL, "
				+ " PASSWORD VARCHAR(50) NOT NULL, "
				+ " ACEPTA_TERMINOS BOOLEAN NOT NULL, "
				+ " FOREIGN KEY(ID_PERSONA) REFERENCES PERSONA(ID)"
				+ ")";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS MONEDA "
				+ "("
				+ " ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
				+ " TIPO VARCHAR(1) NOT NULL, "
				+ " NOMBRE VARCHAR(50) NOT NULL, "
				+ " NOMENCLATURA VARCHAR(10) NOT NULL, "
				+ " VALOR_DOLAR REAL NOT NULL, "
				+ " VOLATILIDAD REAL NULL, "
				+ " STOCK REAL NULL, "
				+ " NOMBRE_ICONO VARCHAR(50) NOT NULL "
				+ ")";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS ACTIVO"
				+ "("
				+ " ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"	
				+ " ID_USUARIO INTEGER NOT NULL, "
				+ " ID_MONEDA INTEGER NOT NULL, "
				+ " CANTIDAD REAL NOT NULL, "
				+ " FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO(ID),"
				+ " FOREIGN KEY(ID_MONEDA) REFERENCES MONEDA(ID) "
				+ ")";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS TRANSACCION"
				+ "("
				+ " ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ " RESUMEN VARCHAR(1000) NOT NULL, "
				+ " FECHA_HORA DATETIME NOT NULL, "
				+ " ID_USUARIO INTEGER NOT NULL, "
				+ " FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO(ID)"
				+ ")";
		stmt.executeUpdate(sql);
		stmt.close();
	}
}
