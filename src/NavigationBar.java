import javax.swing.*;


public class NavigationBar extends JPanel{


    public NavigationBar(MainFrame frame, User user, int hide){

        JButton myCircles = new JButton("My Circles");
        myCircles.addActionListener(event -> frame.navigateTo(m -> new MyCirclesPanel(m, user)));
        JButton browseCircles = new JButton("Browse Circles");
        browseCircles.addActionListener(event -> frame.navigateTo(m -> new BrowseCirclesPanel(m, user)));
        JButton createCircle = new JButton("Create new Circle");
        //createCircle.addActionListener(event -> frame.navigateTo(m -> new AddNewCirclePanel(m, user)));
        JButton settings = new JButton("Settings");
        settings.addActionListener(event -> frame.navigateTo(m -> new Settingspanel(m, user)));
        JButton logOut = new JButton("Logout");
        logOut.addActionListener(event -> frame.navigateTo(Loginpanel :: new));
        setBackground(user.getBackgroundColor());

        //Only show relevant buttons in each panel
        switch (hide){
            //Landingpage, CircleDetailsPanel
            case 1: add(myCircles);
                    add(browseCircles);
                    add(settings);
                    add(logOut);
                    break;
            //BrowseCirclesPanel
            case 2: add(myCircles);
                    add(createCircle);
                    add(settings);
                    add(logOut);
                    break;
            //MyCirclesPanel
            case 3: add(browseCircles);
                    add(settings);
                    add(logOut);
                    break;
            //Settings
            case 4: add(myCircles);
                    add(browseCircles);
                    add(logOut);
                    break;
        }
    }
}
