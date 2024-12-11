package controlador;

import javax.swing.SwingUtilities;
import servicios.Operaciones;
import vista.LoginGUI;

public class Controladores {
	//NEW VISTA MANEJO DE DAO
	public void MostrarLogin(){
	SwingUtilities.invokeLater(() -> {
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(true);
    });		
	}
	
	public void RegistrarUser(String nombre, String apellido, String email, String password, Boolean aceptaTerminos) {
		Operaciones op= new Operaciones();
		op.cargarUsuario(nombre, apellido, email, password, aceptaTerminos);
	}
	
	public boolean LoginUser(String email, String password){
		boolean exito = false;
		Operaciones op= new Operaciones();
		if (op.verificarUsuario(email, password)){
			exito= true;
		}
		return exito;
			
	}
	
}
