package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import dao.ActivoDAO;
import dao.MonedaDAO;
import dao.TransaccionDAO;

public class Main {
	public static void main (String[]args) {
		Connection con=null;
		try {
			con=DriverManager.getConnection("jdbc:sqlite:test.db");
			creacionDeTablasEnBD(con);
			//Instanciar DAOs y servicios
			MonedaDAO monedaDAO = new MonedaDAO(con);
			ActivoDAO activoDAO = new ActivoDAO(con);
			TransaccionDAO transaccionDAO= new TransaccionDAO(con);
			Operaciones operaciones = new Operaciones(monedaDAO,activoDAO,transaccionDAO);
			//Interfaz con las operaciones
			Scanner in = new Scanner(System.in);
			int opcion;
			
			do {
				System.out.println("-Ingrese la operación que desee realizar-");
				System.out.println("1.Crear Moneda");
				System.out.println("2.Listar Monedas");
				System.out.println("3.Generar Stock");
				System.out.println("4.Listar Stock");
				System.out.println("5.Crear Activo");
				System.out.println("6.Listar Mis Activos");
				System.out.println("7.Compra");
				System.out.println("8.Swap");
				System.out.println("9.Salir");
				System.out.print(">");
				opcion=in.nextInt();
				in.nextLine();
				switch (opcion) {
					case 1:
						System.out.print("Tipo de moneda a crear(Cripto/Fiat):");
						String tipo = in.nextLine();
						System.out.print("Nombre:");
						String nombre = in.nextLine();
						System.out.print("Nomenclatura:");
						String nomenclatura = in.nextLine();
						System.out.print("Valor en dolár(USD):");
						Double valorEnDolar = in.nextDouble();
						System.out.print("Volatilidad(0-100):");
						Double volatilidad = in.nextDouble();
						System.out.print("Stock:");
						Double stock = in.nextDouble();
						in.nextLine();
						System.out.print("Guardar Moneda en BD?(S/N):");
						String resp = in.nextLine();
						if(resp.equalsIgnoreCase("S")) {
							operaciones.cargarMoneda(tipo, nombre, nomenclatura, valorEnDolar,volatilidad, stock);
						}
						else {
							System.out.println("Creación de moneda cancelada");
						}
						break;
					case 2:
						System.out.println("--Monedas Disponibles--");
						operaciones.listarMonedas();
						break;
					case 3:
						operaciones.generarStock();
						break;
					case 4: 
						System.out.println("--Lista de Stocks--");
						operaciones.listarStock();
						break;
					case 5:
						System.out.print("Cantidad:");
						Double cantidad = in.nextDouble();
						in.nextLine();
						System.out.print("Nomenclatura:");
						String nom = in.nextLine();
						System.out.print("Guardar Activo en BD?(S/N):");
						String res = in.nextLine();
						if(res.equalsIgnoreCase("S")) {
							operaciones.cargarActivo(cantidad, nom);
						}
						else {
							System.out.println("Creación de activo cancelada");
						}
						break;
					case 6:
						System.out.println("Activo|Cantidad");
						operaciones.listarActivos();
						break;
					case 7:
						System.out.print("Criptomoneda que desea comprar(nomenclatura):");
						String cripto = in.nextLine();
						System.out.print("FIAT con la que desea comprar(nomenclatura):");
						String fiat = in.nextLine();
						System.out.print("Monto:");
						Double monto = in.nextDouble();
						in.nextLine();
					    operaciones.compra(cripto, fiat, monto,in);  //paso Scanner para evitar abrir otro
						break;
					case 8:
						System.out.print("Criptomoneda a convertir(nomenclatura):");
						String criptoConvertir = in.nextLine();
						System.out.print("Cantidad:");
						Double cant = in.nextDouble();
						in.nextLine();
						System.out.print("Criptomoneda que espera(nomenclatura):");
						String criptoEsperada = in.nextLine();
					    operaciones.swap(criptoConvertir, cant, criptoEsperada,in);  //paso Scanner para evitar abrir otro
						break;
					case 9: 
						System.out.print("Fin del programa");
						break;
					}
			}while(opcion != 9);
			in.close();
			//Cierre de conexión
			con.close();
		}catch (SQLException e) {
			System.out.print("Error en la conexión con la BD. "+ e.getMessage());
		}
		
		
		
		
	
}
	
	/**
	 * Este método se encarga de la creación de las tablas donde se
       * almacenará la
	 * información de los objetos. Una vez establecida una conexión, debería
	 * ser lo próximo a ser ejecutado.
	 *
	 * @param connection objeto conexion a la base de datos SQLite
	 * @throws SQLException
	 */
	private static void creacionDeTablasEnBD(Connection connection) throws SQLException {
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