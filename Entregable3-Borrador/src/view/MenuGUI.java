package view;

import javax.swing.*;
import servicios.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGUI extends JFrame implements ActionListener {

	private JTextArea outputArea;

    public MenuGUI() {
        setTitle("Billetera Virtual");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con layout en cuadrícula
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        // Área de salida de texto
        outputArea = new JTextArea(5, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Añadir panel y área de texto al frame
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(scrollPane, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuGUI().setVisible(true);
        });
    }
}