package ImplementazioneDao;
import DAO.utenteDao;
import Database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Database.DatabaseConnection.connection;

public class implUtente implements utenteDao{

    @Override
    public void addUtente(String NomeUtente, char[] Password) throws SQLException {

        String passwordString = new String(Password);

        PreparedStatement addBookPS = connection.prepareStatement(
                "INSERT INTO UTENTE (NOME, PASSWORD) VALUES ('" + NomeUtente +"','" + passwordString +"');"
        );
        addBookPS.executeUpdate();
    }

    @Override
    public String loginUtente(String NomeUtente, char[] Password) throws SQLException {
        String passwordString = new String(Password);

        String query = "SELECT * FROM UTENTE WHERE NOME = ? AND PASSWORD = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, NomeUtente);
        ps.setString(2, passwordString);

        ResultSet rs = ps.executeQuery();
        boolean result = rs.next();
        if(result){
            return NomeUtente;
//            System.out.println("Titoli:");
//            for(Bacheca b : Bacheche){
//                System.out.println(b.getTitolo());
//            }
        }


        rs.close();
        ps.close();
        return null;
    }
}
