import java.util.ArrayList;
import java.util.Date;

enum titoloBacheca{
    universita,
    lavoro,
    tempoLibero
}

public class Bacheca {
    ArrayList<ToDo> toDoList = new ArrayList<ToDo>();

    String titolo;
    String descrizione;
    titoloBacheca tipoBacheca;


    Bacheca(String titoloInserimento){
        titolo = titoloInserimento;
    }

    public void createToDo(String titoloToDo, Date dataToDo, String descrizioneToDo, Utente utente){
        toDoList.add(new ToDo(titoloToDo, dataToDo, descrizioneToDo, utente));
    }
    public void modifyBacheca(){

    }


    public void deleteToDo(){

    }

    public void changePositionToDo(){

    }
    public void swapToDo(){

    }


}
