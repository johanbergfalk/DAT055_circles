package Circles.Controller;

import Circles.Model.Circle;
import Circles.Model.DatabaseConn;
import Circles.Model.MovieDates;
import Circles.Model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.LinkedList;

/**
 * Panel displaying all circles available in the database.
 * @author Robert Nilsson
 * @version 2022-03-02
 */
public class BrowseCirclesPanel extends JPanel {

    private LinkedList<Circle> circles = new LinkedList<Circle>();

    /**
     * Creates a panel displaying all circles as circle cards.
     * @param frame the applications main JFrame
     * @param user logged in user
     */
    public BrowseCirclesPanel(MainFrame frame, User user){

        this.circles = DatabaseConn.getAllCircles();

        setLayout(new BorderLayout());
        setBackground(user.getBackgroundColor());
        Color textColor = user.getForegroundColor();
        Circle temp = new Circle();
        add(new NavigationBar(frame, user, 2, temp), BorderLayout.NORTH);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5), "Browse all Circles");
        titledBorder.setTitleColor(textColor);
        setBorder(titledBorder);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(getBackground());

        //Get all circles from the database
        circles(content, frame, user, circles);

        //If many circles, makes it possible to scroll through all
        JScrollPane scrollPane = new JScrollPane(content ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(user.getBackgroundColor());
        add(scrollPane, BorderLayout.CENTER);

    }

//----Methods-----------------------------------------------------------------------

    //List circles in order of Active circles first, then circles which dates have passed
    private void circles(JPanel c, MainFrame frame, User u, LinkedList<Circle> circles){
        JPanel active = new JPanel();
        active.setBackground(u.getBackgroundColor());
        JLabel activeLabel = new JLabel("Active Circles: ");
        activeLabel.setForeground(Color.decode("#C6E2FF"));
        activeLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
        active.add(activeLabel);
        c.add(active);
        for(Circle i : circles){
            MovieDates dates = new MovieDates(i.getStartTime(), i.getStopTime());
            if(dates.getTotalDaysLeft() >= 0){
                int id = i.getId();
                if(!(id == 13 || id == 14)){
                    c.add(new CircleCard(frame, u, i));
                }
            }
        }
        JPanel completed = new JPanel();
        completed.setBackground(u.getBackgroundColor());
        JLabel passed = new JLabel("Passed circles: ");
        passed.setForeground(Color.decode("#C6E2FF"));
        passed.setFont(new Font("Arial Black", Font.BOLD, 24));
        completed.add(passed);
        c.add(completed);
        for(Circle i : circles){
            MovieDates dates =  new MovieDates(i.getStartTime(), i.getStopTime());
            if(dates.getTotalDaysLeft() < 0){
                c.add(new CircleCard(frame, u, i));
            }
        }
    }
}

