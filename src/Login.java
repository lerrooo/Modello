import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Login {
//SWING UI
    private static JFrame mainFrame;
    private JPanel LoginPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton accediButton;
    private JButton registratiButton;

//store users
    ArrayList<Utente> utenti = new ArrayList<Utente>();
    Utente utenteLoggato = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainGUI");
        frame.setContentPane(new Login().LoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500, 300);
        mainFrame = frame;
    }
    public Login() {
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(textField1.getText().isEmpty() || passwordField1.getPassword().length == 0){
                    JOptionPane.showMessageDialog(mainFrame, "Inserisci nome utente e password", "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                utenteLoggato = loginUtente(utenti, textField1.getText(), passwordField1.getPassword());

                if(utenteLoggato != null){
                    MainGUI mainGuiAfterLogin = new MainGUI(mainFrame, utenteLoggato);
                    mainFrame.setVisible(false);
                    mainFrame.dispose();

                 System.out.println("utente trovato");
                }else {
                    JOptionPane.showMessageDialog(mainFrame, "Utente non trovato", "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registrazione registrazioneGUI = new Registrazione(mainFrame, utenti);
                registrazioneGUI.regFrame.setVisible(true);
                mainFrame.setVisible(false);
            }
        });
    }
    private static Utente loginUtente(ArrayList<Utente> utenti, String NomeUtente, char[] Password){
        String passwordString = new String(Password);
        for (Utente utenteTemp : utenti){
            if(utenteTemp.nome.equals(NomeUtente) && utenteTemp.password.equals(passwordString))
                return utenteTemp;
        }
        return null;
    }
}