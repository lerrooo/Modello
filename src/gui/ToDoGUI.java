package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ToDoGUI {
    private JPanel toDoPanel;
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
    private static final String TITOLO = "Modifica ToDo";

    public ToDoGUI(JPanel currentPanel, JButton todoBottone, String nomeToDo, String nomeBacheca, Controller controller, boolean isYours) {
        
        ArrayList<String> caratteristiche = controller.getSingleToDo(nomeToDo, nomeBacheca);

        todoLabel.setText(nomeToDo);
        todoLabel.setFont(new Font(null, Font.PLAIN, (int) (36 - (nomeToDo.length() * 0.8))));

        descrizioneArea.setText(caratteristiche.getFirst());
        dataLabel.setText(String.valueOf(caratteristiche.get(1)));
        urlField.setText(caratteristiche.get(2));
        completatoRadioButton.setSelected("true".equals(caratteristiche.get(3)));

        coloreScelto = todoBottone.getBackground();

        JFrame frame = new JFrame(TITOLO);
        frameTodo = frame;

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setContentPane(toDoPanel);
        frame.setVisible(true);
        frame.setResizable(false);

        coloreButton.addActionListener(e -> coloreScelto = JColorChooser.showDialog(null, "Scegli un colore", Color.WHITE));

        modificaButton.addActionListener(e -> {
                String nomeTemp = JOptionPane.showInputDialog(null, "Inserire nuovo nome ToDo", TITOLO, JOptionPane.INFORMATION_MESSAGE);
                if(nomeTemp == null) return;
                if(nomeTemp.length() > 20){
                    JOptionPane.showMessageDialog(frameTodo, "Inserisci meno di 20 caratteri", "Errore", JOptionPane.INFORMATION_MESSAGE);

                }
                else
                {
                    todoLabel.setText(nomeTemp);
                    todoLabel.setFont(new Font(null, Font.PLAIN, (36 - (nomeTemp.length()))));
                }
        });
        modificaData.addActionListener(e -> {
                String dataStr = JOptionPane.showInputDialog(null, "Inserisci una nuova data di scadenza (YYYY-MM-DD)", TITOLO, JOptionPane.INFORMATION_MESSAGE);

                try{
                    dataLabel.setText(dataStr);
                }catch (Exception _){
                    JOptionPane.showMessageDialog(frameTodo, "Formato data non valido", "Errore", JOptionPane.INFORMATION_MESSAGE);

                }

        });
        eliminaButton.addActionListener(e -> {

                if(isYours){
                    int risposta = JOptionPane.showConfirmDialog(
                            null,
                            "Sei sicuro di voler procedere?",
                            "Conferma",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE

                    );

                    if(risposta == JOptionPane.OK_OPTION){
                        try {
                            controller.removeToDo(nomeBacheca, nomeToDo);
                        } catch (SQLException _) {
                            //Catch block
                        }
                        currentPanel.remove(todoBottone);
                        currentPanel.revalidate();
                        currentPanel.repaint();
                        frameTodo.dispose();

                    }
                }else{
                    int risposta = JOptionPane.showConfirmDialog(
                            null,
                            "Rimuovere la condivisione?",
                            "Conferma",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE

                    );

                    if(risposta == JOptionPane.OK_OPTION){
                        try {
                            controller.removeCondivisione(nomeBacheca, nomeToDo);
                        } catch (SQLException _) {
                            //Catch block
                        }
                        currentPanel.remove(todoBottone);
                        currentPanel.revalidate();
                        currentPanel.repaint();
                        frameTodo.dispose();

                    }
                }



        });

        confermaButton.addActionListener(e -> {
                try {

                    LocalDate today = LocalDate.now();
                    LocalDate labelDate = null;
                    try{
                        labelDate = LocalDate.parse(dataLabel.getText());
                    }catch (Exception _){
                        JOptionPane.showMessageDialog(null, "Errore nell'inserimento, verifica i dati!");
                    }

                     // formato già corretto

                    if(labelDate == null)
                        return;

                    if (!labelDate.isAfter(today)) { // cioè: se è oggi o una data passata
                        coloreScelto = Color.RED;
                    }

                    todoBottone.setName(todoLabel.getText());
                    controller.updateToDo(todoLabel.getText(), nomeToDo, descrizioneArea.getText(), dataLabel.getText(), urlField.getText(),
                            coloreScelto, completatoRadioButton.isSelected(), nomeBacheca);
                    todoBottone.setText(todoLabel.getText());


                    todoBottone.setForeground(coloreComplementare(coloreScelto));
                    todoBottone.setBackground(coloreScelto);

                    JOptionPane.showMessageDialog(frame,"Modifiche effettuate con successo");
                    frameTodo.setVisible(false);
                    frameTodo.dispose();
                } catch (SQLException _) {
                    //Catch block

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
