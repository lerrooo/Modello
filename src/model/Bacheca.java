package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Bacheca {
    public ArrayList<ToDo> toDoList = new ArrayList<ToDo>();

    public String titolo;
    public String descrizione;
    public titoloBacheca tipoBacheca;

    Bacheca(String titoloInserimento, String descrizioneInserimento, titoloBacheca tipoInserimento){
        titolo = titoloInserimento;
        descrizione = descrizioneInserimento;
        tipoBacheca = tipoInserimento;
    }

    public void createToDo(String titoloToDo, LocalDate dataToDo, String descrizioneToDo, Utente utente){
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
