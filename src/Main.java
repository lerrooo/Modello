import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ArrayList<Utente> utenti = new ArrayList<Utente>();
        Utente utenteLoggato = null;
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
                    utenteLoggato = loginUtente(utenti);
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
        } while (sceltaOpzioni != 2 && utenteLoggato == null);

        if(utenteLoggato != null){
            System.out.println("Benvenuto " + utenteLoggato.nome);
            stampaBacheche(utenteLoggato);
        }

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

  private static Utente loginUtente(ArrayList<Utente> utenti){

      Scanner input = new Scanner(System.in);

      String nomeLogin;
      String passwordLogin;

      System.out.print("Inserisci il nome utente: ");
      nomeLogin = input.nextLine();

      System.out.print("Inserisci la password:");
      passwordLogin = input.nextLine();

        for (Utente utenteTemp : utenti){
            if(utenteTemp.nome.equals(nomeLogin) && utenteTemp.password.equals(passwordLogin))
                return utenteTemp;
        }
        return null;
  }

  private static void stampaBacheche(Utente utente){
        for(Bacheca tempBacheca : utente.bacheche){
            System.out.println("Nome bacheca: " + tempBacheca.titolo + "\nDescrizione bacheca: " + tempBacheca.descrizione);

            for(ToDo tempToDo : tempBacheca.toDoList){
                System.out.println("Nome ToDo: " + tempToDo.titolo + " Descrizione: " + tempToDo.descrizione);
            }

        }
  }
}