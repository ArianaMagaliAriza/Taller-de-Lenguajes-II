package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos {

	/**
	* Este método se encarga de la creación de las tablas donde se almacenará la
	* información de los objetos.
	* Una vez establecida una conexión, debería ser lo próximo a ser ejecutado.
	*
	* @param connection objeto conexion a la base de datos SQLite
	* @throws SQLException
	*/
	private static void creaciónDeTablasEnBD(Connection connection) throws
	SQLException {
	Statement stmt;
	stmt = connection.createStatement();
	String sql = "CREATE TABLE CRIPTOMONEDA " +
	"(" +
	" NOMBRE VARCHAR(50) NOT NULL, " +
	" NOMENCLATURA VARCHAR(10) PRIMARY KEY NOT NULL, " +
	" VALOR_DOLAR REAL NOT NULL " +
	")";
	stmt.executeUpdate(sql);
	sql = "CREATE TABLE FIAT " +
	"(" +
	" NOMBRE VARCHAR(50) NOT NULL, " +
	" NOMENCLATURA VARCHAR(10) PRIMARY KEY NOT NULL, " +
	" VALOR_DOLAR REAL NOT NULL " +
	")";
	stmt.executeUpdate(sql);
	sql = "CREATE TABLE ACTIVO_CRIPTO" +
	"(" +
	" NOMENCLATURA VARCHAR(10) PRIMARY KEY NOT NULL, " +
	" CANTIDAD REAL NOT NULL " +
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
	" RESUMEN VARCHAR(1000) NOT NULL, " +
	" FECHA_HORA DATETIME NOT NULL " +
	")";
	stmt.executeUpdate(sql);
	stmt.close();
	}
}