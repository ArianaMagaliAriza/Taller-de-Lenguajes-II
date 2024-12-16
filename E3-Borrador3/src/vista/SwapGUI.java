package vista;

import java.awt.*;
import javax.swing.*;
import java.util.List;

import controlador.Controladores;
import excepciones.OperacionException;
import modelo.*;

public class SwapGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private Controladores controlador;
    private Moneda criptoSeleccionada;
    private Moneda criptoEsperada;

    // Componentes UI
    private JPanel mainPanel;
    private JLabel userLabel, stockLabel, precioLabel, montoLabel, resultadoLabel;
    private JTextField montoField;
    private JComboBox<String> monedaBox;
    private JButton convertirButton, realizarSwapButton, cancelarButton, cerrarSesionButton;

    public SwapGUI(Controladores controlador, String cripto, String precio) {
        this.controlador = controlador;
        inicializarComponentes();
        cargarDatosIniciales(cripto, precio);
        configurarListeners(precio);
    }

    private void inicializarComponentes() {
        setTitle("Billetera Virtual - Swap");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Panel Superior
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userLabel = new JLabel();
        cerrarSesionButton = new JButton("Cerrar sesión");
        cerrarSesionButton.setBackground(new Color(0, 123, 255)); // Azul
        cerrarSesionButton.setForeground(Color.WHITE); // Texto blanco
        cerrarSesionButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        userPanel.add(userLabel);
        userPanel.add(cerrarSesionButton);
        userPanel.setBackground(new Color(235,247,254));
        mainPanel.add(userPanel, BorderLayout.NORTH);

        // Panel Central
        JPanel centralPanel = new JPanel(null);

        stockLabel = new JLabel();
        stockLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        stockLabel.setBounds(20, 20, 350, 30);

        precioLabel = new JLabel();
        precioLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        precioLabel.setBounds(20, 60, 350, 30);

        montoLabel = new JLabel("Swap con:");
        montoLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        montoLabel.setBounds(20, 100, 150, 30);

        montoField = new JTextField();
        montoField.setBounds(170, 100, 100, 30);

        monedaBox = new JComboBox<>();
        monedaBox.setBounds(280, 100, 80, 30);
        monedaBox.setBackground(new Color(0, 123, 255)); // Azul
        monedaBox.setForeground(Color.BLACK); // Texto blanco
        monedaBox.setFont(new Font("SansSerif", Font.BOLD, 12));

        convertirButton = new JButton("Convertir");
        convertirButton.setBounds(370, 100, 100, 30);
        convertirButton.setBackground(Color.LIGHT_GRAY); // Gris
        convertirButton.setForeground(Color.BLACK); // Texto blanco
        convertirButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        resultadoLabel = new JLabel("Equivale a: ");
        resultadoLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        resultadoLabel.setBounds(20, 140, 350, 30);

        centralPanel.add(stockLabel);
        centralPanel.add(precioLabel);
        centralPanel.add(montoLabel);
        centralPanel.add(montoField);
        centralPanel.add(monedaBox);
        centralPanel.add(convertirButton);
        centralPanel.add(resultadoLabel);
        centralPanel.setBackground(new Color(235,247,254));
	    
        mainPanel.add(centralPanel, BorderLayout.CENTER);

        // Panel Inferior
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        realizarSwapButton = new JButton("Realizar Swap");
        realizarSwapButton.setBackground(new Color(0, 123, 255)); // Azul
        realizarSwapButton.setForeground(Color.WHITE); // Texto blanco
        realizarSwapButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        cancelarButton = new JButton("Cancelar");
        cancelarButton.setBackground(new Color(0, 123, 255)); // Azul
        cancelarButton.setForeground(Color.WHITE); // Texto blanco
        cancelarButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        bottomPanel.add(realizarSwapButton);
        bottomPanel.add(cancelarButton);
        bottomPanel.setBackground(new Color(235,247,254));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void cargarDatosIniciales(String cripto, String precio) {
        controlador.cargarDatosSwap(this,cripto,precio);
    }
    public void cargarUserLabel(Persona persona) {
    	userLabel.setText(persona.getNombres() + " " + persona.getApellidos());
    }
    public void cargarMonedas(List<Moneda> monedasCripto) {
    	for (Moneda moneda : monedasCripto) {
            monedaBox.addItem(moneda.getNomenclatura());
        }
    }
    public void cargarStockPrecio(String cripto,String precio) {
        List<Moneda> monedas = controlador.retornarMonedas();
        for (Moneda moneda : monedas) {
            if (moneda.getNombre().equals(cripto)) {
                criptoEsperada = moneda;
                break;
            }
        }

        stockLabel.setText("Stock disponible: " + criptoEsperada.getStock() + " " + criptoEsperada.getNomenclatura());
        precioLabel.setText("Precio de Compra: $" + precio);
    }

    private void configurarListeners(String precio) {
        convertirButton.addActionListener(e -> convertirMonto(precio));

        realizarSwapButton.addActionListener(e -> realizarSwap(precio));

        cancelarButton.addActionListener(e -> {
            dispose();
            controlador.abrirVentanaCotizaciones();
        });

        cerrarSesionButton.addActionListener(e -> {
            dispose();
            controlador.abrirVentanaLogin();
        });

        monedaBox.addActionListener(e -> {
            String seleccion = (String) monedaBox.getSelectedItem();
            List<Moneda> monedas = controlador.retornarMonedas();
            for (Moneda moneda : monedas) {
                if (moneda.getNomenclatura().equals(seleccion)) {
                    criptoSeleccionada = moneda;
                    break;
                }
            }
        });
    }
    private void convertirMonto(String precio) {
        double monto=Double.parseDouble(montoField.getText());
        controlador.convertirMontoCripto(this, monto, criptoSeleccionada, precio);
    }
    public void cargarResultadoLabel(String resultado) {
    	resultadoLabel.setText(resultado+" "+criptoEsperada.getNomenclatura());
    }
    
    private void realizarSwap(String precio) {
    	try {
        	double monto = Double.parseDouble(montoField.getText());
            if (monto <= 0) {
                mostrarMensaje("El monto debe ser mayor a 0.");
                return;
            }

            if (criptoSeleccionada == null) {
            	mostrarMensaje("Seleccione una moneda válida antes de realizar la compra.");
                return;
            }

            double valorEnDolar = criptoSeleccionada.getValorEnDolar();
            double cotizacionCripto = Double.parseDouble(precio);
            double cantidadCripto =  (monto * valorEnDolar) / cotizacionCripto;

            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    String.format("¿Deseas hacer el Swap de %.6f %s a %s %s?",
                            monto, criptoSeleccionada.getNomenclatura(), cantidadCripto, criptoEsperada.getNomenclatura()),
                    "Confirmar Swap",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
            	controlador.realizarSwap(this,precio,monto,cantidadCripto,criptoSeleccionada,criptoEsperada);
        
                dispose();
                controlador.abrirVentanaCotizaciones();
            }
        } catch (NumberFormatException ex) {
            mostrarMensaje("Por favor, ingrese un número válido en el campo de monto.");
        } catch (OperacionException e) {
			mostrarMensaje(e.getMessage());
		} 

    }
    
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
