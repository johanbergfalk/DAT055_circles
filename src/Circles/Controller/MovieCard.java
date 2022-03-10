package Circles.Controller;

import Circles.Model.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.util.LinkedList;

/**
 * Class for constructing a movie card panel containing movie information.
 * @author Johan Bergfalk
 * @version 2022-03-02
 */
public class MovieCard extends JPanel {
    private User user;
    private Circle circle;
    private Movie movie;
    private long daysLeft;

    /**
     * Constructor for a MovieCard.
     * @param m movie
     * @param u currently active user
     * @param c current circle
     * @param d days left of circle
     */
    public MovieCard(Movie m, User u, Circle c, long d) {
        this.user = u;
        this.circle = c;
        this.movie = m;
        this.daysLeft = d;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 150));
        setBorder(new LineBorder(u.getForegroundColor()));
        setBackground(u.getCardColor());
        setForeground(u.getForegroundColor());

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(this.getBackground());
        //new thread to allow dynamic loading of API-data
        new Thread(() -> {
            createLeftPanel(leftPanel);
            validate();
        }).start();

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(this.getBackground());

        //Show review panel depending on member/creator or if all ready reviewed
        if(daysLeft <= 0){
            createReviewedPanel(rightPanel);
        }else if(c.getCreator().equals(u.getUsername())){
            if(!isReviewed()){
            createRightPanel(rightPanel);
            } else {
                createSelfReviewedPanel(rightPanel);
            }
        } else if(checkMember(circle, user)){
            if(!isReviewed()){
                createRightPanel(rightPanel);
            } else {
                createSelfReviewedPanel(rightPanel);
            }
        } else if((!checkMember(circle, user)) && (daysLeft >= 0)){
            createEmptyPanel(rightPanel);
        }
        else {
            createReviewedPanel(rightPanel);
        }

        //adds the left and right panel into a splitpane
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        sp.setResizeWeight(0.25);
        sp.setEnabled(false);
        sp.setDividerSize(0);
        sp.setBackground(this.getBackground());


        sp.setLeftComponent(leftPanel);
        sp.setRightComponent(rightPanel);
        add(sp);

    }

    //method for creating left side of the frame
    private void createLeftPanel(JPanel left) {
        left.setPreferredSize(new Dimension(350, 150));
        left.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        left.setLayout(new BorderLayout());
        left.add(getPoster(movie), BorderLayout.WEST);
        JLabel title = new JLabel(movie.getName());
        title.setForeground(this.getForeground());
        left.add(title, BorderLayout.NORTH);

        //text area for the description of a movie
        JTextArea ta = new JTextArea();
        ta.setBackground(this.getBackground());
        ta.setForeground(this.getForeground());
        ta.setSize(new Dimension(180,60));
        ta.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setText(movie.getDescription());
        ta.setBackground(this.getBackground());
        left.add(ta, BorderLayout.PAGE_END);
        JScrollPane sp1 = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp1.setBorder(BorderFactory.createEmptyBorder());
        left.add(sp1);
        if(daysLeft <= 0){
            JLabel closed = new JLabel("Voting closed");
            JLabel releaseRate = new JLabel();
            releaseRate.setForeground(this.getForeground());
            closed.setForeground(Color.RED);
            releaseRate.setText("Release date: " + movie.getYear() + ".         " + closed.getText());
            left.add(releaseRate, BorderLayout.SOUTH);
        }else {
            JLabel releaseRate = new JLabel("Release date: " + movie.getYear() + ".         Rate within: " + daysLeft +" days");
            releaseRate.setForeground(this.getForeground());
            left.add(releaseRate, BorderLayout.SOUTH);
        }

    }

    //Show this panel when it is possible to still review movie (and user is member/creator of the circle)
    private void createRightPanel(JPanel right) {
        //IF CIRCLE ACTIVE
        right.setPreferredSize(new Dimension(250, 150));
        right.setLayout(new BorderLayout());


        JPanel contents = new JPanel();
        contents.setBackground(this.getBackground());
        contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));

        JLabel review = new JLabel("Leave a review");
        review.setAlignmentX(Component.CENTER_ALIGNMENT);
        review.setForeground(this.getForeground());
        contents.add(review);

        JTextArea input = new JTextArea();
        input.setBorder(new LineBorder(this.getForeground()));
        input.setBackground(user.getTextFieldColor());
        input.setForeground(this.getForeground());

        input.setText("Write your review here...");
        input.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(input.getText().trim().equals("Write your review here...")) {
                    input.setText("");
                }
            }
            public void focusLost(FocusEvent e) {
                if(input.getText().trim().equals("")) {
                    input.setText("Write your review here...");
                }
            }
        });

        input.setLineWrap(true);
        input.setWrapStyleWord(true);

        contents.add(new JScrollPane(input));

        JLabel rate = new JLabel("Rate");
        rate.setAlignmentX(Component.CENTER_ALIGNMENT);
        rate.setBackground(this.getBackground());
        rate.setForeground(this.getForeground());
        contents.add(rate);

        JPanel sliderPanel = new JPanel();
        sliderPanel.setBackground(this.getBackground());
        sliderPanel.setForeground(this.getForeground());
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));

        //swing related to slider
        JSlider slider = new JSlider(0,10, 1);
        slider.setValue(0);
        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBackground(this.getBackground());
        slider.setForeground(this.getForeground());
        slider.setOpaque(false);
        sliderPanel.add(slider);

        //adds padding between slider and label
        sliderPanel.add(Box.createRigidArea(new Dimension(5,5)));

        //swing related to label displaying the value of slider
        JLabel sliderValue = new JLabel(String.valueOf(slider.getValue()));
        sliderValue.setBackground(this.getBackground());
        sliderValue.setForeground(this.getForeground());
        slider.addChangeListener(e -> sliderValue.setText(String.valueOf(slider.getValue())));
        sliderPanel.add(sliderValue);
        sliderPanel.add(Box.createRigidArea(new Dimension(10,5)));
        contents.add(sliderPanel);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            if(input.getText().equals("Write your review here...")){
                JOptionPane.showMessageDialog(right, "You can't leave the review-field empty!");
            } else {
                submitReview(user, circle, movie, slider.getValue(), input.getText());
            }
        });
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        contents.add(submit);

        right.add(contents);
    }

    //Show this panel when the user has reviewed the movie
    private void createSelfReviewedPanel(JPanel right){

        GradeComment userGrade = DatabaseConn.getUserRating(circle, user, movie);

        right.setPreferredSize(new Dimension(250, 150));
        right.setLayout(new BorderLayout());

        JTextArea ta = new JTextArea();
        ta.setBackground(this.getBackground());
        ta.setForeground(this.getForeground());
        ta.setSize(new Dimension(180,60));
        ta.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setText("Review: \n" + userGrade.getComment());
        ta.setBackground(this.getBackground());
        right.add(ta, BorderLayout.PAGE_END);
        JScrollPane sp1 = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp1.setBorder(BorderFactory.createEmptyBorder());
        right.add(sp1);
        JLabel rating = new JLabel("Rating: " + userGrade.getUserRating());
        rating.setForeground(this.getForeground());
        right.add(rating, BorderLayout.SOUTH);

    }

    //Show this panel when it is no longer possible to review the movie
    private void createReviewedPanel(JPanel right){

        LinkedList<GradeComment>  userComments = DatabaseConn.getAllMovieRatings(circle, movie);

        right.setPreferredSize(new Dimension(250, 150));
        right.setLayout(new BorderLayout());

        JTextArea ta = new JTextArea();
        ta.setBackground(this.getBackground());
        ta.setForeground(this.getForeground());
        ta.setSize(new Dimension(180,60));
        ta.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        String allReviews = "";
        for(GradeComment g : userComments){
            allReviews += g.getUser() + ": " + g.getComment() + "\n Grade: " + g.getUserRating() + "\n\n";
        }

        ta.setText(allReviews);
        ta.setBackground(this.getBackground());
        right.add(ta, BorderLayout.PAGE_END);
        JScrollPane sp1 = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp1.setBorder(BorderFactory.createEmptyBorder());
        right.add(sp1);
        GradeComment grade = DatabaseConn.avgMovieScore(circle, movie);
        JLabel rating = new JLabel("Average movie rating: " + grade.getAvgMovieGrade());
        rating.setForeground(this.getForeground());
        right.add(rating, BorderLayout.SOUTH);

    }

    //creating an empty panel on the right side
    private void createEmptyPanel(JPanel right){
        right.setPreferredSize(new Dimension(250, 150));
        right.setLayout(new BorderLayout());

        JPanel contents = new JPanel();
        contents.setBackground(this.getBackground());
        contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
    }

    //gets the poster for a Circles.Controller.Model.Movie m and scales it properly
    private JLabel getPoster(Movie m) {
        try {
            URL posterUrl = new URL(m.getPosterURL());
            ImageIcon icon = new ImageIcon(posterUrl);
            Image scaleImage = icon.getImage().getScaledInstance(72, 108,Image.SCALE_DEFAULT);
            return new JLabel(new ImageIcon(scaleImage));

        } catch (Exception e) {
            e.printStackTrace();
            return new JLabel("No image");
        }
    }

    //method that gets review from field and pushes it go the database
    private void submitReview(User u, Circle c, Movie m, int rating, String review) {
        if(DatabaseConn.addMovieReview(u, c, m, rating, review)) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "Voting done!");
            CircleDetailsPanel.updateCircleDetail();
        }
        else {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "Voting failed");
            getToolkit().beep();
        }
    }

    //method that checks if a movie already is reviewed or not
    private boolean isReviewed(){
        return DatabaseConn.isReviewed(user, circle, movie);

    }

    //method that checks if a user already is member of a circle or not
    private boolean checkMember(Circle c, User u){
        for(String m : c.getMembers()){
            if(m.equals(u.getUsername())){
                return true;
            }
        }
        return false;
    }

}


