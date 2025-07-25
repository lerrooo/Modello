package ImplementazioneDao;
import DAO.toDoDao;
import Database.DatabaseConnection;
import model.ToDo;

import java.awt.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static Database.DatabaseConnection.connection;

public class implToDo implements toDoDao{

    public void getToDo(ArrayList<String> titoli,
                        ArrayList<String> descrizione,
                        ArrayList<Date> dateDiScadenza,
                        ArrayList<String> urls,
                        ArrayList<String> images,
                        ArrayList<String> coloriToDo,
                        ArrayList<Boolean> stati,
                        ArrayList<Integer> ordini,
                        ArrayList<String> nomeBacheca,
                        String utenteLoggato
    ) throws SQLException {
        String query = "SELECT * FROM ToDo WHERE Autore = ? ORDER BY ordine";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utenteLoggato);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            String colore = "";

            titoli.add(rs.getString("Titolo"));
            descrizione.add(rs.getString("Descrizione"));

            //Data di scadenza per il colore del ToDo
            Date dataDiScadenza = rs.getDate("dataDiScadenza");
            Date today = new Date(System.currentTimeMillis());

            dateDiScadenza.add(dataDiScadenza);
            urls.add(rs.getString("Url"));
            images.add(rs.getString("Image"));

            if (dataDiScadenza.compareTo(today) <= 0)
                colore = "#FF0000";
            else
                colore = rs.getString("coloreSfondo");

            coloriToDo.add(colore);
            stati.add(rs.getBoolean("completato"));
            ordini.add(rs.getInt("ordine"));
            nomeBacheca.add(rs.getString("nomeBacheca"));
        }

        rs.close();
        ps.close();

        String queryCond = "SELECT * FROM TODO WHERE titolo IN (SELECT todotitolo FROM CONDIVISIONE WHERE destinatario = ?)";
        PreparedStatement psCond = connection.prepareStatement(queryCond);
        psCond.setString(1, utenteLoggato);

        ResultSet rsCond = psCond.executeQuery();

        while (rsCond.next()) {

            String colore = "";

            titoli.add(rsCond.getString("Titolo"));
            descrizione.add(rsCond.getString("Descrizione"));

            //Data di scadenza per il colore del ToDo
            Date dataDiScadenza = rsCond.getDate("dataDiScadenza");
            Date today = new Date(System.currentTimeMillis());

            dateDiScadenza.add(dataDiScadenza);
            urls.add(rsCond.getString("Url"));
            images.add(rsCond.getString("Image"));

            if (dataDiScadenza.compareTo(today) <= 0)
                colore = "#FF0000";
            else
                colore = rsCond.getString("coloreSfondo");

            coloriToDo.add(colore);
            stati.add(rsCond.getBoolean("completato"));
            ordini.add(rsCond.getInt("ordine"));
            nomeBacheca.add(rsCond.getString("nomeBacheca"));
        }

        rsCond.close();
        psCond.close();

    }


    public ArrayList<String> getSingleToDoDB(String nomeToDo, String nomeBacheca, String utenteLoggato) throws SQLException {

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


    public void addToDoDB(String nomeToDo, String nomeBacheca, String descrizione, String utenteLoggato) throws SQLException {

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        String query = "INSERT INTO TODO(titolo, descrizione, datadiscadenza, autore, nomeBacheca) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeToDo);
        ps.setString(2, descrizione);
        ps.setDate(3, java.sql.Date.valueOf(tomorrow));
        ps.setString(4, utenteLoggato);
        ps.setString(5, nomeBacheca);
        ps.executeUpdate();
        ps.close();

    }
    public void updateToDo(String newNome, String oldNome, String descrizione, String dataDiScadenza, String url, Color coloreScelto, boolean completato, String nomeBacheca, String utenteLoggato) throws SQLException {
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
        int righeModificate = ps.executeUpdate();
        ps.close();

        if(righeModificate <= 0){

            String query1 = "SELECT todoautorenome FROM CONDIVISIONE WHERE todotitolo = ? AND todoBachecaNome = ? AND destinatario = ?";
            ps = connection.prepareStatement(query1);
            ps.setString(1, oldNome);
            ps.setString(2, nomeBacheca);
            ps.setString(3, utenteLoggato);
            ResultSet rs = ps.executeQuery();
            String autore = "";
            if (rs.next()) {
                autore = rs.getString("todoautorenome");
            }

            rs.close();
            ps.close();

            String query2 = "DELETE FROM CONDIVISIONE WHERE todotitolo = ? AND todoBachecaNome = ? AND todoautorenome = ? AND destinatario = ?";
            ps = connection.prepareStatement(query2);
            ps.setString(1, oldNome);
            ps.setString(2, nomeBacheca);
            ps.setString(3, autore);
            ps.setString(3, utenteLoggato);
            ps.executeUpdate();
            ps.close();


            String query3 = "UPDATE TODO SET titolo = ?, descrizione = ?, datadiscadenza = ?, URL = ?, coloreSfondo = ?, completato = ? WHERE autore = ? AND nomeBacheca = ? AND titolo = ?;";
            ps = connection.prepareStatement(query3);
            ps.setString(1, newNome);
            ps.setString(2, descrizione);
            ps.setDate(3, Date.valueOf(dataDiScadenza));
            ps.setString(4, url);
            ps.setString(5, hex);
            ps.setBoolean(6, completato);
            ps.setString(7, autore);
            ps.setString(8, nomeBacheca);
            ps.setString(9, oldNome);
            ps.executeUpdate();
            ps.close();

            aggiungiCondivisione(nomeBacheca, newNome, autore, utenteLoggato);



        }


    }
    public void spostaToDo(String nomeBacheca1, String nomeToDo, String nomeBacheca2, String utenteLoggato) throws SQLException {

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


    }


    public void swapToDoOrder(String nomeBacheca, String nomeToDo, int nuovoOrdine, String utenteLoggato) throws SQLException {

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

    }

    public boolean autoreToDo(String nomeBacheca, String nomeTodo, String utenteLoggato) throws SQLException {
        String query = "SELECT 1 FROM TODO WHERE nomeBacheca = ? AND titolo = ? AND autore = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeBacheca);
        ps.setString(2, nomeTodo);
        ps.setString(3, utenteLoggato);

        ResultSet rs = ps.executeQuery();
        boolean esiste = rs.next();
        rs.close();
        ps.close();
        return esiste;
    }



    public void aggiungiCondivisione(String nomeBacheca, String nomeToDo, String utenteLoggato, String destinatario) throws SQLException{
        String query = "INSERT INTO CONDIVISIONE VALUES(?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, destinatario);
        ps.setString(2, nomeToDo);
        ps.setString(3, nomeBacheca);
        ps.setString(4, utenteLoggato);
        ps.executeUpdate();
        ps.close();

    }

    public void removeToDo(String nomeBacheca, String nomeToDo, String utenteLoggato) throws SQLException{
        String query = "DELETE FROM TODO where titolo = ? AND nomeBacheca = ? AND autore = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeToDo);
        ps.setString(2, nomeBacheca);
        ps.setString(3, utenteLoggato);
        ps.executeUpdate();
        ps.close();


    }

    public void removeCondivisione(String nomeBacheca, String nomeToDo, String utenteLoggato, String destinatario) throws SQLException{

        String query2 = "DELETE FROM CONDIVISIONE WHERE todotitolo = ? AND todoBachecaNome = ? AND todoautorenome = ? AND destinatario = ?";

        PreparedStatement ps = connection.prepareStatement(query2);
        ps.setString(1, nomeToDo);
        ps.setString(2, nomeBacheca);
        ps.setString(3, utenteLoggato);
        ps.setString(4, destinatario);
        ps.executeUpdate();
        ps.close();
        ps.close();
    }
    public void removeCondivisione(String nomeBacheca, String nomeToDo, String utenteLoggato) throws SQLException{
        String query1 = "SELECT todoautorenome FROM CONDIVISIONE WHERE todotitolo = ? AND todoBachecaNome = ? AND destinatario = ?";
        PreparedStatement ps = connection.prepareStatement(query1);
        ps.setString(1, nomeToDo);
        ps.setString(2, nomeBacheca);
        ps.setString(3, utenteLoggato);
        ResultSet rs = ps.executeQuery();
        String autore = "";
        if (rs.next()) {
            autore = rs.getString("todoautorenome");

            String query2 = "DELETE FROM CONDIVISIONE WHERE todotitolo = ? AND todoBachecaNome = ? AND todoautorenome = ? AND destinatario = ?";

            ps = connection.prepareStatement(query2);
            ps.setString(1, nomeToDo);
            ps.setString(2, nomeBacheca);
            ps.setString(3, autore);
            ps.setString(4, utenteLoggato);
            ps.executeUpdate();
            ps.close();

        }

        rs.close();
        ps.close();
    }

    public void getCondivisioniFromDB(String utenteLoggato, ArrayList<ArrayList<String>> dati) throws SQLException {
        dati.clear();
        dati.add(new ArrayList<>()); // titoli
        dati.add(new ArrayList<>()); // bacheche
        dati.add(new ArrayList<>()); // destinatari

        String query = "SELECT destinatario, todotitolo, todobachecanome FROM CONDIVISIONE WHERE todoautorenome = ? ORDER BY todotitolo";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utenteLoggato);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            dati.get(0).add(rs.getString("todotitolo"));
            dati.get(1).add(rs.getString("todobachecanome"));
            dati.get(2).add(rs.getString("destinatario"));
        }

        rs.close();
        ps.close();
    }




}
