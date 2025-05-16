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

    public Registrazione(){

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("asdasd");
                registraUtente(textField1.toString(), passwordField1.getPassword());
                frameLogin.setVisible(true);
                regFrame.setVisible(false);

            }
        });
    }
    public Registrazione(JFrame frameChiamante, ArrayList<Utente> utenti) {
        frameLogin = frameChiamante;
        utentiTemp = utenti;
        regFrame = new JFrame("Registrazione");
        regFrame.setContentPane(RegistrazionePanel);
        regFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        regFrame.pack();
        regFrame.setVisible(true);

    }

    private void registraUtente(String nomeTemp, char[] Password){
        String passwordTemp = new String(Password);
        if(!nomeTemp.isEmpty() && !passwordTemp.isEmpty()){
            Utente nuovoUtente = new Utente(nomeTemp,passwordTemp);
            utentiTemp.add(nuovoUtente);
        }

    }
}
