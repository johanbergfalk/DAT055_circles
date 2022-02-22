import javax.swing.*;


public class NavigationBar extends JPanel{


    public NavigationBar(MainFrame frame, User user){

        JButton myCircles = new JButton("My Circles");
        myCircles.addActionListener(event -> frame.navigateTo(m -> new MyCirclesPanel(m, user)));
        JButton browseCircles = new JButton("Browse Circles");
        browseCircles.addActionListener(event -> frame.navigateTo(m -> new BrowseCirclesPanel(m, user)));
        JButton settings = new JButton("Settings");
        JButton logOut = new JButton("Logout");
        logOut.addActionListener(event -> frame.navigateTo(Loginpanel :: new));
        add(myCircles);
        add(browseCircles);
        add(settings);
        add(logOut);

    }

}
