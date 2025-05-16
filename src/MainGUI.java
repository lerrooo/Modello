import javax.swing.*;

public class MainGUI {
    private JPanel MainGUI;
    public JFrame frame;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainGUI");
        frame.setContentPane(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public MainGUI(JFrame frameChiamante) {
        frame = new JFrame("SecondaGUI");
        frame.setContentPane(MainGUI);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
