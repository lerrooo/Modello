package gui;

import Controller.Controller;
import org.postgresql.jdbc2.ArrayAssistant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainGUI {

    private JPanel MainGUIPanel;
    public static JFrame frame;
    private static Controller controller;
    private static final ArrayList<JPanel> BachecheJPanel = new ArrayList<>();
    JPanel centerPanelContainer = new JPanel(new GridLayout(1, 3));

    public MainGUI(JFrame frameChiamante, Controller controller) {
        MainGUI.controller = controller;

        //Creazione finestra principale
        frame = new JFrame("Interfaccia principale");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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

//        //Prendiamo tutti i titoli delle bacheche dal DB
//        ArrayList<String> titoliBacheche = controller.getTitoliBacheche();
//
//        //Per ogni titolo creiamo una bacheca (JPanel)
//        for(String titolo : titoliBacheche){
//            centerPanelContainer.add(createBachecaPanel(titolo));
//        }
//
//        coloraPanels();
//
//        //Aggiungiamo i ToDo
//        ArrayList<ArrayList<String>> titoliToDo = controller.getTuttiTitoliToDo();
//
//        for(int i = 0; i < titoliToDo.size(); i++){
//            for(String titolo : titoliToDo.get(i)){
//                System.out.println(titolo + " " + titoliBacheche.get(i));
//                addToDo(titolo, null, titoliBacheche.get(i));
//            }
//        }

        buildPanels(centerPanelContainer);
        coloraPanels();

        // Crea il popup menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem aggiungiBachecaItem = new JMenuItem("Aggiungi bacheca");
        JMenuItem spostaToDoItem = new JMenuItem("Sposta ToDo in una nuova bacheca");
        popupMenu.add(aggiungiBachecaItem);
        popupMenu.add(spostaToDoItem);

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

                    }catch (Exception ex)
                    {
                        System.out.println(ex.getMessage());
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
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });


        menuButton.addActionListener(e -> {
            popupMenu.show(menuButton, 0, menuButton.getHeight());

        });


    }
    private JPanel createBachecaPanel(String nomeBacheca){
        JPanel bachecaJPanel = new JPanel(new BorderLayout());
        bachecaJPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        //Top panel con il titolo
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        JLabel titolo = new JLabel(nomeBacheca);
        titolo.setName("titolo");
        titolo.putClientProperty("id", "titolo");

        titolo.setHorizontalAlignment(SwingConstants.CENTER);
        titolo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonsRow = new JPanel(new BorderLayout());
        JButton removeButton = new JButton("x");
        removeButton.setName("removeButton");

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
                    //controller.getUtenteLoggato().bacheche.get(indexBacheca).toDoList.remove(indexToDo);
                    try {
                        controller.removeBacheca(titolo.getText());

                        parent.remove(bachecaJPanel);

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    parent.revalidate();
                    parent.repaint();

                }


            }
        });

        JButton searchButton = new JButton("+ Cerca");



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

        plusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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

                    ToDoGUI guiToDo = new ToDoGUI(toDoPanel, toDoButton, search, nomeBacheca, controller);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }else {
                JOptionPane.showMessageDialog(frame, "Nessun ToDo trovato con il nome " + search);
            }


        });

        BachecheJPanel.add(bachecaJPanel);
        return bachecaJPanel;
    }

    private static void coloraPanels() {
        for (JPanel panel : BachecheJPanel) {
            JLabel lbl = (JLabel) findComponentByName(panel, "titolo");
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
                }
            }
        }
    }

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

    private void addToDo(String nomeTodo, String Colore, String nomeBacheca) {

        JButton newButton = new JButton(nomeTodo);
        newButton.setName(nomeTodo);

        if(Colore == null)
            newButton.setBackground(Color.white);
        else{
            newButton.setBackground(Color.decode(Colore));
            newButton.setForeground(coloreComplementare(Color.decode(Colore)));
        }

        for (JPanel panel : BachecheJPanel) {
            JLabel lbl = (JLabel) findComponentByName(panel, "titolo");
            if(lbl != null && lbl.getText().equals(nomeBacheca)){

                JPanel toDoPanel = (JPanel) findComponentByName(panel, "toDoPanel");

                if(toDoPanel != null){

                    Dimension fixedSize = new Dimension((int)(BachecheJPanel.getFirst().getWidth() * 0.90), 50);

                    newButton.setPreferredSize(fixedSize);
                    newButton.setMinimumSize(fixedSize);
                    newButton.setMaximumSize(fixedSize);

                    newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    toDoPanel.add(Box.createVerticalStrut(20)); // spazio verticale di 20px
                    //System.out.println("bottone creato");

                    final boolean[] isDragging = {false};
                    //apre l'interfaccia del todo quando clicchi
                    newButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            if(isDragging[0])
                                return;

                            try {
                                ToDoGUI guiToDo = new ToDoGUI(toDoPanel, newButton, newButton.getText(), nomeBacheca, controller);

                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            ToDoGUI.frameTodo.setVisible(true);
                        }
                    });

                    // Coordinate relative per il drag
                    final Point[] offset = {new Point()};
                    final int startY = newButton.getY();

                    //trascinamento
                    newButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            // Registra la posizione relativa del mouse rispetto al bottone
                            offset[0].y = e.getY();
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                            if(!isDragging[0])
                                return;

                            isDragging[0] = false;
                            // Centro verticale del bottone rilasciato
                            int centerY = newButton.getY() + newButton.getHeight() / 2;

                            // Trova tutti gli altri bottoni nel pannello (escludendo il trascinato)
                            ArrayList<JButton> todoButtonsArr = findAllButtons(toDoPanel);
                            todoButtonsArr.remove(newButton);

                            // Trova la posizione in cui dovrebbe essere inserito
                            int insertIndex = 1;

                            for (int i = 0; i < todoButtonsArr.size(); i++) {
                                JButton other = todoButtonsArr.get(i);
                                int otherCenterY = other.getY() + other.getHeight() / 2;

                                // Se il centro del bottone rilasciato è sopra il centro di questo bottone,
                                // allora va inserito prima di lui
                                if (centerY < otherCenterY) {
                                    insertIndex = i + 1;
                                    break;
                                }

                                // Altrimenti continua e lo inseriamo dopo tutti
                                insertIndex = i + 2;
                            }

                            try {
                                controller.swapToDoOrder(nomeBacheca, newButton.getText(), insertIndex);
                                buildPanels(centerPanelContainer);
                                coloraPanels();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }

                            System.out.println("Nuova posizione logica: " + insertIndex);
                        }

                    });

                    newButton.addMouseMotionListener(new MouseMotionAdapter() {
                        @Override
                        public void mouseDragged(MouseEvent e) {
                            // Nuova posizione del bottone in base al movimento del mouse
                            isDragging[0] = true;
                            int newY = newButton.getY() + e.getY() - offset[0].y;
                            newButton.setLocation(newButton.getX(), newY);
                        }
                    });


                    toDoPanel.add(newButton);
                    toDoPanel.revalidate();
                    toDoPanel.repaint();
                }


            }
        }

    }

    private static Color coloreComplementare(Color colore) {

        if(colore == null)
            return Color.WHITE;

        int complementoRosso = 255 - colore.getRed();
        int complementoVerde = 255 - colore.getGreen();
        int complementoBlu = 255 - colore.getBlue();
        return new Color(complementoRosso, complementoVerde, complementoBlu);
    }


}
