import Circles.Controller.Loginpanel;
import Circles.Controller.MainFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MainFrame m = new MainFrame("Circles");
        m.setMinimumSize(new Dimension(800,600));
        m.setLocation(100,100);
        m.setIconImage(new ImageIcon("src/framelogo.png").getImage());
        m.setDefaultCloseOperation(m.EXIT_ON_CLOSE);
        m.setContentPane(new Loginpanel(m));
        m.setVisible(true);
    }
}
