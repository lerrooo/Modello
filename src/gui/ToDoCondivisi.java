package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Classe che si occupa della gestione della GUI dei ToDo condivisi
 **/
public class ToDoCondivisi {

    static JFrame frameTodo;
    private JPanel mainJPanel;
    private JTable table;
    private JScrollPane scrollPane;

    /**
     * @param controller permette di prendere tutti i ToDo dal DB ed elencarli
     * Ogni condivisione è cliccabile ed eliminabile
     **/
    public ToDoCondivisi(Controller controller) throws SQLException {

        ArrayList<ArrayList<String>> dati = controller.getCondivisioni();
        frameTodo = new JFrame("ToDo condivisi");
        frameTodo.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frameTodo.setSize(400, 400);
        frameTodo.setResizable(false);

        mainJPanel = new JPanel(new BorderLayout());

        // Crea il modello della tabella
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Titolo ToDo");
        model.addColumn("Bacheca");
        model.addColumn("Destinatario");

        for (int i = 0; i < dati.get(0).size(); i++) {
            String titolo = dati.get(0).get(i);
            String bacheca = dati.get(1).get(i);
            String destinatario = dati.get(2).get(i);
            model.addRow(new Object[]{titolo, bacheca, destinatario});
        }


        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Listener per click sulla tabella
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int riga = table.getSelectedRow();
                if (riga != -1) {
                    String nomeToDo = table.getValueAt(riga, 0).toString();
                    String nomeBacheca = table.getValueAt(riga, 1).toString();
                    String destinatario = table.getValueAt(riga, 2).toString();

                    String[] options = {"Sì", "No"};
                    int scelta = JOptionPane.showOptionDialog(
                            frameTodo,
                            "Vuoi rimuovere la condivisione del ToDo \"" + nomeToDo + "\"?",
                            "Conferma rimozione",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[1]  // default "No"
                    );

                    if (scelta == JOptionPane.YES_OPTION) {
                        try {
                            controller.removeCondivisione(nomeBacheca, nomeToDo, destinatario);
                            JOptionPane.showMessageDialog(frameTodo, "Condivisione rimossa.");

                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            model.removeRow(riga);

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(frameTodo, "Errore durante la rimozione:\n" + ex.getMessage());
                        }
                    }
                }
            }
        });

        scrollPane = new JScrollPane(table);
        mainJPanel.add(scrollPane, BorderLayout.CENTER);

        frameTodo.setContentPane(mainJPanel);
        frameTodo.setVisible(true);
    }

}
