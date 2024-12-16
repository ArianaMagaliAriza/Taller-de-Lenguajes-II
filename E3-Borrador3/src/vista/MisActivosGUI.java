package vista;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controlador.Controladores;
import modelo.*;

public class MisActivosGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Componentes de la GUI
	private JTable tablaActivos;
	private JButton cerrarSesionButton,generarDatosPruebaButton,exportarCSVButton,misOperacionesButton,cotizacionesButton;
	JLabel balanceLabel;
	private Controladores controlador;
	
    public MisActivosGUI(Controladores controlador) {
    	this.controlador=controlador;
    	Usuario usuario = controlador.getUsuarioLogueado();
    	Persona persona = controlador.devolverPersona(usuario.getId());
    	
        // Configuración básica de la ventana
        setTitle("Billetera Virtual - Mis Activos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // PANEL PRINCIPAL
        JPanel panelPrincipal = new JPanel(new BorderLayout());
	    
        // PANEL SUPERIOR
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel usuarioLabel = new JLabel(persona.getNombres() +" "+ persona.getApellidos());
        usuarioLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        cerrarSesionButton = new JButton("Cerrar Sesión");
        cerrarSesionButton.setBackground(new Color(0, 123, 255)); // Azul
        cerrarSesionButton.setForeground(Color.WHITE); // Texto blanco
        cerrarSesionButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        generarDatosPruebaButton = new JButton("Generar Datos de Prueba");
        generarDatosPruebaButton.setBackground(new Color(0, 123, 255)); // Azul
        generarDatosPruebaButton.setForeground(Color.WHITE); // Texto blanco
        generarDatosPruebaButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelSuperior.add(usuarioLabel);
        panelSuperior.add(cerrarSesionButton);
        panelSuperior.add(generarDatosPruebaButton);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        
        // CONFIGURAR TABLA
        Double balance=0.0;
        Double valorDolarARS=1000.0; //valor de 1 USD en la moneda fiduciaria(ARS en este caso)
    	String[] columnas={"","Cripto","Monto"};
    	
    	// Crear una lista para las filas
    	ArrayList<Object[]> filas = new ArrayList<>();
    	ArrayList<Activo> activosUsuario=(ArrayList<Activo>) controlador.devolverActivosUsuario(usuario.getId());
    	for(Activo activo:activosUsuario) {
    		Moneda moneda = controlador.devolverMoneda(activo.getIdMoneda());
    		balance+=(activo.getCantidad()*moneda.getValorEnDolar()*valorDolarARS);
    		filas.add(new Object[]{new ImageIcon(getClass().getResource("/imagenes/"+moneda.getNombreIcono())), moneda.getNombre()+"("+moneda.getNomenclatura()+")", activo.getCantidad()});
    	}
    	// Convertir la lista a un Object[][]
    	Object[][] monedas = filas.toArray(new Object[0][]);
    	
    	tablaActivos = new JTable();
    	ModeloTablaMonedas modelo=new ModeloTablaMonedas(monedas,columnas);
        tablaActivos.setModel(modelo);
        tablaActivos.setRowHeight(50);
        tablaActivos.setEnabled(false);
        tablaActivos.setShowVerticalLines(false);
        tablaActivos.setShowHorizontalLines(false);
        tablaActivos.setBackground(new Color(235,247,254));

        // Configurar TableRowSorter para habilitar el ordenamiento
        TableRowSorter<ModeloTablaMonedas> sorter = new TableRowSorter<>(modelo);
        tablaActivos.setRowSorter(sorter);
        // Centrar el contenido de las celdas
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER); // Centrar horizontalmente
        // Aplicar el renderizador a todas las columnas(excepto a la primera que es el icono)
        for (int i = 1; i < tablaActivos.getColumnCount(); i++) {
            tablaActivos.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
        panelPrincipal.add(new JScrollPane(tablaActivos),BorderLayout.CENTER);
        // Balance
        // Formato personalizado: separador de miles y 2 decimales
        DecimalFormat formato = new DecimalFormat("#,##0.00");
        balanceLabel = new JLabel("Balance: "+formato.format(balance)+" ARS");
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        panelSuperior.add(balanceLabel,BorderLayout.AFTER_LAST_LINE);
        panelSuperior.setBackground(new Color(235,247,254));

        // Panel Inferior (Botón Volver)
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exportarCSVButton = new JButton("Exportar como CSV");
        exportarCSVButton.setBackground(new Color(0, 123, 255)); // Azul
        exportarCSVButton.setForeground(Color.WHITE); // Texto blanco
        exportarCSVButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelInferior.add(exportarCSVButton);
        misOperacionesButton = new JButton("Mis Operaciones");
        misOperacionesButton.setBackground(new Color(0, 123, 255)); // Azul
        misOperacionesButton.setForeground(Color.WHITE); // Texto blanco
        misOperacionesButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelInferior.add(misOperacionesButton);
        cotizacionesButton = new JButton("Cotizaciones");
        cotizacionesButton.setBackground(new Color(0, 123, 255)); // Azul
        cotizacionesButton.setForeground(Color.WHITE); // Texto blanco
        cotizacionesButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelInferior.add(cotizacionesButton);
        panelInferior.setBackground(new Color(235,247,254));
        panelPrincipal.add(panelInferior,BorderLayout.SOUTH);
        
        // Agregar el panel a la ventana
        add(panelPrincipal);
        
        // LISTENERS
        cerrarSesionButton.addActionListener(e ->{
        	mostrarMensaje("Cerrando Sesión...");
        	dispose();
        	controlador.abrirVentanaLogin();
        });
        
        generarDatosPruebaButton.addActionListener(e ->{
        	controlador.generarDatosPrueba(usuario.getId());
        	recargarTablaActivos(usuario.getId());
        });

        exportarCSVButton.addActionListener(e ->{
        	controlador.generarArchivoCSV(activosUsuario);
        	mostrarMensaje("Archivo CSV generado con éxito!");
        });
        
        misOperacionesButton.addActionListener(e ->{
        	dispose();
        	controlador.abrirVentanaOperaciones();
        });
        cotizacionesButton.addActionListener(e ->{
        	dispose();
        	controlador.abrirVentanaCotizaciones();
        });
        
    }
    
    // Método para actualizar la tabla al generar datos de prueba
    private void recargarTablaActivos(int idUsuario) {
    	// Obtener el modelo actual de la tabla
        ModeloTablaMonedas modelo = (ModeloTablaMonedas) tablaActivos.getModel();
	    
        // Limpiar el modelo existente
        modelo.setRowCount(0);
	    
        // Crear una lista para las filas
        Double balance=0.0;
        Double valorDolarARS=1000.0; //valor de 1 USD en la moneda fiduciaria
        // Obtener los activos actualizados del usuario
        for (Activo activo : controlador.devolverActivosUsuario(idUsuario)) {
            Moneda moneda = controlador.devolverMoneda(activo.getIdMoneda());
    		balance+=(activo.getCantidad()*moneda.getValorEnDolar()*valorDolarARS);

            // Agregar filas al modelo
    		modelo.addRow(new Object[]{
                new ImageIcon(getClass().getResource("/imagenes/" + moneda.getNombreIcono())),
                moneda.getNombre() + "(" + moneda.getNomenclatura() + ")",
                activo.getCantidad()
            });
        }
        
        tablaActivos.revalidate();          // Reorganiza la tabla
        tablaActivos.repaint();             // Redibuja la tabla
        balanceLabel.setText("Balance: ARS "+balance); //Actualiza balance en ARS
    }
    
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
}
