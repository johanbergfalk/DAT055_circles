import java.awt.*;

public class Tempmain {

     MainFrame m;
     User u;

    public static void main(String[] args) {
        MainFrame m = new MainFrame("Circles");
        User u = new User("hej");
        m.setMinimumSize(new Dimension(800,600));
        m.setLocation(100,100);
        m.setContentPane( new Settingspanel(m, u));
        m.setVisible(true);
    }
}
