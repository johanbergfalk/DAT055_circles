import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

/** Creates a panel with all Circles that a user is a member in
 *
 */

public class MyCirclesPanel extends JPanel {

    private final LinkedList<Circle> userCircles;

    public MyCirclesPanel(MainFrame frame, User user){

        MyCircles c = new MyCircles(user); //TODO Denna ska köras med inloggad user
        this.userCircles = c.getUserCircles();

        setPreferredSize(new Dimension(800,600));
        setLayout(new BorderLayout());
        add(new NavigationBar(frame, user), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));

        //List all the users circles
        circles(content, frame, user);

        //If many circles, makes it possible to scroll through all
        JScrollPane scrollPane = new JScrollPane(content ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

    }

//----Methods-----------------------------------------------------------------------

    //Creates the frame for each circle
    private void circles(JPanel c, MainFrame frame, User u){
          for(Circle i : userCircles) {
              CircleCard x = new CircleCard(frame, u, i);
              c.add(x);
          }
    }

    //Tillfälligt för att testa MyCircles
    public static void main(String[] args) throws IOException {
        MainFrame frame = new MainFrame("Navigate");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        //frame.add(new MyCirclesPanel(frame));
        //frame.add(new BrowseCirclesPanel(frame));

        frame.setVisible(true);
    }
}
