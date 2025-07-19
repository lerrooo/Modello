package Controller;

import DAO.DatabaseConnection;
import model.Bacheca;
import model.ToDo;
import model.Utente;

import javax.xml.crypto.Data;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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

        String query = "SELECT * FROM Bacheca WHERE Owner = ?";
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

        String query = "SELECT * FROM ToDo WHERE Autore = ?";
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



//    public Controller() throws SQLException {
//        Bacheche = getBacheche();
//
//
//    }
}
