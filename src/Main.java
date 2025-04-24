import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ArrayList<Utente> utenti = new ArrayList<Utente>();
        Utente utenteLoggato;
        Scanner input;
        int sceltaOpzioni;

        do {
            System.out.println("\nquale opzione scegli? :" +
                    "\n0:login:" +
                    "\n1.registrazione"
            );
            input = new Scanner(System.in);
            sceltaOpzioni = input.nextInt();
            input.nextLine();

            switch (sceltaOpzioni) {
                case 0:

                    break;
                case 1:
                    registraUtente(utenti);
                    break;
                case 2:
                    System.out.println("Uscita dal programma");
                    break;

                default:
                    System.out.println("inserisci il numero corretto");
                    break;
            }
        } while (sceltaOpzioni != 2);
    }

    public static void registraUtente(ArrayList<Utente> utenti){
        Scanner input = new Scanner(System.in);

        String nomeTemp;
        String passwordTemp;

        System.out.print("Inserisci il nome utente: ");
        nomeTemp = input.nextLine();


        System.out.print("Inserisci la password:");
        passwordTemp = input.nextLine();
        Utente nuovoUtente = new Utente(nomeTemp,passwordTemp);
        utenti.add(nuovoUtente);
  }
}