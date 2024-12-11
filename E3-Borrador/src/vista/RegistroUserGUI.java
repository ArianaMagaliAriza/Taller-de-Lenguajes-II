package vista;

import javax.swing.*;

import controlador.Controladores;

import java.awt.*;

public class RegistroUserGUI extends JFrame {
    // Componentes de la GUI para el registro
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField nombresField;
    private JTextField apellidosField;
    private JCheckBox terminosCheckBox;
    private JButton registerButton;

    public RegistroUserGUI() {
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
        
        // Casillero de términos y condiciones
        terminosCheckBox = new JCheckBox("Acepto los términos y condiciones");
        terminosCheckBox.setBackground(new Color(245, 245, 220));
        terminosCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(terminosCheckBox, gbc);

        // Botón de registrar
        registerButton = new JButton("Registrar");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setPreferredSize(new Dimension(140, 40));
        registerButton.setBackground(new Color(203, 123, 42 ));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(registerButton, gbc);

        // Listener para el botón de registrar
        registerButton.addActionListener(e -> handleRegister());

        // Agregar el panel a la ventana
        add(mainPanel);
    }

    // Método para manejar el registro
    private void handleRegister() {
    	Controladores contr= new Controladores(); 
        String nombres = nombresField.getText();
        String apellidos = apellidosField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        boolean terminos = terminosCheckBox.isSelected();

        if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if(!terminos){
        	JOptionPane.showMessageDialog(this, "Debe aceptar los términos y condiciones para registrarse.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
        	contr.RegistrarUser(nombres, apellidos, email, password, terminos);
        	JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }

        //ACÁ SE TENDRÍA QUE ABRIR LA VENTANA MIS ACTIVOS Y OCULTAR LA DE REGISTRO

    }
}
