package gestorefilm;
import gestorefilm.view.GestoreFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestoreFrame::new);
    }
}