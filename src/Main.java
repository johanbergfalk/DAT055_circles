import Circles.Controller.LoginPanel;
import Circles.Controller.MainFrame;
import Circles.Model.DatabaseConn;
import Circles.Model.Passwords;

import javax.swing.*;
import java.awt.*;

/**
 * Starts application
 * @author Oscar Larsson
 * @version 2022-03-03
 */
public class Main {
    public static void main(String[] args) {
        MainFrame m = new MainFrame("Circles");
        m.setMinimumSize(new Dimension(800,600));
        m.setLocation(100,100);
        m.setIconImage(new ImageIcon("src/framelogo.png").getImage());
        m.setDefaultCloseOperation(m.EXIT_ON_CLOSE);
        m.setContentPane(new LoginPanel(m));
        m.setVisible(true);

    }
}
