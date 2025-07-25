package ImplementazioneDao;

import model.Bacheca;
import DAO.bachecaDao;
import Database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Database.DatabaseConnection.connection;

public class implBacheca implements bachecaDao{
    /**
     * Utilizzato per prendere le informazioni delle bacheche dell'utente
     * @param bachecheInfo le salva in un ArrayList di String (Ogni ArrayList equivale ad un bacheca)
     * @param utenteLoggato l'utente che ha effettuato l'accesso
     */
        public void getBacheche(ArrayList<ArrayList<String>> bachecheInfo, String utenteLoggato) throws SQLException {
        String query = "SELECT * FROM Bacheca WHERE Owner = ? ORDER BY titolo DESC";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utenteLoggato);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String titolo = rs.getString("Titolo");
            String descrizione = rs.getString("Descrizione");
            bachecheInfo.getFirst().add(titolo);
            bachecheInfo.get(1).add(descrizione);
            System.out.println("Bacheca: " + titolo + ", numero: " + ", owner: " + utenteLoggato + ", descrizione: " + descrizione);


        }

        rs.close();
        ps.close();

    }
    /**
     * Utilizzato per aggiungere una bacheca al DB
     * @param scelta (0 = Università, 1 = Tempo Libero, 2 = Lavoro)
     * @param utenteLoggato l'utente che ha effettuato l'accesso
     */
    public void addBacheca(int scelta, String utenteLoggato) throws SQLException{
        String[] titoli = {"Università", "Tempo Libero", "Lavoro"};
        String titolo = titoli[scelta];

        String query = "INSERT INTO BACHECA(titolo, owner) VALUES(?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, titolo);
        ps.setString(2, utenteLoggato);

        ps.executeUpdate();
        ps.close();

    }
    /**
     * Utilizzato per rimuovere una bacheca al DB
     * @param nomeBacheca Il nome della bacheca da rimuovere
     * @param utenteLoggato l'utente che ha effettuato l'accesso
     */
    public void removeBacheca(String nomeBacheca, String utenteLoggato) throws SQLException {
        String query = "DELETE FROM BACHECA WHERE OWNER = ? AND titolo = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utenteLoggato);
        ps.setString(2, nomeBacheca);
        ps.executeUpdate();
        ps.close();

    }

}
