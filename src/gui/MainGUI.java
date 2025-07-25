package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe MainGUI che si occupa di creare l'interfaccia delle bacheche
 */
public class MainGUI {
    private static final String TITOLO = "titolo";
    public JPanel mainGUIPanel;
    public static JFrame frame;
    private static Controller controller;
    private static final ArrayList<JPanel> BachecheJPanel = new ArrayList<>();
    JPanel centerPanelContainer = new JPanel(new GridLayout(1, 3));
    /**
     * Si occupa di creare tutta la GUI quando viene instanziata dal Login
     *
     */

    public MainGUI(Controller controller) {
        this.controller = controller;

        //Creazione finestra principale
        frame = new JFrame("Interfaccia principale");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // Barra in alto con bottone menu
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton menuButton = new JButton("⋮");
        menuButton.setFocusable(false);
        menuButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        topBar.add(menuButton);
        frame.add(topBar, BorderLayout.NORTH);
        frame.add(centerPanelContainer, BorderLayout.CENTER);

        // Crea il popup menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem aggiungiBachecaItem = new JMenuItem("Aggiungi bacheca");
        JMenuItem spostaToDoItem = new JMenuItem("Sposta ToDo in una nuova bacheca");
        JMenuItem aggiungiCondivisioneItem = new JMenuItem("Aggiungi condivisione");
        JMenuItem visualizzaCondivisioneItem = new JMenuItem("Visualizza condivisioni");
        JMenuItem aggiungiFiltroDataItem = new JMenuItem("Filtra ToDo per data");
        JMenuItem logOutItem = new JMenuItem("Logout");

        popupMenu.add(aggiungiBachecaItem);
        popupMenu.add(spostaToDoItem);
        popupMenu.add(aggiungiCondivisioneItem);
        popupMenu.add(visualizzaCondivisioneItem);
        popupMenu.add(aggiungiFiltroDataItem);
        popupMenu.add(logOutItem);

        //Action listeners del menù
        aggiungiBachecaItem.addActionListener(e -> {
                    String[] opzioni = {"Università", "Tempo Libero", "Lavoro"};

                    int scelta = JOptionPane.showOptionDialog(
                            null,
                            "Scegli il tipo di bacheca da creare:",
                            "Nuova Bacheca",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opzioni,
                            opzioni[0]
                    );
                    try{
                        controller.addBacheca(scelta);
                        buildPanels(centerPanelContainer);
                        coloraPanels();

                    }catch (Exception _)
                    {
                        //Catch block
                    }
                });
        spostaToDoItem.addActionListener(e -> {

            ArrayList<String> titoli = controller.getTitoliBacheche();

            String[] opzioni = titoli.toArray(new String[0]);

            int scelta = JOptionPane.showOptionDialog(
                    null,
                    "Scegli il tipo di bacheca da cui spostare il ToDo:",
                    "Sposta il ToDo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opzioni,
                    opzioni[0]
            );

            if(scelta == -1)
                return;

            String nomeBacheca1 = opzioni[scelta];
            titoli.remove(nomeBacheca1);

            String nomeToDo = JOptionPane.showInputDialog("Inserisci il nome del ToDo");

            opzioni = titoli.toArray(new String[0]);

            scelta = JOptionPane.showOptionDialog(
                    null,
                    "Scegli il tipo di bacheca da creare:",
                    "Nuova Bacheca",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opzioni,
                    opzioni[0]
            );

            String nomeBacheca2 = opzioni[scelta];

            try {
                controller.spostaToDo(nomeBacheca1, nomeToDo, nomeBacheca2);
                buildPanels(centerPanelContainer);
                coloraPanels();
            } catch (SQLException _) {
                //Catch block
            }

        });
        aggiungiCondivisioneItem.addActionListener(e -> {

            ArrayList<String> titoli = controller.getTitoliBacheche();

            String[] opzioni = titoli.toArray(new String[0]);

            int scelta = JOptionPane.showOptionDialog(
                    null,
                    "Scegli il tipo di bacheca da cui condividere il ToDo:",
                    "Sposta il ToDo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opzioni,
                    opzioni[0]
            );

            if(scelta == -1)
                return;

            String nomeBacheca1 = opzioni[scelta];

            String nomeToDo = JOptionPane.showInputDialog("Inserisci il nome del ToDo");
            String destinatario = JOptionPane.showInputDialog("Inserisci il nome dell'utente con cui condividere il ToDo");
            try {
                controller.aggiungiCondivisione(nomeBacheca1, nomeToDo, destinatario);
            } catch (SQLException _) {
                //Catch block
            }

        });
        visualizzaCondivisioneItem.addActionListener(e -> {
            try {
                new ToDoCondivisi(controller);
            } catch (SQLException _) {
                //Catch block
            }

        });
        aggiungiFiltroDataItem.addActionListener(e -> {
            int scelta = JOptionPane.showOptionDialog(
                    null,
                    "Scegli la data per cui filtrare",
                    "Filtra per data",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Data odierna", "Data personalizzata", "Rimuovi filtro"},
                    "Data odierna"
            );

            if(scelta == -1)
                return;

            if(scelta == 0){
                controller.setFiltro();
            } else if (scelta == 1) {

                String dataStr = JOptionPane.showInputDialog(null, "Inserisci una nuova data di scadenza (YYYY-MM-DD)", "Modifica ToDo", JOptionPane.INFORMATION_MESSAGE);

                try {
                    LocalDate data = LocalDate.parse(dataStr); // converte da stringa a LocalDate
                    controller.setFiltro(Date.valueOf(data));
                } catch (Exception _) {
                    JOptionPane.showMessageDialog(frame, "Formato data non valido", "Errore", JOptionPane.INFORMATION_MESSAGE);
                }


            }else if (scelta == 2){
                controller.setFiltro(null);

            }

            buildPanels(centerPanelContainer);
            coloraPanels();

        });
        logOutItem.addActionListener(e -> {
            int scelta = JOptionPane.showOptionDialog(
                    null,
                    "Disconnettersi?",
                    "Logout",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Sì", "No"},
                    "No"
            );

            if(scelta == 0){
                frame.dispose();
                Login.showLogin(); // Riapri Login

            }

        });

        menuButton.addActionListener(_ -> popupMenu.show(menuButton, 0, menuButton.getHeight()));

        frame.addWindowStateListener(e -> {
            boolean isMaximized = (e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH;
            if (isMaximized) {
                buildPanels(centerPanelContainer);
                coloraPanels();
            }
        });

        buildPanels(centerPanelContainer);
        coloraPanels();
    }
    /**
     * Crea la bacheca con il nome associato a @param nomeBacheca
     **/
    private JPanel createBachecaPanel(String nomeBacheca){
        JPanel bachecaJPanel = new JPanel(new BorderLayout());
        bachecaJPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        //Top panel con il titolo
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        JLabel titolo = new JLabel(nomeBacheca);
        titolo.setName(TITOLO);
        titolo.putClientProperty("id", TITOLO);

        titolo.setHorizontalAlignment(SwingConstants.CENTER);
        titolo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonsRow = new JPanel(new BorderLayout());
        JButton removeButton = new JButton("x");
        removeButton.setName("UI");

        removeButton.addActionListener(e -> {
            Container parent = bachecaJPanel.getParent();
            if (parent != null) {

                int risposta = JOptionPane.showConfirmDialog(
                        null,
                        "Sei sicuro di voler procedere? La bacheca e i ToDo verranno eliminati",
                        "Conferma",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE

                );

                if(risposta == JOptionPane.OK_OPTION){
                    try {
                        controller.removeBacheca(titolo.getText());

                        parent.remove(bachecaJPanel);

                    } catch (SQLException _) {
                        //Catch block
                    }

                    parent.revalidate();
                    parent.repaint();

                }


            }
        });

        JButton searchButton = new JButton("+ Cerca");
        searchButton.setName("UI");

        buttonsRow.add(searchButton, BorderLayout.WEST);
        buttonsRow.add(removeButton, BorderLayout.EAST);
        buttonsRow.setOpaque(false);

        topPanel.add(titolo);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(buttonsRow);

        JPanel toDoPanel = new JPanel();
        toDoPanel.setLayout(new BoxLayout(toDoPanel, BoxLayout.Y_AXIS));
        toDoPanel.setName("toDoPanel");
        toDoPanel.setOpaque(false);

        JButton plusButton = new JButton("+ Aggiungi ToDo");
        plusButton.setName("UI");

        plusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        plusButton.addActionListener(e -> {
                String nomeTemp = JOptionPane.showInputDialog(null, "Inserire nomeToDo", "Crea ToDo", JOptionPane.INFORMATION_MESSAGE);

                //inserire check per unique al nome di bacheca
                if(nomeTemp == null || nomeTemp.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "inserimento fallito");
                    return;
                }

                String descTemp = JOptionPane.showInputDialog(null, "Inserire descrizione ToDo", "Crea ToDo", JOptionPane.INFORMATION_MESSAGE);
                if(descTemp == null || descTemp.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "inserimento fallito");
                    return;
                }

                try {
                    controller.addToDoDB(nomeTemp, titolo.getText(), descTemp);
                    addToDo(nomeTemp, null, titolo.getText());
                } catch (SQLException _) {
                    //Catch block
                }
        });

        bachecaJPanel.add(topPanel, BorderLayout.NORTH);
        bachecaJPanel.add(toDoPanel, BorderLayout.CENTER);
        bachecaJPanel.add(plusButton, BorderLayout.SOUTH);


        searchButton.addActionListener(e -> {
            String search = JOptionPane.showInputDialog(
                    null,
                    "Inserisci il nome del ToDo da cercare nella bacheca " + titolo.getText(),
                    "Cerca ToDo", // <-- Questo è il titolo della finestra
                    JOptionPane.PLAIN_MESSAGE
            );

            if(controller.cercaToDo(titolo.getText(), search)){

                try {
                    JButton toDoButton = (JButton) findComponentByName(toDoPanel, search);

                    if(toDoButton == null)
                        return;

                    new ToDoGUI(toDoPanel, toDoButton, search, nomeBacheca, controller, controller.autoreToDo(nomeBacheca, titolo.getText()));
                } catch (SQLException _) {
                    //Catch block
                }
            }else {
                JOptionPane.showMessageDialog(frame, "Nessun ToDo trovato con il nome " + search);
            }


        });

        BachecheJPanel.add(bachecaJPanel);
        return bachecaJPanel;
    }
    /**
     * Si occupa di colorare i pannelli (bacheche) in base al nome per varietà
     **/
    private static void coloraPanels() {
        for (JPanel panel : BachecheJPanel) {
            JLabel lbl = (JLabel) findComponentByName(panel, TITOLO);
            if (lbl != null) {
                switch (lbl.getText()) {
                    case "Università" -> {
                        panel.setBackground(Color.decode("#5FBB97"));
                        lbl.setForeground(Color.white);
                    }
                    case "Lavoro" -> {
                        panel.setBackground(Color.decode("#8DDCA4"));
                        lbl.setForeground(Color.decode("#72235b"));
                    }
                    case "Tempo Libero" -> {
                        panel.setBackground(Color.decode("#63326e"));
                        lbl.setForeground(Color.decode("#9ccd91"));
                    }
                    default -> {
                        panel.setBackground(Color.decode("#000000"));
                        lbl.setForeground(Color.decode("#FFFFFF"));
                    }
                }
            }
        }
    }
    /**
     * Metodo utilizzato per trovare un componente
     * @param name il nome del componente da cercare
     * @param container l'istanza del container in cui cercare
     */
    private static Component findComponentByName(Container container, String name) {
        for (Component c : container.getComponents()) {
            if (name.equals(c.getName())) {
                return c;
            }
            if (c instanceof Container) {
                Component child = findComponentByName((Container) c, name);
                if (child != null) return child;
            }
        }
        return null;
    }
    /**
     * Metodo che trova tutti i bottoni in un container
     * @param container l'istanza del container in cui cercare
     */
    private static ArrayList<JButton> findAllButtons(Container container) {
        ArrayList<JButton> buttons = new ArrayList<>();

        for (Component c : container.getComponents()) {
            if (c instanceof JButton) {
                buttons.add((JButton) c);
            }

            if (c instanceof Container) {
                buttons.addAll(findAllButtons((Container) c));
            }
        }

        return buttons;
    }
    /**
     * Costruisce tutti i pannelli (bacheche) nella gui
     * @param centerPanelContainer l'istanza del container principale
     */
    private void buildPanels(JPanel centerPanelContainer){
        BachecheJPanel.clear();
        centerPanelContainer.removeAll();

        //Prendiamo tutti i titoli delle bacheche dal DB
        ArrayList<String> titoliBacheche = controller.getTitoliBacheche();

        //Per ogni titolo creiamo una bacheca (JPanel)
        for(String titolo : titoliBacheche){
            centerPanelContainer.add(createBachecaPanel(titolo));
        }

        frame.setVisible(true);

        //Aggiungiamo i ToDo
        ArrayList<ArrayList<String>> titoliToDo = controller.getTuttiTitoliToDo();
        ArrayList<ArrayList<String>> coloriToDo = controller.getTuttiColoriToDo();

        //i == contatore delle bacheche, j == contatore dei todo
        for(int i = 0; i < titoliToDo.size(); i++){
            for(int j = 0; j < titoliToDo.get(i).size(); j++){

                addToDo(titoliToDo.get(i).get(j), coloriToDo.get(i).get(j), titoliBacheche.get(i));


            }
        }
    }
    /**
     * Aggiunge un ToDo alla bacheca e al database con l'ultilizzo del controller
     * @param nomeTodo Il nome del ToDo da aggiungere
     * @param colore Il colore del ToDo
     * @param nomeBacheca Il nome della bacheca associata al ToDo
     */
    private void addToDo(String nomeTodo, String colore, String nomeBacheca) {

        JButton newButton = new JButton(nomeTodo);
        newButton.setName(nomeTodo);

        if(colore == null)
            newButton.setBackground(Color.white);
        else{
            newButton.setBackground(Color.decode(colore));
            newButton.setForeground(coloreComplementare(Color.decode(colore)));
        }

        for (JPanel panel : BachecheJPanel) {
            JLabel lbl = (JLabel) findComponentByName(panel, TITOLO);
            if(lbl != null && lbl.getText().equals(nomeBacheca)){

                JPanel toDoPanel = (JPanel) findComponentByName(panel, "toDoPanel");

                if(toDoPanel != null){

                    Dimension fixedSize = new Dimension(Integer.MAX_VALUE, 50);

                    newButton.setPreferredSize(fixedSize);
                    newButton.setMinimumSize(fixedSize);
                    newButton.setMaximumSize(fixedSize);

                    newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    toDoPanel.add(Box.createVerticalStrut(20)); // spazio verticale di 20px

                    final boolean[] isDragging = {false};
                    final boolean[] isYours = {true};
                    newButton.addActionListener(e ->{

                            if(isDragging[0])
                                return;


                            new ToDoGUI(toDoPanel, newButton, newButton.getText(), nomeBacheca, controller, isYours[0]);

                            ToDoGUI.frameTodo.setVisible(true);

                    });

                    // Coordinate relative per il drag
                    final Point[] offset = {new Point()};

                    //trascinamento
                    newButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            if(!isYours[0])
                                return;
                            // Registra la posizione relativa del mouse rispetto al bottone
                            offset[0].y = e.getY();
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            if (!isYours[0])
                                return;

                            if (!isDragging[0])
                                return;

                            isDragging[0] = false;

                            // Top Y del bottone rilasciato (senza centro)
                            int buttonTopY = newButton.getY();

                            // Trova tutti i bottoni trascinabili (escludi quelli fissi tipo "condividi", ecc.)
                            ArrayList<JButton> todoButtonsArr = findAllButtons(toDoPanel);
                            todoButtonsArr.remove(newButton); // rimuove se stesso

                            // Applichiamo solo ai bottoni trascinabili

                            // Trova la nuova posizione
                            int insertIndex = 0;

                            for (int i = 0; i < todoButtonsArr.size(); i++) {
                                JButton other = todoButtonsArr.get(i);

                                if (buttonTopY < other.getY()) {
                                    insertIndex = i + 1; // lo mettiamo subito prima
                                    break;
                                }

                                insertIndex = i + 2; // altrimenti dopo tutti
                            }

                            try {
                                controller.swapToDoOrder(nomeBacheca, newButton.getText(), insertIndex);
                                buildPanels(centerPanelContainer);
                                coloraPanels();
                            } catch (SQLException _) {
                                //Catch block
                            }

                        }


                    });

                    newButton.addMouseMotionListener(new MouseMotionAdapter() {
                        @Override
                        public void mouseDragged(MouseEvent e) {
                            if(!isYours[0])
                                return;
                            // Nuova posizione del bottone in base al movimento del mouse
                            isDragging[0] = true;
                            int newY = newButton.getY() + e.getY() - offset[0].y;
                            newButton.setLocation(newButton.getX(), newY);
                        }
                    });


                    try{
                        if(!controller.autoreToDo(nomeBacheca, newButton.getText())){
                            isYours[0] = false;
                            // Separatore
                            JSeparator separator = new JSeparator();
                            separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10)); // lo estende orizzontalmente
                            toDoPanel.add(separator);
                            toDoPanel.add(Box.createVerticalStrut(20)); // spazio verticale di 20px

                        }
                    } catch (Exception _) {
                        //Catch block
                    }

                    toDoPanel.add(newButton);
                    toDoPanel.revalidate();
                    toDoPanel.repaint();
                }


            }
        }

    }
    /**
     * Un semplice metodo per trovare il colore complementare
     * @param colore il colore da complementare
     * @return colore complementare
     */
    private static Color coloreComplementare(Color colore) {

        if(colore == null)
            return Color.WHITE;

        int complementoRosso = 255 - colore.getRed();
        int complementoVerde = 255 - colore.getGreen();
        int complementoBlu = 255 - colore.getBlue();
        return new Color(complementoRosso, complementoVerde, complementoBlu);
    }


}
