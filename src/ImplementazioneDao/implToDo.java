package ImplementazioneDao;
import DAO.toDoDao;
import Database.DatabaseConnection;
import model.ToDo;

import java.awt.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            titoli.add(rs.getString("Titolo"));
            descrizione.add(rs.getString("Descrizione"));
            dateDiScadenza.add(rs.getDate("dataDiScadenza"));
            urls.add(rs.getString("Url"));
            images.add(rs.getString("Image"));
            coloriToDo.add(rs.getString("coloreSfondo"));
            stati.add(rs.getBoolean("completato"));
            ordini.add(rs.getInt("ordine"));
            nomeBacheca.add(rs.getString("nomeBacheca"));
        }

        rs.close();
        ps.close();
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


        String query = "INSERT INTO TODO(titolo, descrizione, datadiscadenza, autore, nomeBacheca) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeToDo);
        ps.setString(2, descrizione);
        ps.setDate(3, java.sql.Date.valueOf("2026-01-01"));
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
        ps.executeUpdate();
        ps.close();


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
}
