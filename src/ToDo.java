import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ToDo {
    String titolo;
    Date dataDiScadenza;
    String URL;
    String descrizione;
    Image Immagine;
    Autore autore;
    Boolean completato = false;
    Scanner scanner= new Scanner(System.in);

    //String piuttosto che Utenti altrimenti un utente potrebbe accedere alle informazioni di un altro
    ArrayList<String> utentiAssociati = new ArrayList<String>();

    ToDo(String titoloIniziale, Date data, String descrizioneIniziale, Utente utente){
        titolo = titoloIniziale;
        dataDiScadenza = data;
        descrizione = descrizioneIniziale;
        autore.nomeUtente = utente.nome;
    }

    public ArrayList<String> getUtenti(){
        return utentiAssociati;
    }
    public void visualizeDeadline(){

    }
    public void modifyToDo( int indiceModifica, String nuovoTitolo, Date nuovaData, String nuovaDescrizione, Autore nuovoAutore){

        if(nuovoTitolo != null)
            titolo=nuovoTitolo;
        if(nuovaData != null)
            dataDiScadenza=nuovaData;
        if(nuovaDescrizione != null)
            descrizione=nuovaDescrizione;
        if(nuovoAutore != null)
            autore=nuovoAutore;
    }

}

