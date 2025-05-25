package model;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDo {
    public static String URL = "";
    public String titolo;
    public LocalDate dataDiScadenza;
    public String descrizione;
    Image Immagine;
    String autore;
    public Boolean completato = false;
    Scanner scanner = new Scanner(System.in);

    //String piuttosto che Utenti altrimenti un utente potrebbe accedere alle informazioni di un altro
    ArrayList<String> utentiAssociati = new ArrayList<String>();

    public ToDo(String titoloIniziale, LocalDate data, String descrizioneIniziale, Utente utente){
        titolo = titoloIniziale;
        dataDiScadenza = data;
        descrizione = descrizioneIniziale;
        autore = utente.nome;
    }

    public ArrayList<String> getUtenti(){
        return utentiAssociati;
    }

    public LocalDate visualizeDeadline(){
        return dataDiScadenza;
    }

    public String getTitolo(){return titolo;}

    public void modifyToDo(String nuovoTitolo, LocalDate nuovaData, String nuovaDescrizione, String nomeUtente){

       if(!utentiAssociati.contains(nomeUtente))
           return;

        if(!nuovoTitolo.isEmpty())
            this.titolo = nuovoTitolo;

        if(!nuovaDescrizione.isEmpty())
            this.descrizione = nuovaDescrizione;

        this.dataDiScadenza = nuovaData;

    }

    public void grantPermission(String nomeUtente, String nuovoUtente){
        if(nomeUtente.equals(autore))
            utentiAssociati.add(nuovoUtente);
    }

    public void revokePermission(String nomeUtente, String deleteUtente){
        if(nomeUtente.equals(autore))
            utentiAssociati.remove(deleteUtente);

    }

}

