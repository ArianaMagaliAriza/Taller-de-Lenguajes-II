package servicios;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreacionTablas {
	
	/*
	 * Este método se encarga de la creación de las tablas donde se
       * almacenará la
	 * información de los objetos. Una vez establecida una conexión, debería
	 * ser lo próximo a ser ejecutado.
	 *
	 * @param connection objeto conexion a la base de datos SQLite
	 * @throws SQLException
	 */
	
	public static void creacionDeTablasEnBD(Connection connection) throws SQLException {
			Statement stmt;
			stmt = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS MONEDA" 
						+ "(" 
						+ " TIPO       VARCHAR(1)    NOT NULL, "
						+ " NOMBRE       VARCHAR(50)    NOT NULL, " 
						+ " NOMENCLATURA VARCHAR(10)  PRIMARY KEY   NOT NULL, "
						+ " VALOR_DOLAR	REAL     NOT NULL, " 
						+ " VOLATILIDAD	REAL     NULL, "
						+ " STOCK	REAL     NULL "  + ")";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE IF NOT EXISTS ACTIVO" 
					+ "(" 
					+ " NOMENCLATURA VARCHAR(10)  PRIMARY KEY     NOT NULL, "
					+ " CANTIDAD	REAL    NOT NULL " + ")";
		   	stmt.executeUpdate(sql);
		   	sql = "CREATE TABLE IF NOT EXISTS TRANSACCION" 
		   			+ "(" 
		   			+ " RESUMEN VARCHAR(1000)   NOT NULL, "
		   			+ " FECHA_HORA		DATETIME  NOT NULL " + ")";
		   	stmt.executeUpdate(sql);
		   	stmt.close();
	}
}
