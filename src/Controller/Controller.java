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
import java.util.ArrayList;
import java.util.Arrays;

import static DAO.DatabaseConnection.connection;

public class Controller {

    private DatabaseConnection dbConnection = new DatabaseConnection();
    private ArrayList<Bacheca> Bacheche = new ArrayList<Bacheca>();
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
            System.out.println("Titoli:");
            for(Bacheca b : Bacheche){
                System.out.println(b.getTitolo());
            }
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
        for (Bacheca b : Bacheche) {
            ArrayList<String> titoli = new ArrayList<>();
            for (ToDo t : b.getToDoList()) {
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
