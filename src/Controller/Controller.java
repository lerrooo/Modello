package Controller;

import DAO.DatabaseConnection;
import model.Bacheca;
import model.ToDo;
import model.Utente;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;

import static DAO.DatabaseConnection.connection;

public class Controller {

    private DatabaseConnection dbConnection = new DatabaseConnection();
    private ArrayList<Bacheca> Bacheche = new ArrayList<Bacheca>();
    ArrayList<ToDo> ToDos = new ArrayList<ToDo>();
    private String utenteLoggato = null;

//    public ArrayList<Utente> getUtenti(){
//        return utenti;
//    }

    public String getUtenteLoggato(){
        return utenteLoggato;
    }

    public void setUtenteLoggato(String nomeUtente){
        utenteLoggato = nomeUtente;
    }

    public void addUtente(String NomeUtente, char[] Password) throws SQLException {

        String passwordString = new String(Password);

        PreparedStatement addBookPS = connection.prepareStatement(
                "INSERT INTO UTENTE (NOME, PASSWORD) VALUES ('" + NomeUtente +"','" + passwordString +"');"
        );
        addBookPS.executeUpdate();
    }

    public boolean loginUtente(String NomeUtente, char[] Password) throws SQLException {
        String passwordString = new String(Password);

        String query = "SELECT * FROM UTENTE WHERE NOME = ? AND PASSWORD = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, NomeUtente);
        ps.setString(2, passwordString);

        ResultSet rs = ps.executeQuery();

        boolean result = rs.next();

        if(result){
            setUtenteLoggato(NomeUtente);
            Bacheche = getBacheche();
            ToDos = getToDo();
//            System.out.println("Titoli:");
//            for(Bacheca b : Bacheche){
//                System.out.println(b.getTitolo());
//            }
        }


        rs.close();
        ps.close();

        return result;
    }

    public ArrayList<Bacheca> getBacheche() throws SQLException {
        ArrayList<Bacheca> bacheche = new ArrayList<Bacheca>();

        String query = "SELECT * FROM Bacheca WHERE Owner = ? ORDER BY numeroBacheca";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utenteLoggato);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String titolo = rs.getString("Titolo");
            int numeroBacheca = rs.getInt("numeroBacheca");
            String descrizione = rs.getString("Descrizione");

            System.out.println("Bacheca: " + titolo + ", numero: " + numeroBacheca + ", owner: " + utenteLoggato + ", descrizione: " + descrizione);


            bacheche.add(new Bacheca(titolo, numeroBacheca, descrizione));


        }

        rs.close();
        ps.close();

        return bacheche;
    }

    public ArrayList<ToDo> getToDo() throws SQLException {

        ArrayList<ToDo> TodoArr = new ArrayList<ToDo>();

        String query = "SELECT * FROM ToDo WHERE Autore = ? ORDER BY ordine";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utenteLoggato);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String titolo = rs.getString("Titolo");
            String descrizione = rs.getString("Descrizione");
            Date dataDiScadenza = rs.getDate("dataDiScadenza");
            String url = rs.getString("Url");
            String image = rs.getString("Image");
            String Color = rs.getString("coloreSfondo");
            boolean completato = rs.getBoolean("completato");
            int numeroBacheca = rs.getInt("numeroBacheca");

            System.out.println(titolo + " " + descrizione + " " + dataDiScadenza + " "
                    + url + " " + image + " " + Color + " " + completato + " " + numeroBacheca);

            TodoArr.add(new ToDo(titolo, descrizione, dataDiScadenza, url, image, Color, completato, numeroBacheca));


        }

        rs.close();
        ps.close();

        return TodoArr;
    }

    public ArrayList<String> getSingleToDoDB(String nomeToDo, int indexBacheca) throws SQLException {

        ArrayList<String> result = new ArrayList<String>();

        String descrizione;
        String dataDiScadenza;
        String url;
        boolean completato;

        String query = "SELECT * FROM ToDo WHERE Autore = ? AND Titolo = ? AND NumeroBacheca = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utenteLoggato);
        ps.setString(2, nomeToDo);
        ps.setInt(3, indexBacheca);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            descrizione = rs.getString("Descrizione");
            dataDiScadenza = String.valueOf(rs.getDate("DataDiScadenza"));
            url = rs.getString("Url");
            //image = rs.getString("Image");
            String color = rs.getString("ColoreSfondo");
            completato = rs.getBoolean("Completato");


            result.add(descrizione);
            result.add(dataDiScadenza);
            result.add(url);
            result.add(color);
            result.add(String.valueOf(completato));

            System.out.println(dataDiScadenza);

        }

        rs.close();
        ps.close();


        return result;
    }


    public void addToDoDB(String nomeToDo, int index, String descrizione) throws SQLException {

        String query = "INSERT INTO TODO(titolo, descrizione, datadiscadenza, autore, numerobacheca) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeToDo);
        ps.setString(2, descrizione);
        ps.setDate(3, java.sql.Date.valueOf("2026-01-01"));
        ps.setString(4, utenteLoggato);
        ps.setInt(5, index);
        ps.executeUpdate();
        ps.close();

    }

    // Ritorna lista titoli delle bacheche
    public ArrayList<String> getTitoliBacheche() {
        ArrayList<String> titoli = new ArrayList<>();
        for (Bacheca b : Bacheche) {
            titoli.add(b.getTitolo());
        }
        return titoli;
    }

    public boolean getExistsBacheca(int index){
        for (Bacheca b : Bacheche) {
            if(b.getNumeroBacheca() == index)
                return true;
        }
        return false;
    }

    // Ritorna descrizioni delle bacheche (opzionale)
    public ArrayList<String> getDescrizioniBacheche() {
        ArrayList<String> descrizioni = new ArrayList<>();
        for (Bacheca b : Bacheche) {
            descrizioni.add(b.getDescrizione());
        }
        return descrizioni;
    }

    // Ritorna numeri bacheca (posizioni) delle bacheche
    public ArrayList<Integer> getPosizioniBacheche() {
        ArrayList<Integer> posizioni = new ArrayList<>();
        for (Bacheca b : Bacheche) {
            posizioni.add(b.getNumeroBacheca());
        }
        return posizioni;
    }

    // Ritorna lista di titoli ToDo per ogni bacheca (come lista di liste)
    public ArrayList<ArrayList<String>> getTuttiTitoliToDo() {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int i = 0; i < Bacheche.size(); i++) {
            ArrayList<String> titoli = new ArrayList<>();
            for (ToDo t : ToDos) {
                if(t.bachecaId == i)
                    titoli.add(t.getTitolo());
            }
            result.add(titoli);
        }
        return result;
    }

    public ArrayList<ArrayList<String>> getTuttiColoriToDo() {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int i = 0; i < Bacheche.size(); i++) {
            ArrayList<String> colori = new ArrayList<>();
            for (ToDo t : ToDos) {
                if(t.bachecaId == i)
                    colori.add(t.Colore);
            }
            result.add(colori);
        }
        return result;
    }

    public void updateToDo(String newNome, String oldNome, String descrizione, String dataDiScadenza, String url, Color coloreScelto, boolean completato, int indexBacheca) throws SQLException {
//        System.out.println(nomeToDo + " " + descrizione + " " + dataDiScadenza + " " + url + " " + coloreScelto + " " + completato + " " + indexBacheca);

        String hex = String.format("#%02x%02x%02x",
                coloreScelto.getRed(),
                coloreScelto.getGreen(),
                coloreScelto.getBlue());

        String query = "UPDATE TODO SET titolo = ?, descrizione = ?, datadiscadenza = ?, URL = ?, coloreSfondo = ?, completato = ? WHERE autore = ? AND numeroBacheca = ? AND titolo = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, newNome);
        ps.setString(2, descrizione);
        ps.setDate(3, Date.valueOf(dataDiScadenza));
        ps.setString(4, url);
        System.out.println(hex);
        ps.setString(5, hex);
        ps.setBoolean(6, completato);
        ps.setString(7, utenteLoggato);
        ps.setInt(8, indexBacheca);
        ps.setString(9, oldNome);
        ps.executeUpdate();
        ps.close();

    }

    public void eliminaBacheca() {

    }

    public void addBacheca(int index) throws SQLException{
        String[] titoli = {"Università", "Lavoro", "Tempo Libero"};
        String titolo = titoli[index];

        String query = "INSERT INTO BACHECA(titolo, numeroBacheca, owner) VALUES(?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, titolo);
        ps.setInt(2, index);
        ps.setString(3, utenteLoggato);

        ps.executeUpdate();
        ps.close();

        Bacheche = getBacheche();
    }


//    public Controller() throws SQLException {
//        Bacheche = getBacheche();
//
//
//    }
}
