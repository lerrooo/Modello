package Controller;

import model.Utente;
import java.util.ArrayList;


public class Controller {
    private ArrayList<Utente> utenti = new ArrayList<Utente>();
    private Utente utenteLoggato = null;

    public ArrayList<Utente> getUtenti(){
        return utenti;
    }

    public Utente getUtenteLoggato(){
        return utenteLoggato;
    }

    public void setUtenteLoggato(Utente utente){
        utenteLoggato =utente;
    }

    public void addUtente(Utente utente){
        utenti.add(utente);
    }


}