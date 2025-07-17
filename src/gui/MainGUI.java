package gui;

import Controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainGUI {
    private JPanel MainGUIPanel;
    private JButton button1;
    private JButton addTodo1;
    private JPanel JPanelTodo1;
    public static JFrame frame;
    private static Controller controller;
    static private String utenteLoggato = null;
    static ArrayList<JPanel> BachecheJPanel = new ArrayList<JPanel>();
    static ArrayList<JButton> ButtonsList = new ArrayList<JButton>();
    static ArrayList<JLabel> TitoliList = new ArrayList<JLabel>();
    static ArrayList<JButton> removeButtons = new ArrayList<JButton>();

    public MainGUI(JFrame frameChiamante, Controller controller) {

        MainGUI.controller = controller;
        utenteLoggato = controller.getUtenteLoggato();

        JFrame frame = new JFrame("Interfaccia principale");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//      frame.setSize(800, 800);
        frame.setLayout(new GridLayout(1, 3));



        JPanel panel1 = createPanelWithButton();
        JPanel panel2 = createPanelWithButton();
        JPanel panel3 = createPanelWithButton();

        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        coloraPanels(BachecheJPanel, TitoliList);

        frame.setVisible(true);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeLayout();
            }
        });
        frame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                resizeLayout();
            }
        });
    }

    private JPanel createPanelWithButton() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Top panel con titolo e pulsanti
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Titolo
        JLabel titolo = new JLabel(controller.getTitoliBacheche().get(0));
        titolo.setHorizontalAlignment(SwingConstants.CENTER);
        titolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        TitoliList.add(titolo);

        // Riga con pulsanti [x] e [+ Cerca]
        JPanel buttonsRow = new JPanel(new BorderLayout());

        JButton removeButton = new JButton("x");
        removeButtons.add(removeButton);

        JButton searchButton = new JButton("+ Cerca");

        // Listener per rimuovere il pannello
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = mainPanel.getParent();
                int indexBacheca = BachecheJPanel.indexOf(mainPanel);
                if (parent != null) {
                    parent.remove(mainPanel);
                    BachecheJPanel.remove(mainPanel);
                    TitoliList.remove(indexBacheca);
                    removeButtons.remove(removeButton);
                    parent.revalidate();
                    parent.repaint();
                }
                resizeLayout();
            }
        });

        // Aggiunta componenti alla riga pulsanti
        buttonsRow.add(searchButton, BorderLayout.WEST);
        buttonsRow.add(removeButton, BorderLayout.EAST);
        buttonsRow.setOpaque(false); // mantiene sfondo trasparente

        // Aggiunta al topPanel
        topPanel.add(titolo);
        topPanel.add(Box.createVerticalStrut(5)); // spazio
        topPanel.add(buttonsRow);

        // Pannello centrale (dove si potrebbero aggiungere ToDo)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Pulsante per aggiungere ToDo
        JButton plusButton = getJButton();

        // Assembla i pannelli
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(plusButton, BorderLayout.SOUTH); // Il bottone va in basso
        mainPanel.add(centerPanel, BorderLayout.CENTER); // futuro spazio per i ToDo

        // Aggiunta del pannello alla lista
        BachecheJPanel.add(mainPanel);

        return mainPanel;
    }

    private static JButton getJButton() {
        JButton plusButton = new JButton("+ Aggiungi ToDo");
        plusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        plusButton.setHorizontalAlignment(SwingConstants.CENTER);

//        plusButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int indexBacheca = BachecheJPanel.indexOf(plusButton.getParent());
//                String nomeTemp = JOptionPane.showInputDialog(null, "Inserire nomeToDo", "Crea ToDo", JOptionPane.INFORMATION_MESSAGE);
//
//                //inserire check per unique al nome di bacheca
//                if(nomeTemp == null || nomeTemp.trim().isEmpty()){
//                    JOptionPane.showMessageDialog(null, "inserimento fallito");
//                return;
//                }
//
//                String descTemp = JOptionPane.showInputDialog(null, "Inserire descrizione ToDo", "Crea ToDo", JOptionPane.INFORMATION_MESSAGE);
//                if(descTemp == null || descTemp.trim().isEmpty()){
//                    JOptionPane.showMessageDialog(null, "inserimento fallito");
//                return;
//                }
//
//                JButton newButton = new JButton(nomeTemp);
//                newButton.setBackground(Color.white);
//                newButton.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        int indexBacheca = BachecheJPanel.indexOf(newButton.getParent());
//                        int indexToDo = utenteLoggato.bacheche.get(indexBacheca).findToDoIndex(newButton.getText());
//                        ToDo tempToDo = utenteLoggato.bacheche.get(indexBacheca).toDoList.get(indexToDo);
//                        ToDoGUI guiToDo = new ToDoGUI(BachecheJPanel.get(indexBacheca), newButton, tempToDo, controller, indexBacheca, indexToDo);
//                        ToDoGUI.frameTodo.setVisible(true);
//                    }
//                });
//
//                ButtonsList.add(newButton);
//
//                ToDo tempToDo = new ToDo(nomeTemp, LocalDate.now(), descTemp, utenteLoggato);
//                panel.add(newButton, 1);
//                indexBacheca = BachecheJPanel.indexOf(newButton.getParent());
//                if(indexBacheca != -1){
//                utenteLoggato.bacheche.get(indexBacheca).toDoList.add(tempToDo);
//                //newButton.setToolTipText(String.valueOf(utenteLoggato.bacheche.get(indexBacheca).toDoList.size()));
//                resizeLayout();
//                }
//                else{
//                    JOptionPane.showMessageDialog(null, "indice bacheca non trovato");
//                    panel.remove(newButton);
//                    ButtonsList.remove(newButton);
//                    panel.revalidate();
//                    panel.repaint();
//                }
//            }
//        });
        return plusButton;
    }


    private static void coloraPanels(ArrayList<JPanel> BachecheList, ArrayList<JLabel> TitoliList){
        for(int i = 0; i < BachecheList.size(); i++){
            //TitoliList.get(i).setText(utenteLoggato.bacheche.get(i).titolo);

            if(i == 0){

                BachecheList.get(i).setBackground(Color.decode("#5FBB97"));

                TitoliList.get(i).setForeground(Color.decode("#ffffff"));
            }

            else if (i == 1){

                BachecheList.get(i).setBackground(Color.decode("#8DDCA4"));
                TitoliList.get(i).setForeground(Color.decode("#72235b"));
            }

            else if (i == 2){
                BachecheList.get(i).setBackground(Color.decode("#63326e"));

                TitoliList.get(i).setForeground(Color.decode("#9ccd91"));
            }

        }

    }

    private static void resizeLayout(){
        for(JLabel titolo : TitoliList){
            titolo.setPreferredSize(new Dimension((int) (BachecheJPanel.getFirst().getWidth() - BachecheJPanel.getFirst().getWidth() * 0.10), 50));
            //titolo.setBorder(new EmptyBorder(0, 200, 0, 0));
        }

        for(JButton bottone : removeButtons) {
            //bottone.setPreferredSize(new Dimension((int) (BachecheJPanel.getFirst().getWidth() - BachecheJPanel.getFirst().getWidth() * 0.10), 50));

        }

        for(JButton bottone : ButtonsList) {
            bottone.setPreferredSize(new Dimension((int) (BachecheJPanel.getFirst().getWidth() - BachecheJPanel.getFirst().getWidth() * 0.10), 50));
        }

        for(JPanel panel : BachecheJPanel){
            panel.revalidate();
            panel.repaint();
        }
    }

    private static void refreshBacheche() throws SQLException {
        controller.getBacheche();
    }
}
