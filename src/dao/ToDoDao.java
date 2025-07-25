package dao;



import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ToDoDao {
    void getToDo(ArrayList<String> titoli,
                 ArrayList<String> descrizione,
                 ArrayList<Date> dateDiScadenza,
                 ArrayList<String> urls,
                 ArrayList<String> images,
                 ArrayList<String> coloriToDo,
                 ArrayList<Boolean> stati,
                 ArrayList<Integer> ordini,
                 ArrayList<String> nomeBacheca,
                 String utenteLoggato) throws SQLException;

    ArrayList<String> getSingleToDoDB(String nomeToDo, String nomeBacheca, String utenteLoggato) throws SQLException;
    void addToDoDB(String nomeToDo, String nomeBacheca, String descrizione, String utenteBacheca) throws SQLException;
    void updateToDo(String newNome, String oldNome, String descrizione, String dataDiScadenza, String url, Color coloreScelto,
                    boolean completato, String nomeBacheca, String utenteLoggato) throws SQLException;

    void spostaToDo(String nomeBacheca1, String nomeToDo, String nomeBacheca2, String utenteLoggato) throws  SQLException;
    void swapToDoOrder(String nomeBacheca, String nomeToDo, int nuovoOrdine, String utenteLoggato) throws SQLException;
    boolean autoreToDo(String nomeBacheca, String nomeTodo, String utenteLoggato) throws SQLException;
    void aggiungiCondivisione(String nomeBacheca, String nomeToDo, String utenteLoggato, String destinatario) throws SQLException;
    void removeCondivisione(String nomeBacheca, String nomeToDo, String utenteLoggato, String destinatario) throws SQLException;
    void removeCondivisione(String nomeBacheca, String nomeToDo, String utenteLoggato) throws SQLException;
    void removeToDo(String nomeBacheca, String nomeToDo, String utenteLoggato) throws SQLException;
    void getCondivisioniFromDB(String utenteLoggato, ArrayList<ArrayList<String>> dati) throws SQLException;

}
