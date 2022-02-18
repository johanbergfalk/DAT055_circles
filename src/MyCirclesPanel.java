import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/** Creates a panel with all Circles that a user is a member in
 *
 */

public class MyCirclesPanel extends JPanel {

    private final LinkedList<Circle> userCircles;

    public MyCirclesPanel(MainFrame frame){

        MyCircles t = new MyCircles("user"); //TODO Denna ska köras med inloggad user
        this.userCircles = t.getUserCircles();

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("My Circles"));
        //setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(userCircles.size(), 0, 3,3));
        //content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        //create the frame for each circle
        circles(content, frame);

        JScrollPane scrollPane = new JScrollPane(content ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

    }

//----Methods-----------------------------------------------------------------------

    //Creates the frame for each circle
    private void circles(JPanel c, MainFrame frame){
          for(Circle i : userCircles) {
              CircleCard x = new CircleCard(i, frame);
              c.add(x);
          }
    }

    //Tillfälligt för att testa MyCircles
    public static void main(String[] args) {
        MainFrame frame = new MainFrame("Navigate");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.add(new MyCirclesPanel(frame));

        frame.setVisible(true);
    }
}
