package gui;

import controller.Controller;

import javax.swing.*;
import java.sql.SQLException;
/**
 * Si occupa della gestione della GUI del Login
 * Utilizza i componenti del form per comunicare con il controller
 *
 */
public class Login {
    private final Controller controller;
    private static JFrame mainFrame;
    private JPanel loginPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton accediButton;
    private JButton registratiButton;

    /**
     * Un metodo statico per riaprire il Login in seguito
     */
    public static void showLogin() {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().loginPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500, 300);
        mainFrame = frame;
    }
    /**
     * Costrutture della GUI che si occupa di creare il controller
     */
    public Login() {
        controller = new Controller();

        accediButton.addActionListener(e -> {
            if(textField1.getText().isEmpty() || passwordField1.getPassword().length == 0){
                JOptionPane.showMessageDialog(mainFrame, "Inserisci nome utente e password", "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                boolean loginEffettuato = controller.loginUtente(textField1.getText(), passwordField1.getPassword());
                if (loginEffettuato){
                    new MainGUI(controller);
                    mainFrame.dispose(); // Chiude login

                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Utente non trovato", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                //Catch block
            }
        });

        registratiButton.addActionListener(e -> {
            Registrazione registrazioneGUI = new Registrazione(mainFrame, controller);
            registrazioneGUI.regFrame.setVisible(true);
            mainFrame.setVisible(false);
        });
    }
}
