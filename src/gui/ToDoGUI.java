package gui;

import model.ToDo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

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
    static JFrame frameTodo;
    Color coloreScelto;

    public static void main(String[] args){
        JFrame frame = new JFrame("Modifica ToDo");
        frameTodo = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setContentPane(new ToDoGUI(null, null).ToDoPanel);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public ToDoGUI(JButton bottone, ToDo todoBottone) {
        todoLabel.setText(todoBottone.titolo);
        descrizioneArea.setText(todoBottone.descrizione);
        dataLabel.setText(String.valueOf(todoBottone.dataDiScadenza));
        urlField.setText(ToDo.URL);
        completatoRadioButton.setSelected(todoBottone.completato);

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
                bottone.setText(todoLabel.getText());
                bottone.setForeground(coloreComplementare(coloreScelto));
                bottone.setBackground(coloreScelto);
                todoBottone.titolo = todoLabel.getText();
                todoBottone.descrizione = descrizioneArea.getText();
                todoBottone.dataDiScadenza = LocalDate.parse(dataLabel.getText());
                ToDo.URL = urlField.getText();
                todoBottone.completato = completatoRadioButton.isSelected();

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
