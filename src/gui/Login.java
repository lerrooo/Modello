package gui;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class Login {
    private final Controller controller;
    private static JFrame mainFrame;
    private JPanel LoginPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton accediButton;
    private JButton registratiButton;

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("MainGUI");
        frame.setContentPane(new Login().LoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500, 300);
        mainFrame = frame;
    }


    public Login() throws SQLException {
        controller = new Controller();

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(textField1.getText().isEmpty() || passwordField1.getPassword().length == 0){
                    JOptionPane.showMessageDialog(mainFrame, "Inserisci nome utente e password", "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                try {
                    boolean loginEffettuato = controller.LoginUtente(textField1.getText(), passwordField1.getPassword());
                    System.out.println(loginEffettuato);
                    if (loginEffettuato){
                        System.out.println("utente trovato");
                        MainGUI mainGuiAfterLogin = new MainGUI(mainFrame, controller);
                        mainFrame.setVisible(false);
                        mainFrame.dispose();
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

//                controller.setUtenteLoggato(utenteTemp);

//                if(utenteTemp != null){
//                    controller.setUtenteLoggato(utenteTemp);
//                    MainGUI mainGuiAfterLogin = new MainGUI(mainFrame, controller);
//                    mainFrame.setVisible(false);
//                    mainFrame.dispose();
//
//                 System.out.println("utente trovato");
//                }else {
//                    JOptionPane.showMessageDialog(mainFrame, "Utente non trovato", "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
//                }
            }
        });
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registrazione registrazioneGUI = new Registrazione(mainFrame, controller);
                registrazioneGUI.regFrame.setVisible(true);
                mainFrame.setVisible(false);
            }
        });
    }
}