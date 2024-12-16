package vista;

import javax.swing.*;

import controlador.Controladores;
import excepciones.RegistroException;

import java.awt.*;

public class RegistroUserGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Componentes de la GUI para el registro
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField nombresField;
    private JTextField apellidosField;
    private JButton registerButton;
    private JCheckBox terminos;
    private Controladores controlador;
    
    public RegistroUserGUI(Controladores controlador) {
    	this.controlador=controlador;
        // Configuración básica de la ventana
        setTitle("Billetera Virtual - Registrarse");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setLocationRelativeTo(null); // Centra la ventana

        // Crear el panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 220));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Etiquetas y campos de texto para los datos de registro
        JLabel nombresLabel = new JLabel("Nombres:");
        nombresLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(nombresLabel, gbc);

        nombresField = new JTextField(20);
        nombresField.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(nombresField, gbc);

        JLabel apellidosLabel = new JLabel("Apellidos:");
        apellidosLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(apellidosLabel, gbc);

        apellidosField = new JTextField(20);
        apellidosField.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(apellidosField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(passwordField, gbc);

        // Checkbox aceptar terminos y condiciones
        terminos = new JCheckBox("Aceptar términos y condiciones");
        terminos.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(terminos, gbc);
        
        // Botón de registrar
        registerButton = new JButton("Registrar");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setPreferredSize(new Dimension(140, 40));
        registerButton.setBackground(new Color(203, 123, 42 ));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(registerButton, gbc);

        // Agregar el panel a la ventana
        add(mainPanel);
        
     // Listener para el botón de registrar
        registerButton.addActionListener(e ->{
                String nombres = getNombres();
                String apellidos = getApellidos();
                String email = getEmail();
                String password = new String(getPassword());
                Boolean terminos = getTerminos();
                
                try {
                	controlador.handleRegistro(this,nombres,apellidos,email,password,terminos);
                    // Registro exitoso
                    mostrarMensaje("Usuario registrado con éxito!");
                	// Cierra la ventana de registro y abre la de login
                    dispose();
                	controlador.abrirVentanaLogin();
                }catch(RegistroException e1) {
                	mostrarMensaje("Error: "+e1.getMessage());
                }
                
        });
    }
    
 

    // Getters de campos
    public String getNombres() {
    	return nombresField.getText();
    }
    public String getApellidos() {
    	return apellidosField.getText();
    }
    public String getEmail() {
    	return emailField.getText();
    }
    public char[] getPassword() {
        return passwordField.getPassword();
    }
    public Boolean getTerminos() {
    	return terminos.isSelected();
    }
    
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
