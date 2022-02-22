import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

/** Creates a panel with all Circles that a user is a member in
 *
 */

public class MyCirclesPanel extends JPanel {

    private final LinkedList<Circle> userCircles;

    public MyCirclesPanel(MainFrame frame){

        MyCircles t = new MyCircles("user"); //TODO Denna ska köras med inloggad user
        this.userCircles = t.getUserCircles();

        add(new NavigationBar(frame));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(new Dimension(800,600));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setSize(new Dimension(800,600));

        //List all the users circles
        circles(content, frame);

        //If many circles, makes it possible to scroll through all
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
