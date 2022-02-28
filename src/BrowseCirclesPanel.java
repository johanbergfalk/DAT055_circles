import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class BrowseCirclesPanel extends JPanel {

    private LinkedList<Circle> circles = new LinkedList<Circle>();

    public BrowseCirclesPanel(MainFrame frame, User user){

        //TODO - använd nedan funktion när cirklar finns i databasen
        //LinkedList<Circle> circles = DatabaseConn.getAllCircles();

        //TODO - enbart för test, ta bort när cirklar finns i databasen
        try {
            String start = "2022-03-01";
            String end = "2022-03-06";
            String start2 = "2022-03-01";
            String end2 = "2022-03-30";
            String start3 = "2022-04-15";
            String end3 = "2022-05-01";
            String start4 = "2022-06-10";
            String end4 = "2022-06-30";
            String start5 = "2022-06-12";
            String end5 = "2022-07-25";


            java.util.Date startTime = new SimpleDateFormat("yyyy-MM-dd").parse(start);
            java.util.Date endTime = new SimpleDateFormat("yyyy-MM-dd").parse(end);
            java.util.Date startTime2 = new SimpleDateFormat("yyyy-MM-dd").parse(start2);
            java.util.Date endTime2 = new SimpleDateFormat("yyyy-MM-dd").parse(end2);
            java.util.Date startTime3 = new SimpleDateFormat("yyyy-MM-dd").parse(start3);
            java.util.Date endTime3 = new SimpleDateFormat("yyyy-MM-dd").parse(end3);
            java.util.Date startTime4 = new SimpleDateFormat("yyyy-MM-dd").parse(start4);
            java.util.Date endTime4 = new SimpleDateFormat("yyyy-MM-dd").parse(end4);
            java.util.Date startTime5 = new SimpleDateFormat("yyyy-MM-dd").parse(start5);
            java.util.Date endTime5 = new SimpleDateFormat("yyyy-MM-dd").parse(end5);


            //TODO - ta bort dessa när cirklar finns på en user
            circles.add(new Circle("The most captivating Dramas of the 60´s!", "Robert", "A 5 movie best-of-drama from the 60´s", startTime, endTime));
            circles.add(new Circle("Who the hell needs Valentines day..", "Oscar", "Pick me ups for those not ready for a relationship", startTime2, endTime2));
            circles.add(new Circle("Fly me to the moon", "Johan", "Ultra-super-best dogfighting movies ever!", startTime3, endTime3));
            circles.add(new Circle("The most captivating Dramas of the 60´s!", "Robert", "A 5 movie best-of-drama from the 60´s", startTime4, endTime4));
            circles.add(new Circle("You think you are brave?", "Filip", "Ok... sure, you will not scream like a little girl", startTime5, endTime5));

        }catch(ParseException e){
            //Nothing to see here
        }

        setLayout(new BorderLayout());
        setBackground(user.getBackgroundColor());
        Color textColor = user.getForegroundColor();
        add(new NavigationBar(frame, user, 2), BorderLayout.NORTH);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5), "Browse all Circles");
        titledBorder.setTitleColor(textColor);
        setBorder(titledBorder);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        //Get all circles from the database
        circles(content, frame, user);

        //If many circles, makes it possible to scroll through all
        JScrollPane scrollPane = new JScrollPane(content ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

    }

//----Methods-----------------------------------------------------------------------

    //Creates the frame for each circle
    private void circles(JPanel c, MainFrame frame, User u){
        for(Circle i : circles) {
            c.add(new CircleCard(frame, u,  i));
        }
    }
}

