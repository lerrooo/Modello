package Controller;

import DAO.DatabaseConnection;
import model.Bacheca;
import model.ToDo;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import static DAO.DatabaseConnection.connection;

public class Controller {

    private DatabaseConnection dbConnection = new DatabaseConnection();
    private ArrayList<Bacheca> Bacheche = new ArrayList<Bacheca>();
    private ArrayList<ToDo> ToDos = new ArrayList<ToDo>();
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

        String query = "SELECT * FROM Bacheca WHERE Owner = ? ORDER BY titolo DESC";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utenteLoggato);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String titolo = rs.getString("Titolo");
            String descrizione = rs.getString("Descrizione");

            //System.out.println("Bacheca: " + titolo + ", numero: " + ", owner: " + utenteLoggato + ", descrizione: " + descrizione);


            bacheche.add(new Bacheca(titolo, descrizione));


        }

        rs.close();
        ps.close();

        return bacheche;
    }

    public ArrayList<ToDo> getToDo() throws SQLException {

        ArrayList<ToDo> TodoArr = new ArrayList<>();

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
            String nomeBacheca = rs.getString("nomeBacheca");

            //System.out.println(titolo + " " + descrizione + " " + dataDiScadenza + " "
             //       + url + " " + image + " " + Color + " " + completato + " " + nomeBacheca);

            TodoArr.add(new ToDo(titolo, descrizione, dataDiScadenza, url, image, Color, completato, nomeBacheca));


        }

        rs.close();
        ps.close();

        return TodoArr;
    }

    public ArrayList<String> getSingleToDoDB(String nomeToDo, String nomeBacheca) throws SQLException {

        ArrayList<String> result = new ArrayList<>();

        String descrizione;
        String dataDiScadenza;
        String url;
        boolean completato;

        String query = "SELECT * FROM ToDo WHERE Autore = ? AND Titolo = ? AND nomeBacheca = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utenteLoggato);
        ps.setString(2, nomeToDo);
        ps.setString(3, nomeBacheca);

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

        }

        rs.close();
        ps.close();


        return result;
    }


    public void addToDoDB(String nomeToDo, String nomeBacheca, String descrizione) throws SQLException {


        String query = "INSERT INTO TODO(titolo, descrizione, datadiscadenza, autore, nomeBacheca) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeToDo);
        ps.setString(2, descrizione);
        ps.setDate(3, java.sql.Date.valueOf("2026-01-01"));
        ps.setString(4, utenteLoggato);
        ps.setString(5, nomeBacheca);
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
                if(t.nomeBacheca.equals(Bacheche.get(i).getTitolo()))
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
                if(t.nomeBacheca.equals(Bacheche.get(i).getTitolo()))
                    colori.add(t.Colore);
            }
            result.add(colori);
        }
        return result;
    }

    public void updateToDo(String newNome, String oldNome, String descrizione, String dataDiScadenza, String url, Color coloreScelto, boolean completato, String nomeBacheca) throws SQLException {
        System.out.println(oldNome + " " + descrizione + " " + dataDiScadenza + " " + url + " " + coloreScelto + " " + completato + " " + nomeBacheca);
        System.out.println(newNome + " " + descrizione + " " + dataDiScadenza + " " + url + " " + coloreScelto + " " + completato + " " + nomeBacheca);

        String hex = String.format("#%02x%02x%02x",
                coloreScelto.getRed(),
                coloreScelto.getGreen(),
                coloreScelto.getBlue());

        String query = "UPDATE TODO SET titolo = ?, descrizione = ?, datadiscadenza = ?, URL = ?, coloreSfondo = ?, completato = ? WHERE autore = ? AND nomeBacheca = ? AND titolo = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, newNome);
        ps.setString(2, descrizione);
        ps.setDate(3, Date.valueOf(dataDiScadenza));
        ps.setString(4, url);
        ps.setString(5, hex);
        ps.setBoolean(6, completato);
        ps.setString(7, utenteLoggato);
        ps.setString(8, nomeBacheca);
        ps.setString(9, oldNome);
        ps.executeUpdate();
        ps.close();

        ToDos = getToDo();

    }

    public void eliminaBacheca() {

    }

    public void addBacheca(int scelta) throws SQLException{
        String[] titoli = {"Università", "Tempo Libero", "Lavoro"};
        String titolo = titoli[scelta];

        String query = "INSERT INTO BACHECA(titolo, owner) VALUES(?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, titolo);
        ps.setString(2, utenteLoggato);

        ps.executeUpdate();
        ps.close();

        Bacheche = getBacheche();
        ToDos = getToDo();
    }

    public void removeBacheca(String nomeBacheca) throws SQLException {
        String query = "DELETE FROM BACHECA WHERE OWNER = ? AND titolo = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utenteLoggato);
        ps.setString(2, nomeBacheca);
        ps.executeUpdate();
        ps.close();

        Bacheche = getBacheche();
        ToDos = getToDo();
    }

    public boolean cercaToDo(String nomeBacheca, String nomeToDo){

        for(ToDo t : ToDos){
            if(t.getTitolo().equals(nomeToDo) && t.nomeBacheca.equals(nomeBacheca)){
                return true;
            }
        }

        return false;
    }

    public void spostaToDo(String nomeBacheca1, String nomeToDo, String nomeBacheca2) throws SQLException {

        String query = "SELECT moveToDoToNewBacheca(?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeToDo);
        ps.setString(2, utenteLoggato);
        ps.setString(3, nomeBacheca1);
        ps.setString(4, nomeBacheca2);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            boolean risultato = rs.getBoolean(1);
            if (risultato) {
                System.out.println("ToDo spostato con successo!");
            } else {
                System.out.println("ToDo non trovato o errore nello spostamento.");
            }
        }

        rs.close();
        ps.close();


        ToDos = getToDo();


    }


    public void swapToDoOrder(String nomeBacheca, String nomeToDo, int nuovoOrdine) throws SQLException {

        String query = "SELECT swapToDoOrder(?, ?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeBacheca);
        ps.setString(2, utenteLoggato);
        ps.setString(3, nomeToDo);
        ps.setInt(4, nuovoOrdine);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            boolean risultato = rs.getBoolean(1);
            if (risultato) {
                System.out.println("ToDo spostato con successo!");
            } else {
                System.out.println("ToDo non trovato o errore nello spostamento.");
            }
        }

        rs.close();
        ps.close();


        ToDos = getToDo();

    }

//    public Controller() throws SQLException {
//        Bacheche = getBacheche();
//
//
//    }
}
