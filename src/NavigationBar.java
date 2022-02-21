import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class NavigationBar extends JPanel{


    public NavigationBar(MainFrame frame){



        JButton myCircles = new JButton("My Circles");
        myCircles.addActionListener(event -> frame.navigateTo(MyCirclesPanel :: new));
        JButton browseCircles = new JButton("Browse Circles");
        browseCircles.addActionListener(event -> frame.navigateTo(BrowseCirclesPanel :: new));
        JButton settings = new JButton("Settings");
        JButton logOut = new JButton("Logout");
        logOut.addActionListener(event -> frame.navigateTo(Loginpanel :: new));
        add(myCircles);
        add(browseCircles);
        add(settings);
        add(logOut);

    }

}
