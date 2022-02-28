import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NavigationBar extends JPanel{


    public NavigationBar(MainFrame frame, User user, int hide, Circle c){

        JButton myCircles = new JButton("My Circles");
        myCircles.addActionListener(event -> frame.navigateTo(m -> new MyCirclesPanel(m, user)));
        JButton browseCircles = new JButton("Browse Circles");
        browseCircles.addActionListener(event -> frame.navigateTo(m -> new BrowseCirclesPanel(m, user)));
        JButton createCircle = new JButton("Create new Circle");
        createCircle.addActionListener(event -> frame.navigateTo(m -> new AddNewCirclePanel(m, user)));
        JButton settings = new JButton("Settings");
        settings.addActionListener(event -> frame.navigateTo(m -> new Settingspanel(m, user)));
        JButton logOut = new JButton("Logout");
        logOut.addActionListener(event -> frame.navigateTo(Loginpanel :: new));
        JButton join = new JButton("Join Circle");
        join.addActionListener(e -> {
            DatabaseConn.joinCircle(c, user);
            frame.navigateTo(m -> new BrowseCirclesPanel(m, user));
        });
        JButton leave = new JButton("Leave Circle");
        leave.addActionListener(e -> {
            DatabaseConn.leaveCircle(c, user);
            frame.navigateTo(m -> new BrowseCirclesPanel(m, user));
        });




        setBackground(user.getBackgroundColor());

        //Only show relevant buttons in each panel
        switch (hide){
            //Landingpage
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
            //CircleDetailsPanel with join button
            case 5: add(myCircles);
                    add(browseCircles);
                    add(join);
                    add(settings);
                    add(logOut);
                break;
            //CirleDetailsPanel with leave button
            case 6: add(myCircles);
                add(browseCircles);
                add(leave);
                add(settings);
                add(logOut);
                break;
        }
    }


}
