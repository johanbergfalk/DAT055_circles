import Circles.Model.Circle;
import Circles.Model.MyCircles;
import Circles.Model.User;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Internal tests of class Circle
 * @author Robert Nilsson
 * @version 2022-03-03
 */
class CircleTest {

    User u = new User("TestingUser");
    MyCircles c = new MyCircles(u);
    LinkedList<Circle> circles = c.getUserCircles();
    Circle circle = circles.get(1);



    @Test
    void tesGetID(){

        int result = circle.getId();
        assertEquals(14, result);
    }

    @Test
    void testSetId() {

        circle.setId(3);
        int result = circle.getId();
        assertEquals(3, result);
    }

    @Test
    void testGetName() {

        String result = circle.getName();
        assertEquals("Testing 2", result);
    }

    @Test
    void testSetName() {

        circle.setName("Name changed");
        String result = circle.getName();
        assertEquals("Name changed", result);


    }

    @Test
    void testGetCreator() {

        String result = circle.getCreator();
        assertEquals("TestingUser", result);
    }

    @Test
    void testSetCreator() {

        circle.setCreator("NewGuy");
        assertEquals("NewGuy", circle.getCreator());

    }

    @Test
    void testGetMembers() {

        LinkedList<String> members = circle.getMembers();
        String result = members.get(0);
        assertEquals("TestingUser", result);
    }

    @Test
    void testSetMembers() {

        LinkedList<String> members = new LinkedList<>();
        members.add("First");
        members.add("Second");
        circle.setMembers(members);
        assertEquals(members, circle.getMembers());
    }

    @Test
    void testAddMember() {

        circle.addMember("NewGuy");
        LinkedList<String> m = circle.getMembers();
        String result = m.get(1);
        assertEquals("NewGuy", result);

    }

    @Test
    void testGetDescription() {

        String result = circle.getDescription();
        assertEquals("Used for testing purpose", result);
    }

    @Test
    void testSetDescription() {

        circle.setDescription("En testcirkel");
        String result = circle.getDescription();
        assertEquals("En testcirkel", result);
    }

    @Test
    void testGetStartTime() {

        String result = circle.getStartTime().toString();
        assertEquals("Sun Apr 10 00:00:00 CEST 2022",result);

    }

    @Test
    void setStartTime() {
    }

    @Test
    void testGetStopTime() {
        String result = circle.getStopTime().toString();
        assertEquals("Tue May 03 00:00:00 CEST 2022",result);
    }

    @Test
    void setStopTime() {
    }

    @Test
    void getScore() {

        float result = circle.getScore();
        assertEquals(0.0, result);

    }

    @Test
    void setScore() {

        circle.setScore(3.200000047683716f);
        float result = circle.getScore();
        assertEquals(3.200000047683716, result);

    }
}