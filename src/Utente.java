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
    public void deleteBacheca(boolean confermaEliminazione, int indiceDaEliminare){
        if(confermaEliminazione){
            if(indiceDaEliminare>=0 && indiceDaEliminare<3){
            bacheche.remove(indiceDaEliminare);
            System.out.println("..elemento eliminato..");
            }else
                System.out.println("..elemento non trovato..");
        }
        else
            System.out.println("..non è possibile fare l'eliminazione..");
    }
    public void searchToDo(){

    }
    public void completeToDo(){

    }
}
