import java.util.Date;
import java.util.LinkedList;

/**
 * Get all circles that a user is a member in
 * and send them to MyCirclesPanel
 *
 */

public class MyCircles {

    private String username;
    private LinkedList<Circle> userCircles = new LinkedList<Circle>();

    /**
     * Creates a LinkedList with all circles
     * @param frame
     */
    public MyCircles(MainFrame frame){
         //TODO konstruktor ska köras med inloggat User objekt

        User tempUser = new User("Robert");
        username = tempUser.getUsername();

        //TODO metod som hämtar alla cirklar tillhörande username
        userCircles.add(new Circle("Roberts Drama", "Robert", "Drama from the 60´s",  new Date(2022, 02, 02), new Date(2022,03,02) ));
        userCircles.add(new Circle("Oscars Romance", "Oscar", "Best of 2010",  new Date(2022, 03, 05), new Date(2022,03,30) ));

        frame.setContentPane(new MyCirclesPanel(frame, userCircles));
    }



}
