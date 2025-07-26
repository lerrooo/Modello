package controller;

import implementazionedao.ImplBacheca;
import implementazionedao.ImplToDo;
import implementazionedao.ImplUtente;
import model.Bacheca;
import model.ToDo;

import dao.BachecaDao;
import dao.ToDoDao;
import dao.UtenteDao;

import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe che si occupa gestire il funziomaneto programma collegando GUI a DB
 */
public class Controller {
    private final UtenteDao uDAO;
    private final BachecaDao bDAO;
    private final ToDoDao tDAO;

    Date filter = null;

    private ArrayList<Bacheca> bacheche = new ArrayList<>();
    private ArrayList<ToDo> toDos = new ArrayList<>();
    private String utenteLoggato = null;

    public Controller() {
        this.uDAO = new ImplUtente();
        this.bDAO = new ImplBacheca();
        this.tDAO = new ImplToDo();
    }


    /**
     * Aggiunge un utente al DB
     * @param nomeUtente nome dell'utente da aggiungere
     * @param password dell'utente da aggiungere, char[] per gestione della password
     *
     */
    public void addUtente(String nomeUtente, char[] password) throws SQLException {
    uDAO.addUtente(nomeUtente, password);
    }
    /**
     * Verifica il login di un utente al DB
     * @param nomeUtente nome dell'utente
     * @param password password dell'utente
     * @return un flag booleano che indica se l'azione ha avuto successo o meno
     *
     */
    public boolean loginUtente(String nomeUtente, char[] password) throws SQLException {
            utenteLoggato = uDAO.loginUtente(nomeUtente, password);
            if(utenteLoggato != null)
            {
                getBachecheFromDB();
                return true;
            }
            return false;
    }
    /**
     * Prende tutte le bacheche dal DB
     * @return Arraylist di bacheche
     */
    public ArrayList<Bacheca> getBachecheFromDB() throws SQLException {
        this.bacheche = new ArrayList<>();
        ArrayList<ArrayList<String>> bachecheInfo = new ArrayList<>();

        bachecheInfo.add(new ArrayList<>()); // lista per i titoli
        bachecheInfo.add(new ArrayList<>()); // lista per le descrizioni
        bDAO.getBacheche(bachecheInfo, utenteLoggato);


        if(bachecheInfo.getFirst().isEmpty())
            return bacheche;

        for(int i = 0; i < bachecheInfo.getFirst().size(); i++){
            bacheche.add(new Bacheca(bachecheInfo.get(0).get(i), bachecheInfo.get(1).get(i)));
        }
        getToDoFromDB();
        return bacheche;
    }

    /**
     * Prende tutti i ToDO dal DB in base all'utente loggato
     * @return Arraylist di ToDo
     */
    public ArrayList<ToDo> getToDoFromDB() throws SQLException {
        this.toDos = new ArrayList<>();
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
            toDos.add(todo);
        }
        return toDos;
    }
    /**
     * Prende un singolo ToDo
     * @return Array list di String delle caratteristiche del ToDo
     */

    public ArrayList<String> getSingleToDo(String nomeToDo, String nomeBacheca) {
        ArrayList<String> caratteristiche = new ArrayList<>();
        for(ToDo t : toDos)
            if(t.nomeBacheca.equals(nomeBacheca) && t.titolo.equals(nomeToDo)){
                caratteristiche.add(t.descrizione);
                caratteristiche.add(String.valueOf(t.dataDiScadenza));
                caratteristiche.add(t.URL);
                caratteristiche.add(String.valueOf(t.completato));

                return caratteristiche;
            }

        return null;
    }

    /**
     * Aggiunge un ToDo al DB
     */
    public void addToDoDB(String nomeToDo, String nomeBacheca, String descrizione) throws SQLException {
        tDAO.addToDoDB(nomeToDo, nomeBacheca , descrizione, utenteLoggato);
        getToDoFromDB();
    }

    /**
     *
     * @return Ritorna la lista dei titoli delle bacheche
     */
    // Ritorna lista titoli delle bacheche
    public ArrayList<String> getTitoliBacheche() {
        ArrayList<String> titoli = new ArrayList<>();
        for (Bacheca b : bacheche) {
            titoli.add(b.getTitolo());
        }
        return titoli;
    }

    /**
     * @return Ritorna la lista dei titoli dei ToDo (Un ArrayList a bacheca)
     */
    public ArrayList<ArrayList<String>> getTuttiTitoliToDo() {

        ArrayList<ArrayList<String>> result = new ArrayList<>();

        for (int i = 0; i < bacheche.size(); i++) {
            ArrayList<String> titoli = new ArrayList<>();
            for (ToDo t : toDos) {

                if(filter == null && t.nomeBacheca.equals(bacheche.get(i).getTitolo())){
                        titoli.add(t.getTitolo());
                }else if(t.nomeBacheca.equals(bacheche.get(i).getTitolo()) && (String.valueOf(t.dataDiScadenza).equals(String.valueOf(filter))))
                        titoli.add(t.getTitolo());



            }
            result.add(titoli);
        }
        return result;
    }
    /**
     * @return Ritorna la lista dei colori dei ToDo (Un ArrayList a bacheca)
     */
    public ArrayList<ArrayList<String>> getTuttiColoriToDo() {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int i = 0; i < bacheche.size(); i++) {
            ArrayList<String> colori = new ArrayList<>();
            for (ToDo t : toDos) {
                if(filter == null && t.nomeBacheca.equals(bacheche.get(i).getTitolo())){

                        colori.add(t.Colore);
                }else if(t.nomeBacheca.equals(bacheche.get(i).getTitolo()) && (String.valueOf(t.dataDiScadenza).equals(String.valueOf(filter))))
                        colori.add(t.Colore);

            }
            result.add(colori);
        }
        return result;
    }
    /**
     * Aggiorna un TodDo
     * @param newNome il nuovo nome del ToDo
     * @param oldNome utilizzato per effettuare l'update nel DB
     * @param nomeBacheca utilizzata per individuare la bacheca da cui prendere il ToDo
     */
    public void updateToDo(String newNome, String oldNome, String descrizione, String dataDiScadenza, String url, Color coloreScelto, boolean completato, String nomeBacheca) throws SQLException {
       tDAO.updateToDo(newNome, oldNome, descrizione, dataDiScadenza, url, coloreScelto, completato, nomeBacheca, utenteLoggato);
        getToDoFromDB();
    }
    /**
     * Aggiunge una bacheca nel DB
     * @param scelta (0 = Università, 1 = Tempo Libero, 2 = Lavoro)
     */

    public void addBacheca(int scelta) throws SQLException{
        bDAO.addBacheca(scelta, utenteLoggato);
        getBachecheFromDB();
    }
    /**
     * Rimuove una bacheca in base al nome
     */
    public void removeBacheca(String nomeBacheca) throws SQLException {
       bDAO.removeBacheca(nomeBacheca, utenteLoggato);
       getBachecheFromDB();
    }
    /**
     * Cerca un ToDo in una bacheca
     */
    public boolean cercaToDo(String nomeBacheca, String nomeToDo){

        for(ToDo t : toDos){
            if(t.getTitolo().equals(nomeToDo) && t.nomeBacheca.equals(nomeBacheca)){
                return true;
            }
        }
        return false;
    }
    /**
     * Sposta un ToDo da un bacheca ad un'altra
     * @param nomeBacheca1 La bacheca di partenza
     * @param nomeBacheca2 La bacheca di nomeBacheca2
     */
    public void spostaToDo(String nomeBacheca1, String nomeToDo, String nomeBacheca2) throws SQLException {
        tDAO.spostaToDo(nomeBacheca1, nomeToDo, nomeBacheca2, utenteLoggato);
        getToDoFromDB();
    }

    /**
     * Sposta un ToDo nella stessa bacheca
     * @param nomeBacheca La bacheca da cui spostare
     * @param nomeToDo Il ToDo da spostare
     * @param nuovoOrdine Ordine del nuovo ToDO
     * */
    public void swapToDoOrder(String nomeBacheca, String nomeToDo, int nuovoOrdine) throws SQLException {
        tDAO.swapToDoOrder(nomeBacheca, nomeToDo, nuovoOrdine, utenteLoggato);
        getToDoFromDB();
    }
    /**
     * Interroga il DB per controllare se l'utente loggato possiede o no un ToDO (utilizzato per le condivisioni)
     **/
    public boolean autoreToDo(String nomeBacheca, String nomeToDo) throws SQLException {
       return tDAO.autoreToDo(nomeBacheca, nomeToDo, utenteLoggato);
    }
    /**
     * Aggiunge una condivisione al DB
     **/
    public void aggiungiCondivisione(String nomeBacheca1, String nomeToDo, String destinatario) throws SQLException {
        tDAO.aggiungiCondivisione(nomeBacheca1, nomeToDo, utenteLoggato, destinatario);

    }
    /**
     * Rimuove un ToDo dal DB
     **/
    public void removeToDo(String nomeBacheca, String nomeToDo) throws SQLException {
        tDAO.removeToDo(nomeBacheca, nomeToDo, utenteLoggato);
        getToDoFromDB();

    }
    /**
     * Rimuove una condivisione dal DB (4 parametri se si conosce il destinatario)
     **/
    public void removeCondivisione(String nomeBacheca, String nomeToDo, String destinatario) throws SQLException {
        tDAO.removeCondivisione(nomeBacheca, nomeToDo, utenteLoggato, destinatario);
        getToDoFromDB();
    }
    /**
     * Rimuove una condivisione dal DB (3 parametri se non si conosce il destinatario)
     **/
    public void removeCondivisione(String nomeBacheca, String nomeToDo) throws SQLException {
        tDAO.removeCondivisione(nomeBacheca, nomeToDo, utenteLoggato);
        getToDoFromDB();
    }
    /**
     * Ritorna per ogni bacheca le condivisioni dell'utente
     **/
    public ArrayList<ArrayList<String>> getCondivisioni() throws SQLException {
        ArrayList<ArrayList<String>> dati = new ArrayList<>();

        tDAO.getCondivisioniFromDB(utenteLoggato, dati);

        return dati;
    }
    /**
     * Imposta il filtro della data
     **/
    public void setFiltro(Date data) {
        filter = data;
    }
    /**
     * Imposta il filtro della data alla data odierna
     **/
    public void setFiltro() {
        filter = new Date(System.currentTimeMillis());

    }

}
