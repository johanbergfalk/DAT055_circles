import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Creates a LinkedList with all circles that belong to a user
 */

public class MyCircles {

    private LinkedList<Circle> userCircles = new LinkedList<Circle>();

    /**
     * Creates a LinkedList with all circles a user is a member in
     *
     * @param user the user you want all circles from
     */
    public MyCircles(User user) {

        this.userCircles = DatabaseConn.getUserCircles(user.getUsername());

    }

//----Getters and setters----------------------------------------------

    public LinkedList<Circle> getUserCircles() {
        return userCircles;
    }

}