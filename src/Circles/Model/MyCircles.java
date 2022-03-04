package Circles.Model;

import java.util.LinkedList;

/**
 * Creates a LinkedList with all circles associated with a user.
 * @author Robert Nilsson
 * @version 2022-03-02
 */

public class MyCircles {

    private LinkedList<Circle> userCircles = new LinkedList<>();

    /**
     * Creates a LinkedList with all circles a user is a member in.
     * @param user the user to get all Circles from
     */
    public MyCircles(User user) {

        this.userCircles = DatabaseConn.getUserCircles(user.getUsername());

    }

//----Getters and setters----------------------------------------------

    /**
     *
     * @return a LinkedList with all circles a user i member in
     */
    public LinkedList<Circle> getUserCircles() {
        return userCircles;
    }

}