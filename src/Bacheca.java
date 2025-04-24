import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Bacheca {
    ArrayList<ToDo> toDoList = new ArrayList<ToDo>();

    String titolo;
    String descrizione;
    titoloBacheca tipoBacheca;

    Bacheca(String titoloInserimento, String descrizioneInserimento, titoloBacheca tipoInserimento){
        titolo = titoloInserimento;
        descrizione = descrizioneInserimento;
        tipoBacheca = tipoInserimento;
    }

    public void createToDo(String titoloToDo, Date dataToDo, String descrizioneToDo, Utente utente){
        toDoList.add(new ToDo(titoloToDo, dataToDo, descrizioneToDo, utente));
    }

    public void deleteToDo(int index){
        toDoList.remove(index);
    }

    public void modifyBacheca(String nuovoTitolo, String nuovaDescrizione, titoloBacheca nuovoTipoBacheca){

    if(!nuovoTitolo.isEmpty())
        this.titolo = nuovoTitolo;
    if(!nuovaDescrizione.isEmpty())
        this.descrizione = nuovaDescrizione;
    this.tipoBacheca = nuovoTipoBacheca;
    }

    public void swapToDo(int indexToDoFirst, int indexToDoSecond){
        Collections.swap(toDoList, indexToDoFirst, indexToDoSecond);
    }


}
