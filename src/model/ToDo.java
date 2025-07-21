package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ToDo {
    public static String URL = "";
    public String titolo;
    public Date dataDiScadenza;
    public String descrizione;
    public String Colore;
    String Immagine;
    public boolean completato = false;
    public String nomeBacheca;
    Scanner scanner = new Scanner(System.in);

    //String piuttosto che Utenti altrimenti un utente potrebbe accedere alle informazioni di un altro
    ArrayList<String> utentiAssociati = new ArrayList<String>();

    public ToDo(String titoloIniziale, String descrizioneIniziale, Date data, String url, String image,String Color, boolean completato, String nomeBacheca){
        titolo = titoloIniziale;
        descrizione = descrizioneIniziale;
        dataDiScadenza = data;
        URL = url;
        Immagine = image;
        Colore = Color;
        this.completato = completato;
        this.nomeBacheca = nomeBacheca;
    }

    public ArrayList<String> getUtenti(){
        return utentiAssociati;
    }

    public Date visualizeDeadline(){
        return dataDiScadenza;
    }

    public String getTitolo(){return titolo;}

    public void modifyToDo(String nuovoTitolo, Date nuovaData, String nuovaDescrizione, String nomeUtente){

       if(!utentiAssociati.contains(nomeUtente))
           return;

        if(!nuovoTitolo.isEmpty())
            this.titolo = nuovoTitolo;

        if(!nuovaDescrizione.isEmpty())
            this.descrizione = nuovaDescrizione;

        this.dataDiScadenza = nuovaData;

    }

//    public void grantPermission(String nomeUtente, String nuovoUtente){
//        if(nomeUtente.equals(autore))
//            utentiAssociati.add(nuovoUtente);
//    }
//
//    public void revokePermission(String nomeUtente, String deleteUtente){
//        if(nomeUtente.equals(autore))
//            utentiAssociati.remove(deleteUtente);
//
//    }

}

