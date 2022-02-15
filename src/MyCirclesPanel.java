import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/** Creates a panel with all Circles that a user is a member in
 *
 */

public class MyCirclesPanel extends JPanel {

    private LinkedList<Circle> userCircles = new LinkedList<Circle>();

    public MyCirclesPanel(MainFrame frame, LinkedList<Circle> circles){//, LinkedList<Circle> circles){
        this.userCircles = circles;

        setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        //create the frame for each circle
        circleFrame(content);

        JScrollPane scrollPane = new JScrollPane(content ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane);

    }

//----Methods-----------------------------------------------------------------------

    //Creates the frame for each circle
    private void circleFrame(JPanel c){
          for(Circle i : userCircles) {
              JPanel circleMainSlot = new JPanel(); //Main window for the circle
              circleMainSlot.setLayout(new GridLayout(1,2, 3,3)); //Main split in 2

              JPanel datesAndName = new JPanel();

              DateFormat df = new SimpleDateFormat("yy-MM-dd");
              String dateFrom = df.format(i.get_starttime());
              String dateTo = df.format(i.get_stoptime());

              JTextArea labelDatesAndName = new JTextArea("From: " + dateFrom + "\n\n" + i.getName() + "\n\n" + "To: " + dateTo);
              labelDatesAndName.setOpaque(true); //Todo funkar inte..
              datesAndName.add(labelDatesAndName);

              JPanel descriptionAndButton = new JPanel();
              JLabel desc = new JLabel(i.getDescription());
              descriptionAndButton.add(desc);

              JButton open = new JButton("Open");
              descriptionAndButton.add(open);


              circleMainSlot.setPreferredSize(new Dimension(100, 100));
              circleMainSlot.add(datesAndName);
              circleMainSlot.add(descriptionAndButton);
              c.add(circleMainSlot);
          }
    }




    public static void main(String[] args) {
        MainFrame frame = new MainFrame("Navigate");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        new MyCircles(frame);

        frame.setVisible(true);
    }
}
