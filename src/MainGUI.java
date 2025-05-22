import javax.lang.model.type.ArrayType;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.RandomGenerator;

public class MainGUI {
    private JPanel MainGUIPanel;
    private JButton addTodo1;
    private JPanel JPanelTodo1;
    public JFrame frame;

    static private Utente utenteLoggato = null;

    static ArrayList<JPanel> BachecheJPanel = new ArrayList<JPanel>();
    static ArrayList<JButton> ButtonsList = new ArrayList<JButton>();
    static ArrayList<JLabel> TitoliList = new ArrayList<JLabel>();


    public static void main(String[] args) {
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

    public MainGUI(JFrame frameChiamante, Utente utenteLog) {
        System.out.println("asdasd");
        utenteLoggato = utenteLog;
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

    private static JPanel createPanelWithButton() {

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

                String nomeTemp = JOptionPane.showInputDialog(null, "Inserire nome ToDo", "Crea ToDo", JOptionPane.INFORMATION_MESSAGE);
                String descTemp = JOptionPane.showInputDialog(null, "Inserire descrizione ToDo", "Crea ToDo", JOptionPane.INFORMATION_MESSAGE);

                JButton newButton = new JButton(nomeTemp);
                ButtonsList.add(newButton);

                ToDo tempToDo = new ToDo(nomeTemp, LocalDate.now(), descTemp, utenteLoggato);
                panel.add(newButton, 1);

                resizeLayout();
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
