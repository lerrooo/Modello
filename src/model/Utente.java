package model;

import java.util.ArrayList;

public class Utente {
    public String nome;
    public String password;

    public ArrayList<Bacheca> bacheche = new ArrayList<Bacheca>();

    public Utente(String nomeRegistrazione, String passwordRegistrazione){
        nome = nomeRegistrazione;
        password = passwordRegistrazione;
        bacheche.add(new Bacheca("Università","Bacheca università"));
        bacheche.add(new Bacheca("Lavoro", "Bacheca lavoro"));
        bacheche.add(new Bacheca("Tempo libero", "Bacheca tempo libero"));

    }
    public void createBacheca(String titoloBacheca, String descrizioneBacheca, titoloBacheca tipoBacheca){
        if(bacheche.size() < 3)
            bacheche.add(new Bacheca(titoloBacheca, descrizioneBacheca));
        else
            System.out.println("Limite raggiunto");
    }
    public void deleteBacheca(int indiceDaEliminare){

        if(indiceDaEliminare >= 0 && indiceDaEliminare < 3){
            bacheche.remove(indiceDaEliminare);
            System.out.println("..elemento eliminato..");
        }else
            System.out.println("..elemento non trovato..");
    }
    public void changePositionToDo(int indexBachecaStart, int indexBachecaEnd, int indexToDoStart, int indexToDoEnd){
        bacheche.get(indexBachecaEnd).toDoList.add(indexToDoEnd, bacheche.get(indexBachecaStart).toDoList.get(indexToDoStart));
        bacheche.get(indexBachecaStart).toDoList.remove(indexToDoStart);
    }
    public ToDo searchToDo(String titoloToDo){
        for (Bacheca bacheca : bacheche) {
            for (ToDo toDoTemp : bacheca.toDoList) {
                if (titoloToDo.equals(toDoTemp.titolo))
                    return toDoTemp;
            }
        }
        return null;
    }

    public void completeToDo(int indexBacheca, int indexToDo){
        bacheche.get(indexBacheca).toDoList.get(indexToDo).completato = true;

    }
}
