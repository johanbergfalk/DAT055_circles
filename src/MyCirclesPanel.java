import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
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
        add(new NavigationBar(frame, user, 3), BorderLayout.NORTH);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5), "My Circles");
        titledBorder.setTitleColor(textColor);
        setBorder(titledBorder);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        //Get all circles from user
        circles(content, frame, user);

        //If many circles, makes it possible to scroll through all
        JScrollPane scrollPane = new JScrollPane(content ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

    }

//----Methods-----------------------------------------------------------------------

    //Creates the frame for each circle
    private void circles(JPanel c, MainFrame frame, User u){
          for(Circle i : userCircles) {
              c.add(new CircleCard(frame, u, i));
          }
    }
}
