import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Settingspanel extends JPanel{
    private MainFrame m;
    private User u;

    public Settingspanel(MainFrame m, User u){
        setLayout(new BorderLayout());
        add(new NavigationBar(m, u, 1), BorderLayout.NORTH);
        setBorder(BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5), " "));

        JLabel logedinas = new JLabel();
        logedinas.setText(u.getUsername());
        add(logedinas);
        this.m=m;
        this.u=u;
    }
}
