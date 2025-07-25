package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface bachecaDao {
    void getBacheche(ArrayList<ArrayList<String>> bachecheInfo, String utenteLoggato) throws SQLException;
    void addBacheca(int scelta, String utenteLoggato) throws SQLException;
    void removeBacheca(String nomeBacheca, String utenteLoggato) throws SQLException;
}
