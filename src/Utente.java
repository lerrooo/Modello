import java.util.ArrayList;

public class Utente {
    public String nome;
    String password;

    ArrayList<Bacheca> bacheche = new ArrayList<Bacheca>();

    Utente(String nomeRegistrazione, String passwordRegistrazione){
        nome = nomeRegistrazione;
        password = passwordRegistrazione;
    }

    public void createBacheca(String titoloBacheca){
        if(bacheche.size() < 3)
            bacheche.add(new Bacheca(titoloBacheca));
        else
            System.out.println("Limite raggiunto");
    }

    public void deleteBacheca(Boolean confermaEliminazione, int indiceDaEliminare){
        if(confermaEliminazione){
            if(indiceDaEliminare >= 0 && indiceDaEliminare < 3){
                bacheche.remove(indiceDaEliminare);
                System.out.println("..elemento eliminato..");

            }else
                System.out.println("..elemento non trovato..");
        }
        else
            System.out.println("..non è possibile fare l'eliminazione..");
    }

    public void changePositionToDo(int indexBachecaStart, int indexBachecaEnd, int indexToDoStart, int indexToDoEnd){
        bacheche.get(indexBachecaEnd).toDoList.add(indexToDoEnd, bacheche.get(indexBachecaStart).toDoList.get(indexToDoStart));
        bacheche.get(indexBachecaStart).toDoList.remove(indexToDoStart);
    }

    public Boolean searchToDo(String titoloToDo){

        for (Bacheca bacheca : bacheche) {
            for (ToDo toDoTemp : bacheca.toDoList) {
                if (titoloToDo.equals(toDoTemp.titolo))
                    return true;
            }
        }

        return false;
    }
    public void completeToDo(int indexBacheca, int indexToDo){
        bacheche.get(indexBacheca).toDoList.get(indexToDo).completato = true;

    }
}
