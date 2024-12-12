package servicios;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import dao.ActivoDAOjdbc;
import dao.MonedaDAOjdbc;
import dao.PersonaDAOjdbc;
import dao.TransaccionDAOjdbc;
import dao.UsuarioDAOjdbc;
import modelo.Activo;
import modelo.Moneda;
import modelo.Persona;
import modelo.Transaccion;
import modelo.Usuario;
import modelo.ComparadorNomenclaturaMoneda;
import modelo.ComparadorNomenclaturaActivo;

public class Operaciones {
	private MonedaDAOjdbc monedaDAO;
	private ActivoDAOjdbc activoDAO;
	private TransaccionDAOjdbc transaccionDAO;
	private UsuarioDAOjdbc usuarioDAO;
	private PersonaDAOjdbc personaDAO;
	
	public Operaciones() {}
	public Operaciones(MonedaDAOjdbc monedaDAO,ActivoDAOjdbc activoDAO,TransaccionDAOjdbc transaccionDAO,UsuarioDAOjdbc usuarioDAO,PersonaDAOjdbc personaDAO) {
		this.monedaDAO=monedaDAO;
		this.activoDAO=activoDAO;
		this.transaccionDAO=transaccionDAO;
		this.usuarioDAO=usuarioDAO;
		this.personaDAO=personaDAO;
	}
	//TRANSACCIONES
	public List<Transaccion> listarTransacciones(int idUsuario){
		List<Transaccion> transacciones = transaccionDAO.listarTransacciones(idUsuario);
		return transacciones;
	}
	//PERSONAS
	public int cargarPersona(String email,String apellidos) {
		Persona persona=new Persona(email,apellidos);
		return personaDAO.cargarPersona(persona);
	}
	public Persona obtenerPersona(int id) {
		return personaDAO.obtenerPersona(id);
	}
	
	//USUARIOS
	public void cargarUsuario(String nombres,String apellidos,String email,String password,Boolean terminos) {
		int id = this.cargarPersona(nombres,apellidos);
		Usuario usuario=new Usuario(id,email,password,terminos);
		usuarioDAO.cargarUsuario(usuario);
	}
	public boolean usuarioEnBD(String email){
		return usuarioDAO.usuarioEnBD(email);
	}
	public Usuario obtenerUsuario(String email) {
		return usuarioDAO.obtenerUsuario(email);
	}
	//MONEDAS
	public void cargarMoneda(String tipo, String nombre,String nomenclatura, double valorEnDolar, double volatilidad, double stock,String nombreIcono) throws SQLException{
		if(!tipo.equalsIgnoreCase("Cripto") && !tipo.equalsIgnoreCase("Fiat")) {
			System.out.println("Error, debe ingresar 'Cripto' o 'Fiat'");
			return;
		}
		else {
			Moneda moneda=new Moneda(tipo,nombre,nomenclatura,valorEnDolar,volatilidad,stock,nombreIcono);
			monedaDAO.cargarMoneda(moneda);
			System.out.println("Moneda agregada exitosamente.");
		}
	}
	public List<Moneda> listarMonedas(int ordenarPor) throws SQLException{
			List<Moneda> monedas = monedaDAO.listarMonedas();
			// Si se ingreso el numero 2, ordeno las monedas por nomenclatura
			if(ordenarPor == 2) {
				Collections.sort(monedas,new ComparadorNomenclaturaMoneda());
			}
			return monedas;
	}
	public void generarStock() throws SQLException{
        Random random = new Random();
		List<Moneda> monedas = monedaDAO.listarMonedas();
		for(Moneda moneda: monedas){
            // Generar una cantidad aleatoria para la moneda (entre 0 y 5000) 
            double stockAleatorio = 5000 * random.nextDouble();
			monedaDAO.crearStock(moneda.getNomenclatura(),stockAleatorio);
		}
        System.out.println("Stock generado y actualizado para las monedas.");
	}
	public Moneda obtenerMoneda(int id) {
		return monedaDAO.obtenerMoneda(id);
	}
	/*
	public void listarStocks(int ordenarPor) throws SQLException {
		List<Moneda> monedas = monedaDAO.listarStocks();
		//Si se ingreso el numero 2, ordeno los stocks por nomenclatura
		if(ordenarPor == 2) {
			Collections.sort(monedas,new ComparadorNomenclaturaMoneda());
		}
		for(Moneda moneda: monedas){
			System.out.println(moneda.getNombre()+"("+moneda.getNomenclatura()+")"+" Stock:" +moneda.getStock());
		}
	}*/
	//ACTIVOS
	public boolean activoEnBD(int id) {
		return(activoDAO.activoEnBD(id));
	}
	/*
	public void cargarActivo(int idUsuario,int idMoneda,Double cantidad) {
		if(!monedaDAO.monedaEnBD(nomenclatura)) {
			System.out.println("Error, la moneda no se encuentra en la BD.");
			return;
		}
		else {
			// Si el activo existe, se actualiza el valor en la BD
			if(activoDAO.activoEnBD(nomenclatura)) {
				activoDAO.actualizarActivo(nomenclatura, +cantidad);
				System.out.println("Activo actualizado exitosamente.");
			}
			// Sino se crea
			else {	
				Activo activo=new Activo(idUsuario,idMoneda,cantidad);
				activoDAO.cargarActivo(activo);
				System.out.println("Activo agregado exitosamente.");
			}
		}
	}*/

	public List<Activo> listarActivos(int ordenarPor) throws SQLException{
			List<Activo> activos = activoDAO.listarActivos();
			//Si se ingreso el numero 2, ordeno los activos por nomenclatura
			if(ordenarPor == 2) {
				Collections.sort(activos,new ComparadorNomenclaturaActivo());
			}
			return activos;
	}

	public void generarActivosPrueba(int id) throws SQLException{
        activoDAO.cargarStockActivo(id);
       }
	/*
	//COMPRA Y SWAP
	public void compra(String cripto, String fiat, Double monto,Scanner scanner) throws SQLException {
		// Si la cripto aún no es un activo lo creo
		if(!activoDAO.activoEnBD(cripto)) {
			System.out.println(cripto + " será agregada a sus activos");
			this.cargarActivo(0.0, cripto);
		}
		
			// Solicitar las monedas obtener sus valores en dolar y asi calcular el equivalente
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
			System.out.println(criptoConvertir + " no se encuetra entre sus activos.");
			return;
		}
		if(!activoDAO.activoEnBD(criptoEsperada)) {
			System.out.println(criptoEsperada + " no se encuetra entre sus activos, será creado.");
			this.cargarActivo(0.0,criptoEsperada);
		}
			// Obtengo las monedas saber sus valores en dolar y asi calcular el equivalente
		Moneda monedaCriptoConvertir = monedaDAO.obtenerMoneda(criptoConvertir);
		Moneda monedaCriptoEsperada = monedaDAO.obtenerMoneda(criptoEsperada);
			// Solicitar activo del fiat a convertir para su posterior actualización
		Activo activoCripto = activoDAO.obtenerActivo(criptoConvertir);
			// Calcular valor equivalente en cripto 
		Double valorEquivalente = (monto * monedaCriptoConvertir.getValorEnDolar()) / monedaCriptoEsperada.getValorEnDolar();

			// Confirmar el swap
	    System.out.printf("Hacer swap de %.2f %s con %.2f %s%n", valorEquivalente, criptoEsperada, monto, criptoConvertir);
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
	}*/
}
