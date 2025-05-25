import model.Bacheca;
import model.ToDo;
import model.Utente;
import model.titoloBacheca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        ArrayList<Utente> utenti = new ArrayList<Utente>();
        Utente utenteLoggato = null;
        Scanner input;
        int sceltaOpzioni=-1;

        do {  try{

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
          } catch(Exception e){
                System.out.println("inserisci un numero valido");
            }
        } while (sceltaOpzioni != 2 && utenteLoggato == null);



        if(utenteLoggato != null){
            System.out.println("Benvenuto " + utenteLoggato.nome);

            do { try{
                System.out.println("\nquale opzione scegli? :" +
                        "\n0. Stampa bacheche" +
                        "\n1. Crea bacheca" +
                        "\n2. Elimina bacheca" +
                        "\n3. Modifica bacheca" +
                        "\n4. Aggiungi model.ToDo" +
                        "\n5. Elimina model.ToDo" +
                        "\n6. Modifica model.ToDo" +
                        "\n7. Cambia posizione model.ToDo" +
                        "\n8. Scambia model.ToDo" +
                        "\n9. Completa model.ToDo" +
                        "\n10.Cerca model.ToDo" +
                        "\n11.Esci dal programma"
                );
                input = new Scanner(System.in);
                sceltaOpzioni = input.nextInt();
                input.nextLine();

                switch (sceltaOpzioni) {
                    case 0:
                        stampaBacheche(utenteLoggato);
                        break;
                    case 1:
                        aggiungiBacheca(utenteLoggato);
                        break;
                    case 2:
                        eliminaBacheca(utenteLoggato);
                        break;
                    case 3:
                        modificaBacheca(utenteLoggato);
                        break;
                    case 4:
                        aggiungiToDo(utenteLoggato);
                        break;
                    case 5:
                        eliminaToDo(utenteLoggato);
                        break;
                    case 6:
                        modificaToDo(utenteLoggato);
                        break;
                    case 7:
                        cambiaPosizioneToDo(utenteLoggato);
                        break;
                    case 8:
                        swapPosizioneToDo(utenteLoggato);
                        break;
                    case 9:
                        completaToDo(utenteLoggato);
                        break;
                    case 10:
                        cercaToDo(utenteLoggato);
                        break;
                    default:
                        System.out.println("inserisci il numero corretto");
                        break;
                }
             }
             catch(Exception e){
                System.out.println("inserisci un numero valido");
             }
            } while (sceltaOpzioni != 6);


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
            System.out.println("\nNOME BACHECA: " + tempBacheca.titolo + "\nDescrizione bacheca: " + tempBacheca.descrizione);

            for(ToDo tempToDo : tempBacheca.toDoList){
                System.out.printf("\nNOME TODO: " + tempToDo.titolo + " \nDescrizione: " + tempToDo.descrizione + "\nstato:");
                if(tempToDo.completato)
                    System.out.println(" completato");
                else System.out.println("non completato");
            }

        }
  }

  private static void aggiungiBacheca(Utente utenteLoggato){
        if(utenteLoggato.bacheche.size() < 3){
            Scanner input = new Scanner(System.in);

            System.out.println("Inserisci il nome della bacheca");
            String nomeTemp = input.nextLine();

            System.out.println("Inserisci la descrizione della bacheca");
            String descTemp = input.nextLine();

            System.out.println("Inserisci la tipologia di bacheca + " +
                    "\n1. Università" +
                    "\n2. Lavoro" +
                    "\n3. Tempo libero"
            );
            int scelta = input.nextInt();
            titoloBacheca titoloTemp = null;
            switch (scelta){
                case 1:
                    titoloTemp = titoloBacheca.universita;
                    break;
                case 2:
                    titoloTemp = titoloBacheca.lavoro;
                    break;
                case 3:
                    titoloTemp = titoloBacheca.tempoLibero;
                    break;
                default:
                    System.out.println("Numero inserito non corretto");
                    break;
            }

            utenteLoggato.createBacheca(nomeTemp, descTemp, titoloTemp);
            
        }else
            System.out.println("Limite raggiunto");

  }

  private static void eliminaBacheca(Utente utenteLoggato){
        Scanner input = new Scanner(System.in);

        System.out.println("Inserisci il numero della bacheca da cancellare:");
        for(int i = 0; i < utenteLoggato.bacheche.size(); i++){
            System.out.println(i + ". " + utenteLoggato.bacheche.get(i).titolo);
        }

        int scelta = input.nextInt();
        utenteLoggato.bacheche.remove(scelta);


  }

  private static void modificaBacheca(Utente utenteLoggato){
      Scanner input = new Scanner(System.in);

      System.out.println("Inserisci il numero della bacheca da modificare:");
      for(int i = 0; i < utenteLoggato.bacheche.size(); i++){
          System.out.println(i + ". " + utenteLoggato.bacheche.get(i).titolo);
      }

      int scelta = input.nextInt();

      Bacheca bachecaTemp = utenteLoggato.bacheche.get(scelta);

      System.out.println("Inserisci il nome della bacheca");
      bachecaTemp.titolo = input.nextLine();

      System.out.println("Inserisci la descrizione della bacheca");
      bachecaTemp.descrizione = input.nextLine();

      System.out.println("Inserisci la tipologia di bacheca + " +
              "\n1. Università" +
              "\n2. Lavoro" +
              "\n3. Tempo libero"
      );
      int sceltaTipo = input.nextInt();
      titoloBacheca titoloTemp = null;
      switch (sceltaTipo){
          case 1:

              bachecaTemp.tipoBacheca = titoloBacheca.universita;
              break;
          case 2:
              bachecaTemp.tipoBacheca = titoloBacheca.lavoro;
              break;
          case 3:
              bachecaTemp.tipoBacheca = titoloBacheca.tempoLibero;
              break;
          default:
              System.out.println("Numero inserito non corretto");
              break;
      }


  }

  private static void aggiungiToDo(Utente utenteLoggato){
      Scanner input = new Scanner(System.in);

      System.out.println("Inserisci il numero della bacheca a cui aggiungere un model.ToDo:");
      for(int i = 0; i < utenteLoggato.bacheche.size(); i++){
          System.out.println(i + ". " + utenteLoggato.bacheche.get(i).titolo);
      }

      int scelta = input.nextInt();
      input.nextLine();

      System.out.println("Inserisci il titolo del model.ToDo");
      String titoloTemp = input.nextLine();

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

      //Data temporanea non da input
      LocalDate dataTemp = LocalDate.parse("01/01/2000", formatter);

      System.out.println("Inserisci la descrizione del model.ToDo");
      String descTemp = input.nextLine();

      utenteLoggato.bacheche.get(scelta).toDoList.add(new ToDo(titoloTemp, dataTemp, descTemp, utenteLoggato));

  }

  private static void eliminaToDo(Utente utenteLoggato){

        Scanner input = new Scanner(System.in);

        System.out.println("Inserisci il nome del model.ToDo da eliminare");
        String nomeToDo = input.nextLine();

        for(Bacheca bachecaTemp : utenteLoggato.bacheche){
            for(int i = 0; i < bachecaTemp.toDoList.size(); i++){
                if(bachecaTemp.toDoList.get(i).titolo.equals(nomeToDo)){
                    bachecaTemp.deleteToDo(i);
                    return;
                }

            }
        }

  }
  private static void modificaToDo(Utente utenteLoggato){
      String titoloTemp;
      String descrizioneTemp;

      Scanner input = new Scanner(System.in);

      System.out.println("Inserisci il nome del model.ToDo da modificare");
      String nomeToDo = input.nextLine();
      ToDo tempToDo = null;
      for(Bacheca bachecaTemp : utenteLoggato.bacheche){
          for(int i = 0; i < bachecaTemp.toDoList.size(); i++){
              if(bachecaTemp.toDoList.get(i).titolo.equals(nomeToDo)){
                  tempToDo = bachecaTemp.toDoList.get(i);
              }
          }
      }
      if(tempToDo != null){
          System.out.println("inserisci il nuovo nome:");
          titoloTemp = input.nextLine();
          System.out.println("inserisci la nuova descrizione:");
          descrizioneTemp = input.nextLine();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
          //Data temporanea non da input
          LocalDate dataTemp = LocalDate.parse("01/01/2000", formatter);
          tempToDo.modifyToDo(titoloTemp, dataTemp, descrizioneTemp, utenteLoggato.nome);

      }else System.out.println("- model.ToDo non trovato -");




  }
  private static void cambiaPosizioneToDo(Utente utenteLoggato){
      Scanner input = new Scanner(System.in);
      System.out.println("Inserire il nome del model.ToDo:");
      String nomeToDo = input.nextLine();
      int indexBachecaStart = -1;
      int indexToDoStart = -1;
      int indexBachecaEnd;
      int indexToDoEnd;

      for(int j = 0; j < utenteLoggato.bacheche.size(); j++ ){
          for(int i = 0; i < utenteLoggato.bacheche.get(j).toDoList.size(); i++){
              if(utenteLoggato.bacheche.get(j).toDoList.get(i).titolo.equals(nomeToDo)){
                  indexBachecaStart = j;
                  indexToDoStart = i;
                  System.out.println("indice bacheca:" + indexBachecaStart + "\n indice model.ToDo:" + indexToDoStart );
              }
          }
      }
      if(indexBachecaStart != -1){

          System.out.println("in che bacheca vuoi spostare il model.ToDo? : ");
          indexBachecaEnd=input.nextInt();
          input.nextLine();
          System.out.println("in che posizione della bacheca vuoi spostare il model.ToDo? : ");
          indexToDoEnd=input.nextInt();
          input.nextLine();
          utenteLoggato.changePositionToDo(indexBachecaStart, indexBachecaEnd, indexToDoStart, indexToDoEnd);

      }else System.out.println("- model.ToDo non trovato -");

  }
    private static void swapPosizioneToDo(Utente utenteLoggato){
        int indexToDoStart=-1;
        int indexToDoEnd=-1;
        String nomeTemp;

        Scanner input = new Scanner(System.in);
        System.out.println("Inserisci il numero della bacheca:");
        for(int i = 0; i < utenteLoggato.bacheche.size(); i++){
            System.out.println(i + ". " + utenteLoggato.bacheche.get(i).titolo);
        }
        int scelta = input.nextInt();
        input.nextLine();
        Bacheca bachecaTemp = utenteLoggato.bacheche.get(scelta);
        System.out.println("Inserisci il nome del primo model.ToDo:");
        nomeTemp = input.nextLine();
        for(int i = 0; i < bachecaTemp.toDoList.size(); i++){
            if(bachecaTemp.toDoList.get(i).titolo.equals(nomeTemp)){
                indexToDoStart = i;
            }
        }
        if(indexToDoStart == -1)
            return;
        System.out.println("Inserisci il nome del secondo model.ToDo:");
        nomeTemp = input.nextLine();
        for(int i = 0; i < bachecaTemp.toDoList.size(); i++){
            if(bachecaTemp.toDoList.get(i).titolo.equals(nomeTemp)){
                indexToDoEnd= i;
            }
        }
        if(indexToDoEnd == -1)
            return;
        bachecaTemp.swapToDo(indexToDoStart,indexToDoEnd);

   };
    private static void completaToDo(Utente utenteLoggato){
        int indexBacheca;
        int indexToDo=-1;
        String nomeTemp;
        Scanner input = new Scanner(System.in);
        System.out.println("Inserisci il numero della bacheca:");
        for(int i = 0; i < utenteLoggato.bacheche.size(); i++){
            System.out.println(i + ". " + utenteLoggato.bacheche.get(i).titolo);
        }
        int scelta = input.nextInt();
        input.nextLine();
        Bacheca bachecaTemp = utenteLoggato.bacheche.get(scelta);

        System.out.println("Inserisci il nome del model.ToDo:");
        nomeTemp = input.nextLine();
        for(int i = 0; i < bachecaTemp.toDoList.size(); i++){
            if(bachecaTemp.toDoList.get(i).titolo.equals(nomeTemp)){
                indexToDo = i;
            }
        }
        if(indexToDo == -1)
            return;
        utenteLoggato.completeToDo(scelta,indexToDo);
    }
    private static void cercaToDo(Utente utenteLoggato){
        String nomeToDo;
        Scanner input = new Scanner(System.in);
        System.out.println("Inserisci il nome del model.ToDo:");
        nomeToDo = input.nextLine();

        ToDo toDoTemp = utenteLoggato.searchToDo(nomeToDo);

        if(toDoTemp != null)
            System.out.println("model.ToDo trovato - con scadenza il: " + toDoTemp.visualizeDeadline());

        else System.out.println("model.ToDo non trovato");
    };

}