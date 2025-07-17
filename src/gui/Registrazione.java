package gui;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

public class Registrazione {
;
    private JFrame frameLogin;
    private JPanel RegistrazionePanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton OK;

    JFrame regFrame;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Registrazione");
        frame.setContentPane(new Registrazione().RegistrazionePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public Registrazione(){}

    public Registrazione(JFrame frameChiamante, Controller controller){

            frameLogin = frameChiamante;
            regFrame = new JFrame("Registrazione");
            regFrame.setContentPane(RegistrazionePanel);
            regFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            regFrame.pack();
            regFrame.setVisible(true);


            OK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nome = textField1.getText();
                    char[] password = passwordField1.getPassword();
                    char[] confermaPassword = passwordField2.getPassword();

                    if (!nome.trim().isEmpty() && password.length != 0 && confermaPassword.length != 0) {

                        if (Arrays.equals(password, confermaPassword)) {
                                try {
                                    controller.addUtente(nome, password);
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }

                                JOptionPane.showMessageDialog(regFrame, "Registrazione completata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                                regFrame.dispose();
                                frameLogin.setVisible(true);

                            } else {
                                JOptionPane.showMessageDialog(regFrame, "Utente già registrato", "Registrazione non effettuata", JOptionPane.INFORMATION_MESSAGE);

                            }
                        } else {
                            JOptionPane.showMessageDialog(regFrame, "Password non corrispondenti", "Registrazione non effettuata", JOptionPane.INFORMATION_MESSAGE);                        }
                    }

            }
            );
    }



}
