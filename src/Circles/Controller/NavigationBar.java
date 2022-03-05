package Circles.Controller;

import Circles.Model.Circle;
import Circles.Model.DatabaseConn;
import Circles.Model.User;

import javax.swing.*;

/**
 * Supplies possibility to move between panels.
 * @author Robert Nilsson
 * @version 2022-03-02
 */
public class NavigationBar extends JPanel{

    /**
     *
     * @param frame the applications main JFrame
     * @param user logged in user
     * @param hide contextual button visibility.
     *             1. Landingpage
     *             2. BrowseCirclesPanel
     *             3. MyCirclesPanel
     *             4. Settings
     *             5. CircleDetailsPanel with join button
     *             6. CircleDetailsPanel with leave button
     * @param c used in CircleDetailsPanel
     */
    public NavigationBar(MainFrame frame, User user, int hide, Circle c){

        JButton myCircles = new JButton("My Circles");
        myCircles.addActionListener(event -> {
            Thread my = new Thread (() -> {
                frame.navigateTo(m -> new MyCirclesPanel(m, user));
            });
            frame.navigateTo(m -> new LoadingScreenPanel(user));
            my.start();
        });
        JButton browseCircles = new JButton("Browse Circles");
        browseCircles.addActionListener(event -> {
            Thread browse = new Thread (() -> {
                frame.navigateTo(m -> new BrowseCirclesPanel(m, user));
            });
            frame.navigateTo(m -> new LoadingScreenPanel(user));
            browse.start();
        });
        JButton createCircle = new JButton("Create new Circle");
        createCircle.addActionListener(event -> frame.navigateTo(m -> new AddNewCirclePanel(m, user)));
        JButton settings = new JButton("Settings");
        settings.addActionListener(event -> frame.navigateTo(m -> new SettingsPanel(m, user)));
        JButton logOut = new JButton("Logout");
        logOut.addActionListener(event -> frame.navigateTo(LoginPanel:: new));
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
            //CircleDetailsPanel with leave button
            case 6: add(myCircles);
                add(browseCircles);
                add(leave);
                add(settings);
                add(logOut);
                break;
        }
    }
}
