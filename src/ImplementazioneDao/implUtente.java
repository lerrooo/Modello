package ImplementazioneDao;
import DAO.utenteDao;
import Database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Database.DatabaseConnection.connection;

public class implUtente implements utenteDao{
    /**
     * Registra un utente al DB
     * @param nomeUtente nome dell'utente da registrare
     * @param password password dell'utente da registrare
     **/
    @Override
    public void addUtente(String nomeUtente, char[] password) throws SQLException {

        String passwordString = new String(password);

        PreparedStatement addBookPS = connection.prepareStatement(
                "INSERT INTO UTENTE (NOME, PASSWORD) VALUES ('" + nomeUtente +"','" + passwordString +"');"
        );
        addBookPS.executeUpdate();
    }
    /**
     * Effettua il login di un utente al DB
     * @param nomeUtente nome dell'utente
     * @param password password dell'utente
     * @returns ritorna null se non trova nessun utente, oppure il nome dell'utente in caso contrario
     **/
    @Override
    public String loginUtente(String nomeUtente, char[] password) throws SQLException {
        String passwordString = new String(password);

        String query = "SELECT * FROM UTENTE WHERE NOME = ? AND PASSWORD = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeUtente);
        ps.setString(2, passwordString);

        ResultSet rs = ps.executeQuery();
        boolean result = rs.next();
        if(result){
            return nomeUtente;
        }


        rs.close();
        ps.close();
        return null;
    }
}
