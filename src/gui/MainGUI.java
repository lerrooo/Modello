package gui;

import Controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainGUI {
    private JPanel MainGUIPanel;
    private JButton button1;
    private JButton addTodo1;
    private JPanel JPanelTodo1;
    public static JFrame frame;
    private static Controller controller;
    private static final JPanel[] BachecheJPanel = new JPanel[3];
    private static final JPanel[] CenterJPanel = new JPanel[3];
    private static final JLabel[] TitoliList = new JLabel[3];
    private static final JButton[] removeButtons = new JButton[3];
    private static final ArrayList<JButton> ButtonsList = new ArrayList<>();

    private static JPanel centerPanelsContainer;

    public MainGUI(JFrame frameChiamante, Controller controller) {
        MainGUI.controller = controller;

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

        // Container centrale con le bacheche
        centerPanelsContainer = new JPanel(new GridLayout(1, 3));
        frame.add(centerPanelsContainer, BorderLayout.CENTER);

        // Inizializza bacheche esistenti
        for (int i = 0; i < 3; i++) {
            centerPanelsContainer.add(createPanelWithButton(i));
        }

        coloraPanels();

        ArrayList<ArrayList<String>> tuttiTitoli = controller.getTuttiTitoliToDo();
        for (int i = 0; i < tuttiTitoli.size(); i++) {
            for (String titolo : tuttiTitoli.get(i)) {
                addToDo(titolo, i);
            }
        }

        // Aggiungi nuova bacheca al click sui tre puntini (qui però ha senso solo se fai bacheche dinamiche)
        menuButton.addActionListener(e -> {
            for (int i = 0; i < BachecheJPanel.length; i++) {
                if (BachecheJPanel[i] == null) {
                    JPanel panel = createPanelWithButton(i);
                    BachecheJPanel[i] = panel;

                    refreshCenterPanels(); // aggiorna la UI con la nuova bacheca al posto giusto
                    coloraPanels();
                    resizeLayout();

                    break;  // aggiungi una sola bacheca alla volta
                }
            }
        });



        frame.setVisible(true);

        // Resize responsivo
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeLayout();
            }
        });
        frame.addWindowStateListener(e -> resizeLayout());
    }

    private JPanel createPanelWithButton(int index) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        JLabel titolo = new JLabel(controller.getTitoliBacheche().get(index));
        titolo.setHorizontalAlignment(SwingConstants.CENTER);
        titolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        TitoliList[index] = titolo;

        JPanel buttonsRow = new JPanel(new BorderLayout());
        JButton removeButton = new JButton("x");
        JButton searchButton = new JButton("+ Cerca");
        removeButtons[index] = removeButton;

        removeButton.addActionListener(e -> {
            Container parent = mainPanel.getParent();
            if (parent != null) {
                parent.remove(mainPanel);

                BachecheJPanel[index] = null;
                CenterJPanel[index] = null;
                TitoliList[index] = null;
                removeButtons[index] = null;
                resizeLayout();
                parent.revalidate();
                parent.repaint();
            }
        });

        buttonsRow.add(searchButton, BorderLayout.WEST);
        buttonsRow.add(removeButton, BorderLayout.EAST);
        buttonsRow.setOpaque(false);

        topPanel.add(titolo);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(buttonsRow);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

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
                    addToDo(nomeTemp, index);
                    controller.addToDoDB(nomeTemp, index, descTemp);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(plusButton, BorderLayout.SOUTH);

        BachecheJPanel[index] = mainPanel;
        CenterJPanel[index] = centerPanel;

        return mainPanel;
    }

    private static void coloraPanels() {
        for (int i = 0; i < BachecheJPanel.length; i++) {
            JPanel panel = BachecheJPanel[i];
            JLabel titolo = TitoliList[i];
            if (panel == null || titolo == null) continue;

            switch (i % 3) {
                case 0 -> {
                    panel.setBackground(Color.decode("#5FBB97"));
                    titolo.setForeground(Color.white);
                }
                case 1 -> {
                    panel.setBackground(Color.decode("#8DDCA4"));
                    titolo.setForeground(Color.decode("#72235b"));
                }
                case 2 -> {
                    panel.setBackground(Color.decode("#63326e"));
                    titolo.setForeground(Color.decode("#9ccd91"));
                }
            }
        }
    }

    private static void resizeLayout() {
        boolean hasPanels = false;
        for (JPanel p : BachecheJPanel) {
            if (p != null) {
                hasPanels = true;
                break;
            }
        }
        if (!hasPanels) return;

        // Prendo il primo panel non null come riferimento
        JPanel firstPanel = null;
        for (JPanel p : BachecheJPanel) {
            if (p != null) {
                firstPanel = p;
                break;
            }
        }
        if (firstPanel == null) return;

        for (JLabel titolo : TitoliList) {
            if (titolo != null) {
                titolo.setPreferredSize(new Dimension((int) (firstPanel.getWidth() * 0.90), 50));
            }
        }
        for (JButton bottone : ButtonsList) {
            Dimension newSize = new Dimension((int) (firstPanel.getWidth() * 0.90), 50);
            bottone.setPreferredSize(newSize);
            bottone.setMinimumSize(newSize);
            bottone.setMaximumSize(newSize);
        }
        for (JPanel panel : BachecheJPanel) {
            if (panel != null)
                refreshAllComponents(panel);
        }
    }

    private static void addToDo(String nomeTodo, int indexBacheca) {
        if (indexBacheca < 0 || indexBacheca >= CenterJPanel.length) return;
        if (CenterJPanel[indexBacheca] == null) return;

        JButton newButton = new JButton(nomeTodo);
        newButton.setBackground(Color.white);
//        newButton.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        int indexToDo = utenteLoggato.bacheche.get(indexBacheca).findToDoIndex(newButton.getText());
//                        ToDoGUI guiToDo = new ToDoGUI(BachecheJPanel.get(indexBacheca), newButton, tempToDo, controller, indexBacheca, indexToDo);
//                        ToDoGUI.frameTodo.setVisible(true);
//                    }
//                });
        // Prendo primo panel non nullo come riferimento per larghezza
        JPanel firstPanel = null;
        for (JPanel p : BachecheJPanel) {
            if (p != null) {
                firstPanel = p;
                break;
            }
        }
        int widthRef = (firstPanel != null) ? firstPanel.getWidth() : 200;

        Dimension fixedSize = new Dimension((int)(BachecheJPanel[0].getWidth() * 0.90), 50);

        newButton.setPreferredSize(fixedSize);
        newButton.setMinimumSize(fixedSize);
        newButton.setMaximumSize(fixedSize);

        newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        ButtonsList.add(newButton);

        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        container.add(newButton, BorderLayout.CENTER);
        newButton.putClientProperty("container", container);

        CenterJPanel[indexBacheca].add(container);
        CenterJPanel[indexBacheca].revalidate();
        CenterJPanel[indexBacheca].repaint();
    }

    private void refreshCenterPanels() {
        centerPanelsContainer.removeAll();

        // Conta bacheche effettive
        int countNonNull = 0;
        for (JPanel p : BachecheJPanel) {
            if (p != null) countNonNull++;
        }

        // Se nessuna bacheca, almeno una colonna per non sfasare layout (opzionale)
        if (countNonNull == 0) countNonNull = 1;

        centerPanelsContainer.setLayout(new GridLayout(1, countNonNull));

        for (JPanel p : BachecheJPanel) {
            if (p != null) {
                centerPanelsContainer.add(p);
            }
        }

        centerPanelsContainer.revalidate();
        centerPanelsContainer.repaint();
    }



    static void refreshAllComponents(Container container) {
        container.revalidate();
        container.repaint();
        for (Component comp : container.getComponents()) {
            if (comp instanceof Container child) {
                refreshAllComponents(child);
            }
        }
    }
}
