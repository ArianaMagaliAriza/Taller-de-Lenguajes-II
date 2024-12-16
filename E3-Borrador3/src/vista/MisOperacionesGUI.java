package vista;

import javax.swing.*;

import controlador.Controladores;
import modelo.Usuario;

import java.awt.*;

public class MisOperacionesGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTextArea operacionesArea;
	private JButton volverButton;
	private Controladores controlador;
	
	    public MisOperacionesGUI(Controladores controlador) {
	    	this.controlador=controlador;
	    	Usuario usuario=controlador.getUsuarioLogueado();
	    	
	        setTitle("Billetera Virtual - Mis Operaciones");
	        setSize(800, 600);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        // Panel Principal
	        JPanel mainPanel = new JPanel(new BorderLayout());
	        JLabel label=new JLabel("Mis Operaciones");
	        label.setFont(new Font("SansSerif", Font.PLAIN, 18));
	        add(label,BorderLayout.BEFORE_FIRST_LINE);
	        add(mainPanel);

	        // Panel Central (Operaciones)
	        operacionesArea = new JTextArea(5,5);
	        operacionesArea.setEditable(false); // No editable
	        operacionesArea.setFont(new Font("SansSerif", Font.PLAIN, 18));
	        
	        operacionesArea.setText(controlador.listarOperaciones(usuario.getId()));

	        JScrollPane scrollPane = new JScrollPane(operacionesArea);
	        mainPanel.add(scrollPane, BorderLayout.CENTER);

	        // Panel Inferior (Botón Volver)
	        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        volverButton = new JButton("Volver");
	        volverButton.setBackground(new Color(0, 123, 255)); // Azul
	        volverButton.setForeground(Color.WHITE); // Texto blanco
	        volverButton.setFont(new Font("SansSerif", Font.BOLD, 13));
	        bottomPanel.add(volverButton);
	        bottomPanel.setBackground(new Color(235,247,254));
	        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

	        // Evento del botón Volver (Ejemplo)
	        volverButton.addActionListener(e -> {
	            dispose(); // Cierra esta ventana
	            controlador.abrirVentanaActivos();
	        });
	    }

	}


