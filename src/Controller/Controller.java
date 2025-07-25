package Controller;

import Database.DatabaseConnection;
import ImplementazioneDao.implBacheca;
import ImplementazioneDao.implToDo;
import ImplementazioneDao.implUtente;
import model.Bacheca;
import model.ToDo;

import DAO.bachecaDao;
import DAO.toDoDao;
import DAO.utenteDao;
import model.Utente;

import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Objects;

public class Controller {
    private final utenteDao uDAO;
    private final bachecaDao bDAO;
    private final toDoDao tDAO;

    Date Filter = null;

    private DatabaseConnection dbConnection = new DatabaseConnection();
    private ArrayList<Bacheca> Bacheche = new ArrayList<Bacheca>();
    private ArrayList<ToDo> ToDos = new ArrayList<ToDo>();
    private String utenteLoggato = null;

    public Controller() {
        this.uDAO = new implUtente();
        this.bDAO = new implBacheca();
        this.tDAO = new implToDo();
    }

//    public ArrayList<Utente> getUtenti(){
//        return utenti;
//    }

    public String getUtenteLoggato(){
        return utenteLoggato;
    }


    public void addUtente(String NomeUtente, char[] Password) throws SQLException {
    uDAO.addUtente(NomeUtente, Password);
    }

    public boolean LoginUtente(String nomeUtente, char[] password) throws SQLException {
        utenteLoggato = uDAO.loginUtente(nomeUtente, password);
        System.out.println(utenteLoggato);
        if(utenteLoggato != null)
        {
            getBachecheFromDB();
            return true;
        }



        return false;
    }

    public ArrayList<Bacheca> getBachecheFromDB() throws SQLException {
        this.Bacheche = new ArrayList<>();
        ArrayList<ArrayList<String>> bachecheInfo = new ArrayList<>();

        bachecheInfo.add(new ArrayList<>()); // lista per i titoli
        bachecheInfo.add(new ArrayList<>()); // lista per le descrizioni
        bDAO.getBacheche(bachecheInfo, utenteLoggato);


        if(bachecheInfo.getFirst().isEmpty())
            return Bacheche;

        for(int i = 0; i < bachecheInfo.getFirst().size(); i++){
            Bacheche.add(new Bacheca(bachecheInfo.get(0).get(i), bachecheInfo.get(1).get(i)));
        }
        getToDoFromDB();
        return Bacheche;
    }

    public ArrayList<ToDo> getToDoFromDB() throws SQLException {
//        ToDos = tDAO.getToDo();
        this.ToDos = new ArrayList<>();
        ArrayList<String> titoli = new ArrayList<>();
        ArrayList<String> descrizione = new ArrayList<>();
        ArrayList<Date> dateDiScadenza = new ArrayList<>();
        ArrayList<String> urls = new ArrayList<>();
        ArrayList<String> images = new ArrayList<>();
        ArrayList<String> coloriToDo = new ArrayList<>();
        ArrayList<Boolean> stati = new ArrayList<>();
        ArrayList<Integer> ordini = new ArrayList<>();
        ArrayList<String> nomeBacheca = new ArrayList<>();

        tDAO.getToDo(
                titoli,
                descrizione,
                dateDiScadenza,
                urls,
                images,
                coloriToDo,
                stati,
                ordini,
                nomeBacheca,
                utenteLoggato
        );

        for(int i = 0; i < titoli.size(); i++){
            ToDo todo = new ToDo(
                    titoli.get(i),
                    descrizione.get(i),
                    dateDiScadenza.get(i),
                    urls.get(i),
                    images.get(i),
                    coloriToDo.get(i),
                    stati.get(i),
                    ordini.get(i),
                    nomeBacheca.get(i)
            );
            ToDos.add(todo);
        }
        return ToDos;
    }

    public ArrayList<String> getSingleToDoDB(String nomeToDo, String nomeBacheca) throws SQLException {
        return tDAO.getSingleToDoDB(nomeToDo, nomeBacheca, utenteLoggato);
    }

    public ArrayList<String> getSingleToDo(String nomeToDo, String nomeBacheca) throws SQLException {
        ArrayList<String> caratteristiche = new ArrayList<>();
        for(ToDo t : ToDos)
            if(t.nomeBacheca.equals(nomeBacheca) && t.titolo.equals(nomeToDo)){
                caratteristiche.add(t.descrizione);
                caratteristiche.add(String.valueOf(t.dataDiScadenza));
                caratteristiche.add(t.URL);
                caratteristiche.add(String.valueOf(t.completato));

                return caratteristiche;
            }

        return null;
    }


    public void addToDoDB(String nomeToDo, String nomeBacheca, String descrizione) throws SQLException {
        tDAO.addToDoDB(nomeToDo, nomeBacheca , descrizione, utenteLoggato);
        getToDoFromDB();
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

        for (int i = 0; i < Bacheche.size(); i++) {
            ArrayList<String> titoli = new ArrayList<>();
            for (ToDo t : ToDos) {

                if(Filter == null){
                    if(t.nomeBacheca.equals(Bacheche.get(i).getTitolo()))
                        titoli.add(t.getTitolo());
                }else
                {
//                    System.out.println(t.dataDiScadenza + "   " + Filter);

//                    if((String.valueOf(t.dataDiScadenza).equals(String.valueOf(Filter)))){
//                        System.out.println("UGUALI!!");
//                    }

                    if(t.nomeBacheca.equals(Bacheche.get(i).getTitolo()) && (String.valueOf(t.dataDiScadenza).equals(String.valueOf(Filter))))
                        titoli.add(t.getTitolo());
                }


            }
            result.add(titoli);
        }
        return result;
    }

    public ArrayList<ArrayList<String>> getTuttiColoriToDo() {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int i = 0; i < Bacheche.size(); i++) {
            ArrayList<String> colori = new ArrayList<>();
            for (ToDo t : ToDos) {
                if(Filter == null){
                    if(t.nomeBacheca.equals(Bacheche.get(i).getTitolo()))
                        colori.add(t.Colore);
                }else
                {
//                    System.out.println(t.dataDiScadenza + "   " + Filter);

//                    if((String.valueOf(t.dataDiScadenza).equals(String.valueOf(Filter)))){
//                        System.out.println("UGUALI!!");
//                    }

                    if(t.nomeBacheca.equals(Bacheche.get(i).getTitolo()) && (String.valueOf(t.dataDiScadenza).equals(String.valueOf(Filter))))
                        colori.add(t.Colore);
                }
            }
            result.add(colori);
        }
        return result;
    }

    public void updateToDo(String newNome, String oldNome, String descrizione, String dataDiScadenza, String url, Color coloreScelto, boolean completato, String nomeBacheca) throws SQLException {
       tDAO.updateToDo(newNome, oldNome, descrizione, dataDiScadenza, url, coloreScelto, completato, nomeBacheca, utenteLoggato);
        getToDoFromDB();
    }


    public void addBacheca(int scelta) throws SQLException{
        bDAO.addBacheca(scelta, utenteLoggato);
        getBachecheFromDB();
    }

    public void removeBacheca(String nomeBacheca) throws SQLException {
       bDAO.removeBacheca(nomeBacheca, utenteLoggato);
       getBachecheFromDB();
    }

    public boolean cercaToDo(String nomeBacheca, String nomeToDo){

        for(ToDo t : ToDos){
            if(t.getTitolo().equals(nomeToDo) && t.nomeBacheca.equals(nomeBacheca)){
                return true;
            }
        }
        return false;
    }

    public void spostaToDo(String nomeBacheca1, String nomeToDo, String nomeBacheca2) throws SQLException {
        tDAO.spostaToDo(nomeBacheca1, nomeToDo, nomeBacheca2, utenteLoggato);
        getToDoFromDB();
    }


    public void swapToDoOrder(String nomeBacheca, String nomeToDo, int nuovoOrdine) throws SQLException {
        tDAO.swapToDoOrder(nomeBacheca, nomeToDo, nuovoOrdine, utenteLoggato);
        getToDoFromDB();
    }

    public boolean autoreToDo(String nomeBacheca, String nomeToDo) throws SQLException {
       return tDAO.autoreToDo(nomeBacheca, nomeToDo, utenteLoggato);
    }

    public void aggiungiCondivisione(String nomeBacheca1, String nomeToDo, String destinatario) throws SQLException {
        tDAO.aggiungiCondivisione(nomeBacheca1, nomeToDo, utenteLoggato, destinatario);

    }

    public void removeToDo(String nomeBacheca, String nomeToDo) throws SQLException {
        tDAO.removeToDo(nomeBacheca, nomeToDo, utenteLoggato);
        getToDoFromDB();

    }

    public void removeCondivisione(String nomeBacheca, String nomeToDo, String destinatario) throws SQLException {
        tDAO.removeCondivisione(nomeBacheca, nomeToDo, utenteLoggato, destinatario);
        getToDoFromDB();
    }

    public void removeCondivisione(String nomeBacheca, String nomeToDo) throws SQLException {
        tDAO.removeCondivisione(nomeBacheca, nomeToDo, utenteLoggato);
        getToDoFromDB();
    }

    public ArrayList<ArrayList<String>> getCondivisioni() throws SQLException {
        ArrayList<ArrayList<String>> dati = new ArrayList<>();

        tDAO.getCondivisioniFromDB(utenteLoggato, dati);

        return dati;
    }

    public void setFiltro(Date data) {
        Filter = data;
    }

    public void setFiltro() {
        Filter = new Date(System.currentTimeMillis());

    }


//        public Controller() throws SQLException {
//        Bacheche = getBacheche();
//        }
//
//
}
