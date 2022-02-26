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

        //this.userCircles = DatabaseConn.getUserCircles(user.getUsername());

        //TODO - Ta bort detta! Test av MovieDates
        try {
            String start = "2022-03-01";
            String end = "2022-03-06";
            String start2 = "2022-03-01";
            String end2 = "2022-03-30";

            java.util.Date startTime = new SimpleDateFormat("yyyy-MM-dd").parse(start);
            java.util.Date endTime = new SimpleDateFormat("yyyy-MM-dd").parse(end);
            java.util.Date startTime2 = new SimpleDateFormat("yyyy-MM-dd").parse(start2);
            java.util.Date endTime2 = new SimpleDateFormat("yyyy-MM-dd").parse(end2);


        //TODO - ta bort dessa när cirklar finns på en user
        userCircles.add(new Circle("The most captivating Dramas of the 60´s!", "Robert", "A 5 movie best-of-drama from the 60´s", startTime, endTime));
        userCircles.add(new Circle("Who the hell needs Valentines day..", "Oscar", "Pick me ups for those not ready for a relationship", startTime2, endTime2));
        }catch(ParseException e){
            //Nothing to see here
        }
    }

//----Getters and setters----------------------------------------------

    public LinkedList<Circle> getUserCircles() {
        return userCircles;
    }

}