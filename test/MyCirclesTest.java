import Circles.Model.Circle;
import Circles.Model.MyCircles;
import Circles.Model.User;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Internal tests of class MyCircles
 * @author Robert Nilsson
 * @version 2022-03-03
 */
class MyCirclesTest {

    // Test of method getUserCircles ------------------
    @Test
    public void noCirclesOnUser(){
        MyCircles circles = new MyCircles(new User("NonExistingUserUsedForTesting"));
        LinkedList<Circle> emptyList = new LinkedList<>();
        LinkedList<Circle> userList = circles.getUserCircles();

        assertEquals(emptyList, userList);
    }
    @Test
    public void twoCirclesOnUser(){
        //Uses login: TestingUser, Pass: q
        //Circles.Controller.Model.User is member in 2 circles

        MyCircles circles = new MyCircles(new User("TestingUser"));
        LinkedList<Circle> shouldBeTwoCircles = circles.getUserCircles();
        LinkedList<Circle>  twoCircles = new LinkedList<>();
        twoCircles.add(new Circle());
        twoCircles.add(new Circle());

        assertEquals(shouldBeTwoCircles.size(), twoCircles.size());
    }
    @Test
    public void correctFirstCirle(){
        //Uses login: TestingUser, Pass: q
        //Circles.Controller.Model.User is member in 2 circles

        MyCircles circles = new MyCircles(new User("TestingUser"));
        LinkedList<Circle> firstCircle = circles.getUserCircles();
        LinkedList<Circle> secondCircle = circles.getUserCircles();
        Circle first = firstCircle.get(1);
        Circle second = secondCircle.get(1);

        assertEquals(first.getId(), second.getId());
    }

}