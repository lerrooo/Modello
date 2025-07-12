package gui;

import Controller.Controller;
import model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
                    String password = new String(passwordField1.getPassword());
                    String confermaPassword = new String(passwordField2.getPassword());

                    if (!nome.trim().isEmpty() && !password.isEmpty() && !confermaPassword.isEmpty()) {
                        if (password.equals(confermaPassword)) {
                            Utente registrato  = registraUtente(nome, password);
                            if (registrato != null) {

                                try {
                                    controller.addUtente(registrato);
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
                    } else {
                        JOptionPane.showMessageDialog(regFrame, "Inserisci nome utente e password.", "--Errore di gui.Registrazione--", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            );
    }
        private Utente registraUtente (String nomeTemp, String passwordTemp){

            if (!nomeTemp.trim().isEmpty() && !passwordTemp.isEmpty()) {

                return new Utente(nomeTemp, passwordTemp);
            }
            return null;
        }


}
