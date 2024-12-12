package vista;

import javax.swing.*;

import controlador.Controladores;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Componentes de la GUI
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private Controladores controlador;
    
    public LoginGUI(Controladores controlador) {
    	this.controlador=controlador;
        // Configuración básica de la ventana
        setTitle("Billetera Virtual");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Crear el panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 220));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Etiqueta y campo de texto para el email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 15));
        emailField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(emailField, gbc);

        // Etiqueta y campo de texto para la contraseña
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(passwordField, gbc);

        // Botón de login
        loginButton = new JButton("Iniciar sesión");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setPreferredSize(new Dimension(140, 40));
        loginButton.setBackground(new Color(203, 123, 42 )); 
        loginButton.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);

        // Botón de registro
        registerButton = new JButton("Registrarse");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setPreferredSize(new Dimension(140, 40));
        registerButton.setBackground(new Color(203, 123, 42 )); 
        registerButton.setForeground(Color.BLACK);
        gbc.gridy = 6;
        mainPanel.add(registerButton, gbc);

        // Agregar el panel a la ventana
        add(mainPanel);
        
        // Listeners para los botones
        loginButton.addActionListener(e ->{
                String email = getEmail();
                String password = new String(getPassword());
                
                if (email.isEmpty() || password.isEmpty()) {
                    mostrarError("Por favor, complete todos los campos.");
                    return;
                }
                else {
                this.controlador.handleLogin(this,email,password);
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
                controlador.abrirVentanaRegistro();
            }
        });
    
    }

    // Getters de campos
    public String getEmail() {
    	return emailField.getText();
    }
    public char[] getPassword() {
        return passwordField.getPassword();
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}