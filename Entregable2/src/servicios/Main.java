package servicios;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import dao.ActivoDAOjdbc;
import dao.MonedaDAOjdbc;
import dao.TransaccionDAOjdbc;

public class Main {
	public static void main (String[]args) {
		try {
			Connection con = Conexion.getCon();
			Conexion.creacionDeTablasEnBD(con);
			//Instanciar DAOs y servicios
			MonedaDAOjdbc monedaDAO = new MonedaDAOjdbc(con);
			ActivoDAOjdbc activoDAO = new ActivoDAOjdbc(con);
			TransaccionDAOjdbc transaccionDAO= new TransaccionDAOjdbc(con);
			Operaciones operaciones = new Operaciones(monedaDAO,activoDAO,transaccionDAO);
			//Interfaz con las operaciones
			Scanner in = new Scanner(System.in);
			int opcion;
			int criterio;
			
			do {
				System.out.println("-Ingrese la operación que desee realizar-");
				System.out.println("1.Crear Moneda");
				System.out.println("2.Listar Monedas");
				System.out.println("3.Generar Stock");
				System.out.println("4.Listar Stock");
				System.out.println("5.Generar Activo");
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
						System.out.print("Ordenar por: \n 1. Valor en dolar. \n 2. Nomenclatura. \n >");
						criterio = in.nextInt();
						in.nextLine();
						System.out.println("--Monedas Disponibles--");
						operaciones.listarMonedas(criterio);
						break;
					case 3:
						operaciones.generarStock();
						break;
					case 4: 
						System.out.print("Ordenar por: \n 1. Cantidad Descendente. \n 2. Nomenclatura. \n >");
						criterio = in.nextInt();
						in.nextLine();
						System.out.println("--Lista de Stocks--");
						operaciones.listarStocks(criterio);
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
						System.out.print("Ordenar por: \n 1. Cantidad Descendente. \n 2. Nomenclatura. \n >");
						criterio = in.nextInt();
						in.nextLine();
						System.out.println("Activo|Cantidad");
						operaciones.listarActivos(criterio);
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
			Conexion.discon();
		}catch (SQLException e) {
			System.out.print("Error en la conexión con la BD. "+ e.getMessage());
		}
		
		
		
		
	
}
	

}
