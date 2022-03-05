package Circles.Controller;

import Circles.Model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.LinkedList;

/**
 * Class for creating a new panel presenting information about a circle.
 * @author Johan Bergfalk and Robert Nilsson
 * @version 2022-03-02
 */
public class CircleDetailsPanel extends JPanel {

    private final LinkedList<Movie> moviesInCircle;
    private static MainFrame frame;
    private static User user;
    private static Circle c;

    /**
     * Constructor for a new CircleDetailsPanel.
     * @param frame the applications main JFrame
     * @param user the active user
     * @param c the circle to display information about
     */
    public CircleDetailsPanel(MainFrame frame, User user, Circle c) {

        this.frame = frame;
        this.user = user;
        this.c = c;

        setLayout(new BorderLayout());
        setBackground(user.getBackgroundColor());
        Color textColor = user.getForegroundColor();

        MovieDates end = new MovieDates(c.getStartTime(), c.getStopTime());

        //NavBar
        if(checkCreator(c, user)) {
            add(new NavigationBar(frame, user, 1, c), BorderLayout.NORTH);
        } else if((!checkMember(c, user)) && (end.getTotalDaysLeft() < 0)) {
            add(new NavigationBar(frame, user, 1, c), BorderLayout.NORTH);
        } else if(checkMember(c, user)){
            add(new NavigationBar(frame, user, 6, c), BorderLayout.NORTH);
        } else {
            add(new NavigationBar(frame, user, 5, c), BorderLayout.NORTH);
        }

        TitledBorder titledBorder = BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5), c.getName());
        titledBorder.setTitleColor(textColor);
        setBorder(titledBorder);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        moviesInCircle = DatabaseConn.getCircleMovies(c);
        int numberOfMovies = moviesInCircle.size();
        int moviePos = 1;

        if(moviesInCircle != null) {
            for(Movie m : moviesInCircle) {
                MovieDates dates = new MovieDates(c.getStartTime(), c.getStopTime(), moviePos, numberOfMovies);
                content.add(new MovieCard(m, user, c, dates.getMovieDaysLeft()));
                moviePos +=1;
            }
        }
        else {
            content.add(new JLabel("No movies has been added to this circle yet"));
        }

        JScrollPane scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);

    }

    //checks if the active user have created this circle
    private boolean checkCreator(Circle c, User u){
        return c.getCreator().equals(u.getUsername());
    }

    //check if the active user is member of this circle
    private boolean checkMember(Circle c, User u){
        for(String m : c.getMembers()){
            if(m.equals(u.getUsername())){
                return true;
            }
        }
        return false;
    }

    /**
     * Makes a new instance of CircleDetailsPanel with updated information about the circle.
     */
    public static void updateCircleDetail(){
        frame.navigateTo(k -> new CircleDetailsPanel(k, user, c));
    }

}
