import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ToDo {
    String titolo;
    LocalDate dataDiScadenza;
    String URL;
    String descrizione;
    Image Immagine;
    String autore;
    Boolean completato = false;
    Scanner scanner = new Scanner(System.in);

    //String piuttosto che Utenti altrimenti un utente potrebbe accedere alle informazioni di un altro
    ArrayList<String> utentiAssociati = new ArrayList<String>();

    ToDo(String titoloIniziale, LocalDate data, String descrizioneIniziale, Utente utente){
        titolo = titoloIniziale;
        dataDiScadenza = data;
        descrizione = descrizioneIniziale;
        autore = utente.nome;
    }

    public ArrayList<String> getUtenti(){
        return utentiAssociati;
    }

    public LocalDate visualizeDeadline(){
        return dataDiScadenza;
    }

    public void modifyToDo(String nuovoTitolo, LocalDate nuovaData, String nuovaDescrizione, String nomeUtente){

       if(!utentiAssociati.contains(nomeUtente))
           return;

        if(!nuovoTitolo.isEmpty())
            this.titolo = nuovoTitolo;

        if(!nuovaDescrizione.isEmpty())
            this.descrizione=nuovaDescrizione;

        this.dataDiScadenza = nuovaData;

    }

    public void grantPermission(String nomeUtente, String nuovoUtente){
        if(nomeUtente.equals(autore))
            utentiAssociati.add(nuovoUtente);
    }

    public void revokePermission(String nomeUtente, String deleteUtente){
        if(nomeUtente.equals(autore))
            utentiAssociati.remove(deleteUtente);

    }

}

