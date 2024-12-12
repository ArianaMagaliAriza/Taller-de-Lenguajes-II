package vista;

import java.awt.*;
import javax.swing.*;

import controlador.Controladores;
public class CompraGUI extends JFrame{
private static final long serialVersionUID = 1L;
private Controladores controlador;

	public CompraGUI(Controladores controlador,String cripto) {
		this.controlador=controlador;
		
        setTitle("Billetera Virtual - Compra");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Panel Superior (Usuario)
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel userLabel = new JLabel("Juan Perez");
        JButton cerrarSesionButton = new JButton("Cerrar sesión");
        userPanel.add(userLabel);
        userPanel.add(cerrarSesionButton);
        mainPanel.add(userPanel, BorderLayout.NORTH);
        //Panel Central
    	// Panel Inferior (Botones inferiores)
    	JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	JButton realizarCompraButton = new JButton("Realizar Compra");
    	JButton cancelarButton = new JButton("Cancelar");
    	bottomPanel.add(realizarCompraButton);
    	bottomPanel.add(cancelarButton);
    	mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    	// Listeners
    	cerrarSesionButton.addActionListener(e -> {
    		dispose();
    		controlador.abrirVentanaLogin();
    	});
    	cancelarButton.addActionListener(e -> {
    		dispose();
    		controlador.abrirVentanaCotizaciones();
    	});
 }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
