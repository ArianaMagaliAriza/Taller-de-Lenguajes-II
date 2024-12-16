package vista;


import java.awt.*;
import java.util.List;
import javax.swing.*;
import modelo.Moneda;
import modelo.Persona;
import controlador.Controladores;
import excepciones.*;

public class CompraGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private Controladores controlador;
    private Moneda fiatSeleccionada;
    private Moneda criptoSeleccionada;

    // Componentes UI
    private JPanel mainPanel;
    private JLabel userLabel, stockLabel, precioLabel, montoLabel, resultadoLabel;
    private JTextField montoField;
    private JComboBox<String> monedaBox;
    private JButton convertirButton, realizarCompraButton, cancelarButton, cerrarSesionButton;

    public CompraGUI(Controladores controlador, String cripto, String precio) {
        this.controlador = controlador;
        inicializarComponentes();
        cargarDatosIniciales(cripto, precio);
        configurarListeners(precio);
    }

    private void inicializarComponentes() {
        setTitle("Billetera Virtual - Compra");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Panel Superior
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userLabel = new JLabel();
        cerrarSesionButton = new JButton("Cerrar sesión");
        userPanel.add(userLabel);
        userPanel.add(cerrarSesionButton);
        mainPanel.add(userPanel, BorderLayout.NORTH);

        // Panel Central
        JPanel centralPanel = new JPanel(null);

        stockLabel = new JLabel();
        stockLabel.setFont(new Font("Arial", Font.BOLD, 14));
        stockLabel.setBounds(20, 20, 350, 30);

        precioLabel = new JLabel();
        precioLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        precioLabel.setBounds(20, 60, 350, 30);

        montoLabel = new JLabel("Quiero comprar con:");
        montoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        montoLabel.setBounds(20, 100, 150, 30);

        montoField = new JTextField();
        montoField.setBounds(170, 100, 100, 30);

        monedaBox = new JComboBox<>();
        monedaBox.setBounds(280, 100, 80, 30);

        convertirButton = new JButton("Convertir");
        convertirButton.setBounds(370, 100, 100, 30);

        resultadoLabel = new JLabel("Equivale a: ");
        resultadoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        resultadoLabel.setBounds(20, 140, 350, 30);

        centralPanel.add(stockLabel);
        centralPanel.add(precioLabel);
        centralPanel.add(montoLabel);
        centralPanel.add(montoField);
        centralPanel.add(monedaBox);
        centralPanel.add(convertirButton);
        centralPanel.add(resultadoLabel);

        mainPanel.add(centralPanel, BorderLayout.CENTER);

        // Panel Inferior
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        realizarCompraButton = new JButton("Realizar Compra");
        cancelarButton = new JButton("Cancelar");
        bottomPanel.add(realizarCompraButton);
        bottomPanel.add(cancelarButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void cargarDatosIniciales(String cripto, String precio) {
        controlador.cargarDatosCompra(this,cripto,precio);
    }
    public void cargarUserLabel(Persona persona) {
    	userLabel.setText(persona.getNombres() + " " + persona.getApellidos());
    }
    public void cargarMonedas(List<Moneda> monedasFiat) {
    	for (Moneda moneda : monedasFiat) {
            monedaBox.addItem(moneda.getNomenclatura());
        }
    }
    public void cargarStockPrecio(String cripto,String precio) {
        List<Moneda> monedas = controlador.retornarMonedas();
        for (Moneda moneda : monedas) {
            if (moneda.getNombre().equals(cripto)) {
                criptoSeleccionada = moneda;
                break;
            }
        }

        stockLabel.setText("Stock disponible: " + criptoSeleccionada.getStock() + " " + criptoSeleccionada.getNomenclatura());
        precioLabel.setText("Precio de Compra: $" + precio);
    }

    private void configurarListeners(String precio) {
        convertirButton.addActionListener(e -> convertirMonto(precio));

        realizarCompraButton.addActionListener(e -> realizarCompra(precio));

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
                    fiatSeleccionada = moneda;
                    break;
                }
            }
        });
    }
    private void convertirMonto(String precio) {
        double monto=Double.parseDouble(montoField.getText());
        controlador.convertirMonto(this, monto, fiatSeleccionada, precio);
    }
    public void cargarResultadoLabel(String resultado) {
    	resultadoLabel.setText(resultado+" "+criptoSeleccionada.getNomenclatura());
    }
    
    private void realizarCompra(String precio) {
    	try {
        	double monto = Double.parseDouble(montoField.getText());
            if (monto <= 0) {
                mostrarMensaje("El monto debe ser mayor a 0.");
                return;
            }

            if (fiatSeleccionada == null) {
            	mostrarMensaje("Seleccione una moneda válida antes de realizar la compra.");
                return;
            }

            double valorEnDolar = fiatSeleccionada.getValorEnDolar();
            double cotizacionCripto = Double.parseDouble(precio);
            double cantidadCripto = (monto / valorEnDolar) / cotizacionCripto;

            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    String.format("¿Deseas comprar %.6f %s por %s %s?",
                            cantidadCripto, criptoSeleccionada.getNomenclatura(), monto, fiatSeleccionada.getNomenclatura()),
                    "Confirmar Compra",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
            	controlador.realizarCompra(this,precio,monto,cantidadCripto,fiatSeleccionada,criptoSeleccionada);
        
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
