package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Arrays;

public class Registrazione {

    private JFrame frameLogin;
    private JPanel registrazionePanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton okButton;
    private  static  final String REG_ERROR = "Registrazione non effettuata";

    JFrame regFrame;
    /**
     * @param frameChiamante Per riapire la schermata di login al termine
     * @param controller per comunicare la registrazione al controller
     **/
    public Registrazione(JFrame frameChiamante, Controller controller) {

        frameLogin = frameChiamante;
        regFrame = new JFrame("Registrazione");
        regFrame.setContentPane(registrazionePanel);
        regFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        regFrame.pack();
        regFrame.setVisible(true);

            okButton.addActionListener(e -> {
                    String nome = textField1.getText();
                    char[] password = passwordField1.getPassword();
                    char[] confermaPassword = passwordField2.getPassword();

                    if (!nome.trim().isEmpty() && password.length != 0 && confermaPassword.length != 0) {

                        if (Arrays.equals(password, confermaPassword)) {
                                try {
                                    controller.addUtente(nome, password);
                                    JOptionPane.showMessageDialog(regFrame, "Registrazione completata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                                    regFrame.dispose();
                                    frameLogin.setVisible(true);

                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(regFrame, "Utente già registrato", REG_ERROR, JOptionPane.INFORMATION_MESSAGE);
                                }



                            } else {
                            JOptionPane.showMessageDialog(regFrame, "Password non corrispondenti", REG_ERROR, JOptionPane.INFORMATION_MESSAGE);                        }


                        } else {
                            JOptionPane.showMessageDialog(regFrame, "Utente e Password non possono essere vuoti", REG_ERROR, JOptionPane.INFORMATION_MESSAGE);                        }


                    }
            );
    }



}
