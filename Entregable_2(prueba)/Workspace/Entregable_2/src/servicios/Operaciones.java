package servicios;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import dao.ActivoDAOjdbc;
import dao.MonedaDAOjdbc;
import dao.TransaccionDAOjdbc;
import modelo.Activo;
import modelo.Moneda;
import modelo.Transaccion;

public class Operaciones {
	private MonedaDAOjdbc monedaDAO;
	private ActivoDAOjdbc activoDAO;
	private TransaccionDAOjdbc transaccionDAO;
	
	public Operaciones(MonedaDAOjdbc monedaDAO,ActivoDAOjdbc activoDAO,TransaccionDAOjdbc transaccionDAO) {
		this.monedaDAO=monedaDAO;
		this.activoDAO=activoDAO;
		this.transaccionDAO=transaccionDAO;
	}
	
	public void cargarMoneda(String tipo, String nombre,String nomenclatura, double valorEnDolar, double volatilidad, double stock) throws SQLException{
		if(!tipo.equals("Cripto") && !tipo.equals("Fiat")) {
			System.out.print("Error, debe ingresar 'Cripto' o 'Fiat'");
			return;
		}
		else {
			Moneda moneda=new Moneda(tipo,nombre,nomenclatura,valorEnDolar,volatilidad,stock);
			monedaDAO.cargarMoneda(moneda);
			System.out.println("Moneda agregada exitosamente.");
		}
	}
	public void listarMonedas() throws SQLException{
			List<Moneda> monedas = monedaDAO.listarMonedas();
			for(Moneda moneda: monedas){
				System.out.println(moneda.getNombre()+"("+moneda.getNomenclatura()+")"+" Valor:"+moneda.getValorEnDolar()+" USD"+ " Volatilidad:"+moneda.getVolatilidad()+" Stock:" +moneda.getStock());
			}
	}
	public void generarStock() throws SQLException{
        Random random = new Random();
		List<Moneda> monedas = monedaDAO.listarMonedas();
		for(Moneda moneda: monedas){
            // Generar una cantidad aleatoria para la moneda (entre 0 y 5000)
            double stockAleatorio = 5000 * random.nextDouble();
			monedaDAO.crearStock(moneda.getNomenclatura(),stockAleatorio);
		}
        System.out.println("Stock generado y actualizado para los activos.");
	}
	public void listarStock() throws SQLException {
		List<Moneda> monedas = monedaDAO.listarPorStock();
		for(Moneda moneda: monedas){
			System.out.println(moneda.getNombre()+"("+moneda.getNomenclatura()+")"+" Stock:" +moneda.getStock());
		}
	}
	
	//ACTIVOS
	public void cargarActivo(Double cantidad,String nomenclatura) {
		if(!monedaDAO.monedaEnBD(nomenclatura)) {
			System.out.println("Error, la moneda no se encuentra en la BD.");
			return;
		}
		else {
			Activo activo=new Activo(cantidad,nomenclatura);
			activoDAO.cargarActivo(activo);
			System.out.println("Activo agregado exitosamente.");
		}
	}

	public void listarActivos() throws SQLException{
			List<Activo> activos = activoDAO.listarActivos();
			for(Activo activo: activos){
				System.out.println(activo.getNomenclatura()+"\t"+activo.getCantidad());
			}
	}
	
	//COMPRA Y SWAP
	public void compra(String cripto, String fiat, Double monto,Scanner scanner) throws SQLException {
		// Si la cripto aún no es un activo lo creo
		if(!activoDAO.activoEnBD(cripto)) {
			this.cargarActivo(0.0, cripto);
		}
		
			// Solicitar las monedas para obtener sus valores en dolar y asi calcular el equivalente
		Moneda monedaCripto = monedaDAO.obtenerMoneda(cripto);
		Moneda monedaFiat = monedaDAO.obtenerMoneda(fiat);
			// Solicitar activo FIAT para su posterior actualización
		Activo activoFiat = activoDAO.obtenerActivo(fiat);
			// Calcular valor equivalente en cripto
		Double valorEquivalente = (monto * monedaFiat.getValorEnDolar()) / monedaCripto.getValorEnDolar();

			// Confirmar la compra
	    System.out.printf("Vas a comprar %.6f %s con %.2f %s%n", valorEquivalente, cripto, monto, fiat);
	    System.out.print("¿Deseas confirmar la operación? (si/no): ");
	    String confirmacion = scanner.nextLine();
	         
	    if (!confirmacion.equalsIgnoreCase("si")) {
	    	System.out.println("Operación cancelada.");
	    	return;
	    }
	          
			//verificar si hay stock
	    if(valorEquivalente > monedaCripto.getStock()) {
	    	System.out.println("El stock de "+cripto+" es insuficiente para realizar la compra");
	    	return;
	    }
			//verificar si fiat es suficiente
	    if(monto > activoFiat.getCantidad()) {
	    	System.out.println("Cantidad de "+fiat+" insuficiente para realizar la compra");
	    	return;
	    }
		
			// Actualizar activos del usuario y stock de la moneda
	    activoDAO.actualizarActivo(cripto, valorEquivalente);
	    activoDAO.actualizarActivo(fiat, -monto);
	    monedaDAO.actualizarStock(cripto, -valorEquivalente);
	    
	    	// Guardar descripcion de la transaccion en BD
	    String resumen = "Compra de " + valorEquivalente + " " + cripto + " con " + monto + " " + fiat;
	    LocalDateTime fechaHora = LocalDateTime.now();
	    String fecha = "Fecha: "+ fechaHora;
	    Transaccion transaccion = new Transaccion(resumen,fecha);
	    transaccionDAO.cargarTransaccion(transaccion);
		System.out.println("Compra realizada con éxito.");
	}
	

	public void swap(String criptoConvertir, Double monto, String criptoEsperada,Scanner scanner) throws SQLException {
		//se verifica si las criptos están entre sus activos
		if(!activoDAO.activoEnBD(criptoConvertir)) {
			System.out.println(criptoConvertir + " no se encuetra entre tus activos.");
			return;
		}
		//SI NO EXISTEN LOS CREO??
		if(!activoDAO.activoEnBD(criptoEsperada)) {
			System.out.println(criptoEsperada + " no se encuetra entre tus activos.");
			return;
		}
			// Obtengo las monedas saber sus valores en dolar y asi calcular el equivalente
		Moneda monedaCriptoConvertir = monedaDAO.obtenerMoneda(criptoConvertir);
		Moneda monedaCriptoEsperada = monedaDAO.obtenerMoneda(criptoEsperada);
			// Solicitar activo del fiat a convertir para su posterior actualización
		Activo activoCripto = activoDAO.obtenerActivo(criptoConvertir);
			// Calcular valor equivalente en cripto 
		Double valorEquivalente = (monto * monedaCriptoConvertir.getValorEnDolar()) / monedaCriptoEsperada.getValorEnDolar();

			// Confirmar el swap
	    System.out.printf("Hacer swap de %.6f %s con %.2f %s%n", valorEquivalente, criptoEsperada, monto, criptoConvertir);
	    System.out.print("¿Deseas confirmar la operación? (si/no): ");
	    String confirmacion = scanner.nextLine();
	         
	    if (!confirmacion.equalsIgnoreCase("si")) {
	    	System.out.println("Operación cancelada.");
	    	return;
	    }
	          
			//verificar si hay stock de la cripto esperada
	    if(valorEquivalente > monedaCriptoEsperada.getStock()) {
	    	System.out.println("Stock insuficiente para realizar la compra");
	    	return;
	    }
			//verificar si cripto a convertir es suficiente
	    if(monto > activoCripto.getCantidad()) {
	    	System.out.println("Cantidad de "+criptoConvertir+" Cripto insuficiente para realizar la compra");
	    	return;
	    }
			// Actualizar activos del usuario y stock de la moneda
	    activoDAO.actualizarActivo(criptoEsperada, valorEquivalente);
	    activoDAO.actualizarActivo(criptoConvertir, -monto);
	    monedaDAO.actualizarStock(criptoEsperada, -valorEquivalente);
	    
	    	// Guardar descripcion de la transaccion en BD
	    String resumen = "Swap de " + valorEquivalente + " " + criptoEsperada + " con " + monto + " " + criptoConvertir;
	    LocalDateTime fechaHora = LocalDateTime.now();
	    String fecha = "Fecha: "+ fechaHora;
	    Transaccion transaccion = new Transaccion(resumen,fecha);
	    transaccionDAO.cargarTransaccion(transaccion);
		System.out.println("Swap realizado con éxito.");
	}
}
