import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.LinkedList;

/** Creates a panel with all Circles that a user is a member in
 *
 */

public class MyCirclesPanel extends JPanel {

    private final LinkedList<Circle> userCircles;

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

    //Creates the frame for each circle
    private void circles(JPanel c, MainFrame frame, User u){
        JPanel active = new JPanel();
        active.setBackground(u.getBackgroundColor());
        JLabel activeLabel = new JLabel("My active Circles: ");
        activeLabel.setForeground(Color.decode("#C6E2FF"));
        activeLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
        active.add(activeLabel);
        c.add(active);
        for(Circle i : userCircles){
            MovieDates dates = new MovieDates(i.getStartTime(), i.getStopTime());
            if(dates.getTotalDaysLeft() >= 0){
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
        for(Circle i : userCircles){
            MovieDates dates =  new MovieDates(i.getStartTime(), i.getStopTime());
            if(dates.getTotalDaysLeft() < 0){
                c.add(new CircleCard(frame, u, i));
            }
        }
    }
}
