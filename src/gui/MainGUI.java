package gui;

import Controller.Controller;
import model.ToDo;
import model.Utente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainGUI {
    private JPanel MainGUIPanel;
    private JButton addTodo1;
    private JPanel JPanelTodo1;
    public static JFrame frame;
    private static Controller controller;
    static private Utente utenteLoggato = null;
    static ArrayList<JPanel> BachecheJPanel = new ArrayList<JPanel>();
    static ArrayList<JButton> ButtonsList = new ArrayList<JButton>();
    static ArrayList<JLabel> TitoliList = new ArrayList<JLabel>();

    public MainGUI(JFrame frameChiamante, Controller controller) {
        MainGUI.controller = controller;
        utenteLoggato = controller.getUtenteLoggato();
        JFrame frame = new JFrame("Aggiungi Bottone sopra il +");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
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

        JPanel panel = new JPanel();
        JLabel titolo = new JLabel("");
        titolo.setHorizontalAlignment(SwingConstants.CENTER);
        TitoliList.add(titolo);
        panel.add(titolo, 0);

        panel.setBorder(new EmptyBorder(0, 50, 0, 50));
        BachecheJPanel.add(panel);

        JButton plusButton = new JButton("+");
        plusButton.setHorizontalAlignment(SwingConstants.CENTER);
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexBacheca = BachecheJPanel.indexOf(plusButton.getParent());
                String nomeTemp = JOptionPane.showInputDialog(null, "Inserire nomeToDo", "Crea ToDo", JOptionPane.INFORMATION_MESSAGE);

                if(nomeTemp == null || nomeTemp.trim().isEmpty() || (utenteLoggato.bacheche.get(indexBacheca).findToDoIndex(nomeTemp) != -1)){
                    JOptionPane.showMessageDialog(null, "inserimento fallito");
                return;
                }

                String descTemp = JOptionPane.showInputDialog(null, "Inserire descrizione ToDo", "Crea ToDo", JOptionPane.INFORMATION_MESSAGE);
                if(descTemp == null || descTemp.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "inserimento fallito");
                return;
                }
                JButton newButton = new JButton(nomeTemp);
                newButton.setBackground(Color.white);
                newButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
//commento
                        int indexBacheca = BachecheJPanel.indexOf(newButton.getParent());
                        int indexToDo = utenteLoggato.bacheche.get(indexBacheca).findToDoIndex(newButton.getText());
                        ToDo tempToDo = utenteLoggato.bacheche.get(indexBacheca).toDoList.get(indexToDo);
                        ToDoGUI guiToDo = new ToDoGUI(BachecheJPanel.get(indexBacheca), newButton, tempToDo, controller, indexBacheca, indexToDo);
                        ToDoGUI.frameTodo.setVisible(true);
                    }
                });

                ButtonsList.add(newButton);

                ToDo tempToDo = new ToDo(nomeTemp, LocalDate.now(), descTemp, utenteLoggato);
                panel.add(newButton, 1);
                indexBacheca = BachecheJPanel.indexOf(newButton.getParent());
                if(indexBacheca != -1){
                utenteLoggato.bacheche.get(indexBacheca).toDoList.add(tempToDo);
                //newButton.setToolTipText(String.valueOf(utenteLoggato.bacheche.get(indexBacheca).toDoList.size()));
                resizeLayout();
                }
                else{
                    JOptionPane.showMessageDialog(null, "indice bacheca non trovato");
                    panel.remove(newButton);
                    ButtonsList.remove(newButton);
                    panel.revalidate();
                    panel.repaint();
                }
            }
        });

        panel.add(plusButton);

        return panel;
    }


    private static void coloraPanels(ArrayList<JPanel> BachecheList, ArrayList<JLabel> TitoliList){
        for(int i = 0; i < BachecheList.size(); i++){
            TitoliList.get(i).setText(utenteLoggato.bacheche.get(i).titolo);

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

        }

        for(JButton bottone : ButtonsList) {
            bottone.setPreferredSize(new Dimension((int) (BachecheJPanel.getFirst().getWidth() - BachecheJPanel.getFirst().getWidth() * 0.10), 50));
        }

        for(JPanel panel : BachecheJPanel){
            panel.revalidate();
            panel.repaint();
        }
    }
}
