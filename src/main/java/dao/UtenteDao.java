package dao;

import java.sql.SQLException;

public interface UtenteDao {
   void addUtente(String nomeUtente, char[] password) throws SQLException;
   String loginUtente(String nomeUtente, char[] password) throws SQLException;
}
