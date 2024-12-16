package servicios;

import java.sql.Connection;
import java.sql.SQLException;

import controlador.Controladores;
import dao.ActivoDAOjdbc;
import dao.MonedaDAOjdbc;
import dao.PersonaDAOjdbc;
import dao.TransaccionDAOjdbc;
import dao.UsuarioDAOjdbc;
import vista.*;

public class Main {
	public static void main (String[]args) {
		try {
			Connection con = Conexion.getCon();
			CreacionTablas.creacionDeTablasEnBD(con);
			//Instanciar DAOs y servicios
			MonedaDAOjdbc monedaDAO = new MonedaDAOjdbc(con);
			ActivoDAOjdbc activoDAO = new ActivoDAOjdbc(con);
			TransaccionDAOjdbc transaccionDAO = new TransaccionDAOjdbc(con);
			UsuarioDAOjdbc usuarioDAO = new UsuarioDAOjdbc(con);
			PersonaDAOjdbc personaDAO = new PersonaDAOjdbc(con);
			Operaciones operaciones = new Operaciones(monedaDAO,activoDAO,transaccionDAO,usuarioDAO,personaDAO);
			
			Controladores controlador = new Controladores(operaciones);
			LoginGUI loginVista = new LoginGUI(controlador);
			
			controlador.crearMonedasPrueba();
			controlador.mostrarLogin(loginVista);
			
			//cerrar conexion cuando el usuario cierra la aplicacion
		}catch (SQLException e) {
			System.out.print("Error en la conexión con la BD. "+ e.getMessage());
		}
		
		
		
		
	
}
	

}
