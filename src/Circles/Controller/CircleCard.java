package Circles.Controller;

import Circles.Model.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;
import java.util.LinkedList;

/**
 * Creates a card with all circle details to be used in
 * BrowseCirclesPanel and MyCirclesPanel.
 * @author Robert Nilsson
 * @version 2022-03-02
 */

public class CircleCard extends JPanel {

    private final Circle circle;

    /**
     *
     * @param frame the applications main JFrame
     * @param user Logged in user
     * @param i Circle to be displayed in card
     */
    public CircleCard(MainFrame frame, User user, Circle i){

        this.circle = i;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(780,150));
        setMaximumSize(new Dimension(780,150));
        setMinimumSize(new Dimension(780,150));
        setBorder(new LineBorder(user.getForegroundColor()));
        setBackground(user.getCardColor());
        setForeground(user.getForegroundColor());

        //Left side of the card
        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(390,150));
        left.setMaximumSize(new Dimension(390,150));
        left.setMinimumSize(new Dimension(390,150));

        createLeft(circle, left);

        //Right side of the card
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(350,150));
        right.setMaximumSize(new Dimension(350,150));
        right.setMinimumSize(new Dimension(350,150));

        new Thread(() -> {
            createRight(circle, right, frame, user);
            validate();
        }).start();

        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        pane.setResizeWeight(0.5);
        pane.setEnabled(false);
        pane.setDividerSize(0);
        pane.add(left);
        pane.add(right);
        pane.setBackground(user.getCardColor());
        add(pane);
    }

//----Methods-----------------------------------------------------------

    //Left side of panel with description, running-dates and score
    private void createLeft(Circle i, JPanel left){
        left.setLayout(new GridLayout(3,0));
        left.setBackground(this.getBackground());
        JPanel title =  new JPanel();
        JLabel name = new JLabel(i.getName(), JLabel.LEFT);
        name.setFont(new Font("Arial", Font.BOLD, 15));
        title.setBackground(this.getBackground());
        name.setForeground(this.getForeground());
        title.add(name);
        left.add(title);

        //Description
        JTextArea description = new JTextArea();
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setText(i.getDescription());
        description.setBackground(this.getBackground());
        description.setForeground(this.getForeground());
        JScrollPane scrollPane = new JScrollPane(description, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        left.add(scrollPane);

        MovieDates days = new MovieDates(i.getStartTime(), i.getStopTime());

        //leftbottom
        JPanel leftBottom = new JPanel();
        leftBottom.setBackground(this.getBackground());
        leftBottom.setLayout(new GridLayout(3,0,0,0));

        if(days.getTotalDaysLeft() < 0){
            JLabel runtime = new JLabel("Circle is closed");
            runtime.setForeground(Color.RED);
            leftBottom.add(runtime);
            LinkedList<Movie> movielist = DatabaseConn.getCircleMovies(i);
            JLabel movies = new JLabel("No. movies: " + movielist.size());
            movies.setForeground(this.getForeground());
            leftBottom.add(movies);
            GradeComment finalGrade = DatabaseConn.avgCircleScore(circle);
            JLabel score = new JLabel("Circle final score: " + finalGrade.getAvgCircleGrade());
            score.setForeground(this.getForeground());
            leftBottom.add(score);
            left.add(leftBottom);
        } else {

            JLabel runtime = new JLabel("Running from: " + days.getLocalStart() + " to " + days.getLocalEnd());
            runtime.setForeground(this.getForeground());
            leftBottom.add(runtime);
            LinkedList<Movie> movielist = DatabaseConn.getCircleMovies(i);
            JLabel movies = new JLabel("No. movies: " + movielist.size());
            movies.setForeground(this.getForeground());
            leftBottom.add(movies);
            JLabel score = new JLabel("Average score: " + i.getScore());
            score.setForeground(this.getForeground());
            leftBottom.add(score);
            left.add(leftBottom);
        }
    }

    //Right side of panel with members, poster, details-button
    private void createRight(Circle i, JPanel right, MainFrame frame, User u){
        right.setLayout(new GridLayout(0,2));
        right.setBackground(this.getBackground());

        Movie m = DatabaseConn.getFirstMovie(i);

        JLabel poster = getPoster(m);
        poster.setForeground(this.getForeground());
        poster.setBackground(this.getBackground());
        right.add(poster);


        JPanel membersAndButton = new JPanel();
        membersAndButton.setLayout(new GridLayout(2,0));
        membersAndButton.setBackground(this.getBackground());
        membersAndButton.setForeground(this.getForeground());

        //Members
        JTextArea members = new JTextArea();
        members.setForeground(this.getForeground());
        members.setBackground(this.getBackground());
        members.setEditable(false);
        members.setLineWrap(true);
        members.setWrapStyleWord(true);
        String mem = "Members: \n";
        for(String p : i.getMembers()){
            mem += p + "\n";
        }
        members.setText(mem);
        JScrollPane scrollPane = new JScrollPane(members, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new LineBorder(u.getForegroundColor()));
        scrollPane.setBackground(this.getBackground());
        scrollPane.setForeground(this.getForeground());
        membersAndButton.add(scrollPane);


        //Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,0));
        buttonPanel.setBackground(this.getBackground());


        JPanel temp = new JPanel();
        temp.setBackground(this.getBackground());
        buttonPanel.add(temp.add(new JLabel("")));
        JPanel createdBy = new JPanel();
        createdBy.setBackground(this.getBackground());
        JLabel creator = new JLabel("Created by: " + i.getCreator());
        creator.setForeground(this.getForeground());
        buttonPanel.add(createdBy.add(creator));
        JButton detailsButton = new JButton("Circle details");
        detailsButton.addActionListener(event -> frame.navigateTo(k -> new CircleDetailsPanel(k, u, circle)));
        buttonPanel.add(detailsButton);
        membersAndButton.add(buttonPanel);
        right.add(membersAndButton);

    }

    //Gives poster to supplied move
    private JLabel getPoster(Movie m) {
        try {
            URL posterUrl = new URL(m.getPosterURL());
            ImageIcon icon = new ImageIcon(posterUrl);
            Image scaleImage = icon.getImage().getScaledInstance(72, 108, Image.SCALE_DEFAULT);
            return new JLabel(new ImageIcon(scaleImage));

        } catch(Exception e)
            {
                return new JLabel("No image");
            }
    }
}
