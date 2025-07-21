package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Bacheca {
    public ArrayList<ToDo> toDoList = new ArrayList<ToDo>();

    public String titolo;
    public String descrizione;
    public int numeroBacheca;
    public titoloBacheca tipoBacheca;

    public Bacheca(String titoloInserimento, String descrizioneInserimento){
        titolo = titoloInserimento;
        descrizione = descrizioneInserimento;
    }

//    public void createToDo(String titoloToDo, LocalDate dataToDo, String descrizioneToDo, Utente utente){
//        toDoList.add(new ToDo(titoloToDo, dataToDo, descrizioneToDo, utente));
//    }

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

    public int findToDoIndex(String titoloToDo){
        for(int i = 0; i < toDoList.size(); i++){
            if(toDoList.get(i).getTitolo().equals(titoloToDo)){
                return i;
            }
        }
        return -1;
    }

    public void swapToDo(int indexToDoFirst, int indexToDoSecond){
        Collections.swap(toDoList, indexToDoFirst, indexToDoSecond);
    }


    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Integer getNumeroBacheca() {
        return numeroBacheca;
    }

    public ArrayList<ToDo> getToDoList() {
        return toDoList;
    }
}
