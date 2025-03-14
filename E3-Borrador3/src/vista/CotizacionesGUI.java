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
	private JButton volverButton,cerrarSesionButton;
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
	        userPanel.setBackground(new Color(235,247,254));
	        mainPanel.add(userPanel, BorderLayout.NORTH);
	        
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
	                    String nombreCripto = (String) tablaMonedas.getValueAt(fila, 1);
	                    String precio = tablaMonedas.getValueAt(fila, 2).toString();
	                    //extraer el nombre de la cripto sin la nomeclatura
	                    String cripto = nombreCripto.split("\\(")[0].trim();
	                    mostrarMensaje("Comprar " + cripto);
	                    dispose();
	                    controlador.abrirVentanaCompra(cripto,precio);
	                } else if (columna == 4) { // Botón "Swap"
	                   String boton=(String) tablaMonedas.getValueAt(fila, columna);
	                   if(!boton.equals("Swap")) {
	                	mostrarMensaje("La cripto seleccionada no está entre sus activos");
	                	return;
	                    }
	                    String nombreCripto = (String) tablaMonedas.getValueAt(fila, 1);
	                    String precio = tablaMonedas.getValueAt(fila, 2).toString();
	                    //extraer el nombre de la cripto sin la nomeclatura
	                    String cripto = nombreCripto.split("\\(")[0].trim();
	                    mostrarMensaje("Swap " + cripto);
	                    dispose();
	                    //abrir ventana swap
	                    controlador.abrirVentanaSwap(cripto,precio);
	                }
	            }
	        });
	        
        	// mejoras de la tabla
        	tablaMonedas.setRowHeight(50);
       	 	tablaMonedas.setShowHorizontalLines(false); // Quita las lineas horizontales
       	 	tablaMonedas.setShowVerticalLines(false);   // Quita las lineas verticales
	        tablaMonedas.setBackground(new Color(235,247,254));
            	// Configurar TableRowSorter para habilitar el ordenamiento
	        TableRowSorter<ModeloTablaMonedas> sorter = new TableRowSorter<>(new ModeloTablaMonedas(datos, columnas));
	        tablaMonedas.setRowSorter(sorter);
        	// Centrar el contenido de las celdas
            	DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
            	centrado.setHorizontalAlignment(SwingConstants.CENTER); // Centrar horizontalmente
            	// Aplicar el renderizador a todas las columnas(excepto a la primera que es el icono)
            	for (int i = 1; i < tablaMonedas.getColumnCount(); i++) {
                	tablaMonedas.getColumnModel().getColumn(i).setCellRenderer(centrado);
            	}
            
	        mainPanel.add(new JScrollPane(tablaMonedas), BorderLayout.CENTER);
	        
	        
        	// Panel Inferior (Botón Volver)
        	JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        	volverButton = new JButton("Volver");
        	volverButton.setBackground(new Color(0, 123, 255)); // Azul
        	volverButton.setForeground(Color.WHITE); // Texto blanco
        	volverButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        	bottomPanel.add(volverButton);
        	bottomPanel.setBackground(new Color(235,247,254));
        	mainPanel.add(bottomPanel, BorderLayout.SOUTH);
	
        	// Listeners
        	cerrarSesionButton.addActionListener(e ->{
        		actualizacionCotizaciones.detenerActualizacion();// Detenemos la actualización en 2do plano
        		dispose();//Cerramos la ventana
        		controlador.abrirVentanaLogin();
        	});
        	volverButton.addActionListener(e ->{
        		actualizacionCotizaciones.detenerActualizacion();//Detenemos la actualización en 2do plano
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





