import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class Registrazione {

    private ArrayList<Utente> utentiTemp;
    private JFrame frameLogin;
    private JPanel RegistrazionePanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton button1;
    JFrame regFrame;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainGUI");
        frame.setContentPane(new Registrazione().RegistrazionePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public Registrazione(){}
        public Registrazione(JFrame frameChiamante, ArrayList < Utente > utenti){
            frameLogin = frameChiamante;
            utentiTemp = utenti;
            regFrame = new JFrame("Registrazione");
            regFrame.setContentPane(RegistrazionePanel);
            regFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            regFrame.pack();
            regFrame.setVisible(true);

            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nome = textField1.getText();
                    char[] passchars = passwordField1.getPassword();
                    String password = new String(passchars);
                    if (!nome.trim().isEmpty() && passchars.length > 0) {
                        registraUtente(nome, passchars);
                        JOptionPane.showMessageDialog(regFrame, "Registrazione completata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        regFrame.dispose();
                        frameLogin.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(regFrame, "Inserisci nome utente e password.", "Errore di Registrazione", JOptionPane.ERROR_MESSAGE);
                    }

                }
            });
    }
        private void registraUtente (String nomeTemp,char[] Password){
            String passwordTemp = new String(Password);
            if (!nomeTemp.trim().isEmpty() && passwordTemp.length()>0) {
                Utente nuovoUtente = new Utente(nomeTemp, passwordTemp);
                utentiTemp.add(nuovoUtente);
            }
        }
    }
