package Circles.Controller;

import Circles.Model.Circle;
import Circles.Model.MovieDates;
import Circles.Model.MyCircles;
import Circles.Model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.LinkedList;

/** Creates a panel with all Circles that a user is a member or creator in.
 * @author Robert Nilsson
 * @version 2022-03-02
 */

public class MyCirclesPanel extends JPanel {

    private final LinkedList<Circle> userCircles;

    /**
     * Creates a panel displaying all user circles.
     * @param frame the applications main JFrame
     * @param user logged in user
     */
    public MyCirclesPanel(MainFrame frame, User user){

        MyCircles c = new MyCircles(user);
        this.userCircles = c.getUserCircles();

        setLayout(new BorderLayout());
        setBackground(user.getBackgroundColor());
        Color textColor = user.getForegroundColor();
        Circle temp = new Circle();
        add(new NavigationBar(frame, user, 3, temp), BorderLayout.NORTH);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5), "My Circles");
        titledBorder.setTitleColor(textColor);
        setBorder(titledBorder);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(this.getBackground());

        //Get all circles from user
        circles(content, frame, user);

        //If many circles, makes it possible to scroll through all
        JScrollPane scrollPane = new JScrollPane(content ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

    }

//----Methods-----------------------------------------------------------------------

    //Lists all circles the user is member/creator in
    private void circles(JPanel c, MainFrame frame, User u){

        if(userCircles.size() > 0) {
            JPanel active = new JPanel();
            active.setBackground(u.getBackgroundColor());
            JLabel activeLabel = new JLabel("My active Circles: ");
            activeLabel.setForeground(Color.decode("#C6E2FF"));
            activeLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
            active.add(activeLabel);
            c.add(active);

            for (Circle i : userCircles) {
                MovieDates dates = new MovieDates(i.getStartTime(), i.getStopTime());
                if (dates.getTotalDaysLeft() >= 0) {
                    c.add(new CircleCard(frame, u, i));
                }
            }
            JPanel completed = new JPanel();
            completed.setBackground(u.getBackgroundColor());
            JLabel passed = new JLabel("Passed circles: ");
            passed.setForeground(Color.decode("#C6E2FF"));
            passed.setFont(new Font("Arial Black", Font.BOLD, 24));
            completed.add(passed);
            c.add(completed);
            for (Circle i : userCircles) {
                MovieDates dates = new MovieDates(i.getStartTime(), i.getStopTime());
                if (dates.getTotalDaysLeft() < 0) {
                    c.add(new CircleCard(frame, u, i));
                }
            }
        } else {
            JPanel noCirclesPanel = new JPanel();
            noCirclesPanel.setBackground(u.getBackgroundColor());
            noCirclesPanel.setLayout(new GridLayout(2,1,0,0));


            JLabel noFound = new JLabel("404 No Circles found :(");
            noFound.setFont(new Font("Arial Black", Font.BOLD, 42));
            noFound.setForeground(Color.decode("#C6E2FF"));
            noFound.setHorizontalAlignment(SwingConstants.CENTER);

            JTextPane useNavPrompt = new JTextPane();
            useNavPrompt.setText("Oops.. looks like you haven't joined any circles yet!\nUse 'Browse Circles' to find or create on that suits you.");
            useNavPrompt.setFont(new Font("serif", Font.BOLD, 24));
            StyledDocument doc = useNavPrompt.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);
            useNavPrompt.setEditable(false);
            useNavPrompt.setBackground(u.getBackgroundColor());
            useNavPrompt.setForeground(u.getForegroundColor());
            useNavPrompt.setAlignmentX(SwingConstants.CENTER);

            noCirclesPanel.add(noFound);
            noCirclesPanel.add(useNavPrompt);

            JPanel stretcher = new JPanel();
            stretcher.setBackground(u.getBackgroundColor());
            stretcher.setLayout(new GridBagLayout());
            stretcher.add(noCirclesPanel);

            c.add(stretcher);
        }
    }
}
