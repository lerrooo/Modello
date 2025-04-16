import java.util.ArrayList;

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

    public void createToDo(){

    }

    public void modifyToDo(){

    }

    public void deleteToDo(){

    }

    public void changePositionToDo(){

    }

}
