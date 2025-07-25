package DAO;

import java.sql.SQLException;

public interface utenteDao {
   void addUtente(String NomeUtente, char[] Password) throws SQLException;
   String loginUtente(String NomeUtente, char[] Password) throws SQLException;
}
