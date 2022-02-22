import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.LinkedList;

public class BrowseCirclesPanel extends JPanel {

    private LinkedList<Circle> circles = new LinkedList<Circle>();

    public BrowseCirclesPanel(MainFrame frame, User user){

        //TODO metod som hämtar alla cirklar från DatabaseConn
        circles.add(new Circle("The most captivating Dramas of the 60´s!", "Robert", "A 5 movie best-of-drama from the 60´s", new Date(2022, 02, 02), new Date(2022, 03, 02)));
        circles.add(new Circle("Who the hell needs Valentines day..", "Oscar", "Pick me ups for those not ready for a relationship", new Date(2022, 03, 05), new Date(2022, 03, 30)));
        circles.add(new Circle("Fly me to the moon", "Johan", "Ultra-super-best dogfighting movies ever!", new Date(2022, 02, 02), new Date(2022, 03, 02)));
        circles.add(new Circle("The most captivating Dramas of the 60´s!", "Robert", "A 5 movie best-of-drama from the 60´s", new Date(2022, 02, 02), new Date(2022, 03, 02)));
        circles.add(new Circle("Who the hell needs Valentines day..", "Oscar", "Pick me ups for those not ready for a relationship", new Date(2022, 03, 05), new Date(2022, 03, 30)));


        setPreferredSize(new Dimension(800,600));
        setLayout(new BorderLayout());
        add(new NavigationBar(frame, user), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
        //content.setLayout(new GridLayout(circles.size(), 0, 3,3));

        //List all circles in the database
        circles(content, frame, user);

        //If many circles, makes it possible to scroll through all
        JScrollPane scrollPane = new JScrollPane(content ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

    }

//----Methods-----------------------------------------------------------------------

    //Creates the frame for each circle
    private void circles(JPanel c, MainFrame frame, User u){
        for(Circle i : circles) {
            CircleCard x = new CircleCard(frame, u,  i);
            c.add(x);
        }
    }
}

