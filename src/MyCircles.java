import java.util.Date;
import java.util.LinkedList;

/**
 * Creates a LinkedList with all circles that belong to a user
 */

public class MyCircles {

    private final LinkedList<Circle> userCircles = new LinkedList<Circle>();

    /**
     * Creates a LinkedList with all circles
     * @param u String Username
     */
    public MyCircles(String u){

        //TODO metod som hämtar alla cirklar tillhörande username från DatabaseConn
        userCircles.add(new Circle("Roberts Drama", "Robert", "Drama from the 60´s",  new Date(2022, 02, 02), new Date(2022,03,02) ));
        userCircles.add(new Circle("Oscars Romance", "Oscar", "Best of 2010",  new Date(2022, 03, 05), new Date(2022,03,30) ));

    }

//----Getters and setters----------------------------------------------

    public LinkedList<Circle> getUserCircles() {
        return userCircles;
    }
}
