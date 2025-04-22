import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ArrayList<Utente> utenti = new ArrayList<Utente>();
        Utente utenteLoggato;
        registraUtente(utenti);
        System.out.println(utenti.get(0).nome);
    }

    public static void registraUtente(ArrayList<Utente> utenti){
        Scanner input = new Scanner(System.in);;

        String nomeTemp;
        String passwordTemp;

        System.out.print("Inserisci il nome utente: ");
        nomeTemp = input.nextLine();


        System.out.print("Inserisci la password:");
        passwordTemp = input.nextLine();
        Utente nuovoUtente= new Utente(nomeTemp,passwordTemp);
        utenti.add(nuovoUtente);
  }
}