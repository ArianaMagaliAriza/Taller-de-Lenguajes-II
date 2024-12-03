package controlador;

import javax.swing.SwingUtilities;
import servicios.CreacionTablas;
import vista.LoginGUI;

public class Controladores {
	//NEW VISTA MANEJO DE DAO
	public void MostrarLogin(){
	SwingUtilities.invokeLater(() -> {
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(true);
    });		
	}
}
