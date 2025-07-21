package gui;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;


public class ToDoGUI {
    private JPanel ToDoPanel;
    private JButton modificaButton;
    private JTextArea descrizioneArea;
    private JTextField urlField;
    private JButton coloreButton;
    private JRadioButton completatoRadioButton;
    private JLabel todoLabel;
    private JButton modificaData;
    private JLabel dataLabel;
    private JButton eliminaButton;
    private JButton confermaButton;
    static JFrame frameTodo;
    Color coloreScelto;

//    public static void main(String[] args){
//        JFrame frame = new JFrame("Modifica ToDo");
//        frameTodo = frame;
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 400);
//        frame.setContentPane(new ToDoGUI(null,null, null, 0,0).ToDoPanel);
//        frame.setVisible(true);
//        frame.setResizable(false);
//    }

    public ToDoGUI(JPanel currentPanel, JButton todoBottone, String nomeToDo, String nomeBacheca, Controller controller) throws SQLException {

        ArrayList<String> caratteristiche = new ArrayList<String>();


        caratteristiche = controller.getSingleToDoDB(nomeToDo, nomeBacheca);

//        if(descrizione == null)
//            return;


        todoLabel.setText(nomeToDo);
        descrizioneArea.setText(caratteristiche.getFirst());
        dataLabel.setText(String.valueOf(caratteristiche.get(1)));
        urlField.setText(caratteristiche.get(2));
        completatoRadioButton.setSelected("1".equals(caratteristiche.get(3)));

        coloreScelto = todoBottone.getBackground();

        JFrame frame = new JFrame("Modifica ToDo");
        frameTodo = frame;

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setContentPane(ToDoPanel);
        frame.setVisible(true);
        frame.setResizable(false);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                todoBottone.setText(todoLabel.getText());
//                todoBottone.setForeground(coloreComplementare(coloreScelto));
//                todoBottone.setBackground(coloreScelto);
//                todoBottone.dataDiScadenza = LocalDate.parse(dataLabel.getText());
//                todoBottone.completato = completatoRadioButton.isSelected();

            }
        });

        coloreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coloreScelto = JColorChooser.showDialog(null, "Scegli un colore", Color.WHITE);
            }
        });

        modificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeTemp = JOptionPane.showInputDialog(null, "Inserire nuovo nome ToDo", "Modifica ToDo", JOptionPane.INFORMATION_MESSAGE);
                if(nomeTemp == null) return;
                if(nomeTemp.length() > 20){
                    JOptionPane.showMessageDialog(frameTodo, "Inserisci meno di 20 caratteri", "Errore", JOptionPane.INFORMATION_MESSAGE);

                }
                else
                {
                    todoLabel.setText(nomeTemp);
                    todoLabel.setFont(new Font(null, Font.PLAIN, (int) (36 - (nomeTemp.length()))));
                }

            }
        });
        modificaData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dataStr = JOptionPane.showInputDialog(null, "Inserisci una nuova data di scadenza (YYYY-MM-DD)", "Modifica ToDo", JOptionPane.INFORMATION_MESSAGE);

                try{
                    LocalDate data = LocalDate.parse(dataStr);
                    dataLabel.setText(dataStr);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(frameTodo, "Formato data non valido", "Errore", JOptionPane.INFORMATION_MESSAGE);

                }

            }
        });
        eliminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int risposta = JOptionPane.showConfirmDialog(
                        null,
                        "Sei sicuro di voler procedere?",
                        "Conferma",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE

                );

                if(risposta == JOptionPane.OK_OPTION){
                    //controller.getUtenteLoggato().bacheche.get(indexBacheca).toDoList.remove(indexToDo);
                    currentPanel.remove(todoBottone);
                    currentPanel.revalidate();
                    currentPanel.repaint();
                    frameTodo.dispose();
                    
                }
            }
        });

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.updateToDo(todoLabel.getText(), nomeToDo, descrizioneArea.getText(), dataLabel.getText(), urlField.getText(), coloreScelto, completatoRadioButton.isSelected(), nomeBacheca);
                    todoBottone.setText(todoLabel.getText());
                    todoBottone.setForeground(coloreComplementare(coloreScelto));
                    todoBottone.setBackground(coloreScelto);

                    JOptionPane.showMessageDialog(frame,"Modifiche effettuate con successo");
                    frameTodo.setVisible(false);
                    frameTodo.dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static Color coloreComplementare(Color colore) {

        if(colore == null)
            return Color.WHITE;

        int complementoRosso = 255 - colore.getRed();
        int complementoVerde = 255 - colore.getGreen();
        int complementoBlu = 255 - colore.getBlue();
        return new Color(complementoRosso, complementoVerde, complementoBlu);
    }


}
