import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Login {
//SWING UI
    public MainGUI MainGUI;
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
         mainFrame = frame;
    }
    public Login() {
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(loginUtente(utenti, textField1.getText(), passwordField1.getPassword()) != null){
                    MainGUI secondaGUI = new MainGUI(mainFrame);
                    secondaGUI.frame.setVisible(true);
                    mainFrame.setVisible(false);
                 System.out.println("utente trovato");
                }else {
                    System.out.println("utente non trovato");
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