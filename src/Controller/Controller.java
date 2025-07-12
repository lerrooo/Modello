package Controller;

import DAO.DatabaseConnection;
import model.Bacheca;
import model.Utente;

import javax.xml.crypto.Data;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static DAO.DatabaseConnection.connection;

public class Controller {

    private DatabaseConnection dbConnection = new DatabaseConnection();

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

    public void addUtente(Utente utente) throws SQLException {

        PreparedStatement addBookPS = connection.prepareStatement(
                "INSERT INTO UTENTE (NOME, PASSWORD) VALUES ('" + utente.nome +"','" + utente.password +"');"
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

        if(result)
            setUtenteLoggato(NomeUtente);

        rs.close();
        ps.close();

        return result;
    }

    public ArrayList<Bacheca> getBacheche() throws SQLException {

        ArrayList<Bacheca> bacheche = new ArrayList<Bacheca>();

        String query = "SELECT * FROM Bacheche WHERE NOME = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utenteLoggato);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String titolo = rs.getString("Titolo");
            int numeroBacheca = rs.getInt("numeroBacheca");
            String owner = rs.getString("Owner");
            String descrizione = rs.getString("Descrizione");

            System.out.println("Bacheca: " + titolo + ", numero: " + numeroBacheca + ", owner: " + owner + ", descrizione: " + descrizione);

            bacheche.add(new Bacheca());

            Bacheca b = new Bacheca(titolo, numeroBacheca, owner, descrizione);

        }

        rs.close();
        ps.close();

    }

//    public Controller(){
//        addUtente(new Utente("a","a"));
//    }
}
