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
import excepciones.*;
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
	public int obtenerId(String nomenclatura) {
		return monedaDAO.obtenerId(nomenclatura);
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
	//Chequea si la moneda está entre los activos
	public boolean esActivo(Moneda moneda,List<Activo>activosUsuario) {
			for(Activo activo:activosUsuario) {
				Moneda monedaUser=obtenerMoneda(activo.getIdMoneda());
				if(monedaUser.getNombre().equals(moneda.getNombre())) {
					return true;
				}
			}
			return false;
	}
	
	public void cargarActivo(int idUsuario,int idMoneda,Double cantidad) {
		
			// Si el activo existe, se actualiza el valor en la BD
			if(activoDAO.activoEnBD(idMoneda)) {
				activoDAO.actualizarActivo(idUsuario,idMoneda, +cantidad);
			}
			// Sino se crea
			else {	
				Activo activo=new Activo(idUsuario,idMoneda,cantidad);
				activoDAO.cargarActivo(activo);
			}
		
	}

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
	
	//COMPRA Y SWAP
	public boolean compra(int idUsuario,Moneda cripto, Moneda fiat, Double monto,Double cantidadCripto,List<Activo>activosUsuario) throws SQLException, StockInsuficienteException, SaldoInsuficienteException {
		
			//verificar si hay stock
	    if(cantidadCripto > cripto.getStock()) {
	    	throw new StockInsuficienteException();
	    }
	    
			//verificar si fiat es suficiente
        double saldoUsuario = 0;
        for (Activo activo : activosUsuario) {
            if (esActivo(fiat,activosUsuario)) {
                saldoUsuario = activo.getCantidad();
                break;
            }
        }
        if (saldoUsuario < monto) {
        	throw new SaldoInsuficienteException();
        }
		
			// Actualizar activos del usuario y stock de la moneda
	    activoDAO.actualizarActivo(idUsuario,this.obtenerId(cripto.getNomenclatura()), cantidadCripto);
	    activoDAO.actualizarActivo(idUsuario,this.obtenerId(fiat.getNomenclatura()), -monto);
	    monedaDAO.actualizarStock(cripto.getNomenclatura(), -cantidadCripto);
	    
	    	// Guardar descripcion de la transaccion en BD
	    String resumen = "Compra de " + cantidadCripto + " " + cripto.getNomenclatura() + " con " + monto + " " + fiat.getNomenclatura();
	    LocalDateTime fechaHora = LocalDateTime.now();
	    String fecha = "Fecha: "+ fechaHora;
	    Transaccion transaccion = new Transaccion(resumen,fecha,idUsuario);
	    transaccionDAO.cargarTransaccion(transaccion);
		return true;
	}
	

	public boolean swap(int idUsuario,Moneda criptoConvertir, Double monto, Moneda criptoEsperada,Double cantidadCripto,List<Activo>activosUsuario) throws SQLException, StockInsuficienteException, SaldoInsuficienteException {
		
			//verificar si hay stock de la cripto esperada
	    if(cantidadCripto > criptoEsperada.getStock()) {
	    	throw new StockInsuficienteException();
	    }
			//verificar si cripto a convertir es suficiente
        double saldoUsuario = 0;
        for (Activo activo : activosUsuario) {
            if (esActivo(criptoConvertir,activosUsuario)) {
                saldoUsuario = activo.getCantidad();
                break;
            }
        }
	    if(saldoUsuario < monto) {
	    	throw new SaldoInsuficienteException();
	    }
	    
			// Actualizar activos del usuario y stock de la moneda
	    activoDAO.actualizarActivo(idUsuario,this.obtenerId(criptoEsperada.getNomenclatura()), cantidadCripto);
	    activoDAO.actualizarActivo(idUsuario,this.obtenerId(criptoConvertir.getNomenclatura()), -monto);
	    monedaDAO.actualizarStock(criptoEsperada.getNomenclatura(), -cantidadCripto);
	    
	    	// Guardar descripcion de la transaccion en BD
	    String resumen = "Swap de " + cantidadCripto + " " + criptoEsperada.getNomenclatura() + " con " + monto + " " + criptoConvertir.getNomenclatura();
	    LocalDateTime fechaHora = LocalDateTime.now();
	    String fecha = "Fecha: "+ fechaHora;
	    Transaccion transaccion = new Transaccion(resumen,fecha,idUsuario);
	    transaccionDAO.cargarTransaccion(transaccion);
		return true;
	}
}
