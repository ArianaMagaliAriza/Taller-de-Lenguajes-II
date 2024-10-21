package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class baseDeDatos{
	/**
	* Este método se encarga de la creación de las tablas donde se almacenará la
	* información de los objetos.
	* Una vez establecida una conexión, debería ser lo próximo a ser ejecutado.
	*
	* @param connection objeto conexion a la base de datos SQLite
	* @throws SQLException
	*/
	public static Connection conectar() {
		Connection connection = null;
		try {
			String url = "jdbc:sqlite:test.db";
			connection = DriverManager.getConnection(url);
			System.out.println("Conectado a SQLite");
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}

	private static void creacionDeTablasEnBD(Connection connection) throwsSQLException {
	Statement stmt;
	stmt = connection.createStatement();
	String sql = "CREATE TABLE CRIPTO " +
			"(" +
			" NOMBRE VARCHAR(50) NOT NULL, " +
			" NOMENCLATURA VARCHAR(10) PRIMARY KEY NOT NULL, " +
			" VALOR_DOLAR REAL NOT NULL " +
			")";
	stmt.executeUpdate(sql);
	sql = "CREATE TABLE FIDUCIARIO " +
		"(" +
		" NOMBRE VARCHAR(50) NOT NULL, " +
		" NOMENCLATURA VARCHAR(10) PRIMARY KEY NOT NULL, " +
		" VALOR_DOLAR REAL NOT NULL " +
		")";
	stmt.executeUpdate(sql);
	sql = "CREATE TABLE MONEDA" +
		"(" +
		" NOMBRE VARCHAR(50) PRIMARY KEY NOT NULL, " +
	")";
	stmt.executeUpdate(sql);
	sql = "CREATE TABLE ACTIVO_FIAT" +
		"(" +
		" NOMENCLATURA VARCHAR(10) PRIMARY KEY NOT NULL, " +
		" CANTIDAD REAL NOT NULL " +
		")";
	stmt.executeUpdate(sql);
	sql = "CREATE TABLE TRANSACCION" +
		"(" +
		" MONTO REAL NOT NULL, " +
		" MONEDA CRIPTO NOT NULL " +
		")";
	stmt.executeUpdate(sql);
	stmt.close();
	}
}