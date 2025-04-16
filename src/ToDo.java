import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class ToDo {
    String titolo;
    Date dataDiScadenza;
    String URL;
    String descrizione;
    Image Immagine;
    Autore autore;
    Boolean completato = false;

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
    public void modifyToDo(){

    }
}

