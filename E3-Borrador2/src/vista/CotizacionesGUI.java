package vista;

import javax.swing.*;

import controlador.Controladores;
import modelo.Activo;
import modelo.ModeloTablaMonedas;
import modelo.Moneda;
import modelo.Persona;
import modelo.Usuario;
import servicios.ActualizacionCotizaciones;

import java.awt.*;
import java.util.ArrayList;

public class CotizacionesGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTable tablaMonedas;
	private JButton volverButton,cerrarSesionButton,compraButton,swapButton;
	private Controladores controlador;

		public CotizacionesGUI(Controladores controlador) {
			this.controlador=controlador;
			Usuario usuario=controlador.getUsuarioLogueado();
			Persona persona=controlador.devolverPersona(usuario.getId());
			ActualizacionCotizaciones actualizacionCotizaciones=new ActualizacionCotizaciones(this);
			
	        setTitle("Billetera Virtual - Cotizaciones");
	        setSize(800, 600);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        // Panel Principal
	        JPanel mainPanel = new JPanel(new BorderLayout());
	        add(mainPanel);

	        // Panel Superior (Usuario)
	        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        JLabel usuarioLabel = new JLabel(persona.getNombres() +" "+ persona.getApellidos());
	        usuarioLabel.setFont(new Font("Arial", Font.BOLD, 14));
	        cerrarSesionButton = new JButton("Cerrar sesión");
	        cerrarSesionButton.setBackground(new Color(0, 123, 255)); // Azul
	        cerrarSesionButton.setForeground(Color.WHITE); // Texto blanco
	        cerrarSesionButton.setFont(new Font("SansSerif", Font.BOLD, 12));
	        userPanel.add(usuarioLabel);
	        userPanel.add(cerrarSesionButton);
	        mainPanel.add(userPanel, BorderLayout.NORTH);


	        compraButton = new JButton("Compra");
	        compraButton.setBackground(Color.GREEN);
	        compraButton.setForeground(Color.WHITE); // Texto blanco
	        compraButton.setFont(new Font("SansSerif", Font.BOLD, 12));
	        swapButton = new JButton("Swap");
	        swapButton.setBackground(Color.ORANGE);
	        swapButton.setForeground(Color.WHITE); // Texto blanco
	        swapButton.setFont(new Font("SansSerif", Font.BOLD, 12));
	        
	        
	        // Panel Central (Tabla de Cotizaciones)
	        String[] columnas = {"","Cripto", "Precio de Compra(USD)","",""};
	        // Crear una lista para las filas
	    	ArrayList<Object[]> filas = new ArrayList<>();
	    	ArrayList<Moneda> monedas=(ArrayList<Moneda>) controlador.retornarMonedas();
	    	ArrayList<Activo> activosUsuario=(ArrayList<Activo>) controlador.devolverActivosUsuario(usuario.getId());
	    	String swap;
	    	for(Moneda moneda:monedas) {
	    		swap="---";
	    		if(controlador.esActivo(moneda, activosUsuario)) {
	    			swap="Swap";
	    		}
	    		filas.add(new Object[]{new ImageIcon(getClass().getResource("/imagenes/"+moneda.getNombreIcono())), moneda.getNombre()+"("+moneda.getNomenclatura()+")",
	    				moneda.getValorEnDolar(),"Comprar",swap});
	    	}
	    	// Convertir la lista a un Object[][]
	    	Object[][] datos = filas.toArray(new Object[0][]);
	    	
	    	// Crear la tabla
	        tablaMonedas = new JTable(new ModeloTablaMonedas(datos, columnas)) {
	            private static final long serialVersionUID = 1L;

	            @Override
	            public boolean isCellEditable(int row, int column) {
	                // Solo las columnas de botones son editables
	                return column == 3 || column == 4;
	            }
	        };
	        
	     // Agregar un MouseListener para detectar clics en los botones
	        tablaMonedas.addMouseListener(new java.awt.event.MouseAdapter() {
	            @Override
	            public void mouseClicked(java.awt.event.MouseEvent evt) {
	                int fila = tablaMonedas.rowAtPoint(evt.getPoint());
	                int columna = tablaMonedas.columnAtPoint(evt.getPoint());

	                if (columna == 3) { // Botón "Comprar"
	                    String cripto = (String) tablaMonedas.getValueAt(fila, 1);
	                    mostrarMensaje("Comprar " + cripto);
	                    dispose();
	                    controlador.abrirVentanaCompra(cripto);
	                } else if (columna == 4) { // Botón "Swap"
	                    String cripto = (String) tablaMonedas.getValueAt(fila, 1);
	                    mostrarMensaje("Swap " + cripto);
	                }
	            }
	        });
	        
        	// mejoras de la tabla
        	tablaMonedas.setRowHeight(50);
       	 	//tablaMonedas.setEnabled(false); // Desactiva la edición de la tabla
       	 	tablaMonedas.setShowHorizontalLines(false); // Quita las lineas horizontales
       	 	tablaMonedas.setShowVerticalLines(false);   // Quita las lineas verticales
	        JScrollPane scrollPane = new JScrollPane(tablaMonedas);
	        mainPanel.add(scrollPane, BorderLayout.CENTER);
	        
	        
        	// Panel Inferior (Botón Volver)
        	JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        	volverButton = new JButton("Volver");
        	volverButton.setBackground(new Color(0, 123, 255)); // Azul
        	volverButton.setForeground(Color.WHITE); // Texto blanco
        	volverButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        	bottomPanel.add(volverButton);
        	mainPanel.add(bottomPanel, BorderLayout.SOUTH);
	
        	// Listeners
        	cerrarSesionButton.addActionListener(e ->{
        		actualizacionCotizaciones.detenerActualizacion();// Detenemos la actualización
        		dispose();//Cerramos la ventana
        		controlador.abrirVentanaLogin();
        	});
        	volverButton.addActionListener(e ->{
        		actualizacionCotizaciones.detenerActualizacion();//Detenemos la actualización
        		dispose();//Cerramos la ventana
        		controlador.abrirVentanaActivos();
        	});
     }
	
	public void actualizarCotizaciones(double btc, double eth, double usdc, double usdt, double doge) {
    	tablaMonedas.getModel().setValueAt(btc, 0, 2);
    	tablaMonedas.getModel().setValueAt(eth, 1, 2);
    	tablaMonedas.getModel().setValueAt(usdc, 2, 2);
    	tablaMonedas.getModel().setValueAt(usdt, 3, 2);
    	tablaMonedas.getModel().setValueAt(doge, 4, 2);
    	tablaMonedas.revalidate();
    	tablaMonedas.repaint();
	}

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}





