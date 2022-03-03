import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTest {

//----Login ----------------------------------------------------------------
    @Test
    public void loginFailsWhenNonExistingUser() throws ExecutionException, InterruptedException {
        Login login = new Login(new char[] {0}, "IDONTEXIST!!!");
        Login.Result result = login.validateuser().get();

        assertEquals(Login.Result.NO_SUCH_USER, result);
    }



//----MyCircles --------------------------------------------------------------

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
        //User is member in 2 circles

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
        //User is member in 2 circles

        MyCircles circles = new MyCircles(new User("TestingUser"));
        LinkedList<Circle> firstCircle = circles.getUserCircles();
        LinkedList<Circle> secondCircle = circles.getUserCircles();
        Circle first = firstCircle.get(1);
        Circle second = secondCircle.get(1);

        assertEquals(first.getId(), second.getId());
    }
//----User --------------------------------------------------------------

    //Test of method getForegroundColor -----------------
    @Test
    public void shouldReturnWhiteForeground(){
        User u = new User("TestingUser");
        u.setDarkmode(true);

        Color white = Color.WHITE;
        Color result = u.getForegroundColor();

        assertEquals(white, result);
    }
    @Test
    public void shouldReturnBlackForeground(){
        User u = new User("TestingUser");
        u.setDarkmode(false);

        Color black = Color.BLACK;
        Color result = u.getForegroundColor();

        assertEquals(black, result);
    }
    //Test of method getBackgroundColor ---------------------
    @Test
    public void shouldReturnBlackBackground(){
        User u = new User("TestingUser");
        u.setDarkmode(true);

        Color black = Color.BLACK;
        Color result = u.getBackgroundColor();

        assertEquals(black, result);
    }
    @Test
    public void shouldReturnWhiteBackground(){
        User u = new User("TestingUser");
        u.setDarkmode(false);

        Color white = Color.WHITE;
        Color result = u.getBackgroundColor();

        assertEquals(white, result);
    }
    //Test of method getCardColor ---------------------
    @Test
    public void shouldReturnDarkGrey() {
        User u = new User("TestingUser");
        u.setDarkmode(true);

        Color gray = Color.DARK_GRAY;
        Color result = u.getCardColor();

        assertEquals(gray, result);
    }
    @Test
    public void shouldReturnPanel() {
        User u = new User("TestingUser");
        u.setDarkmode(false);

        Color panel = UIManager.getColor("Panel.background");
        Color result = u.getCardColor();

        assertEquals(panel, result);
    }
    //Test of method getTextFieldColor ----------------------
    @Test
    public void shouldReturnDarkerGray() {
        User u = new User("TestingUser");
        u.setDarkmode(true);

        Color darkerGray = Color.decode("#333333");
        Color result = u.getTextFieldColor();

        assertEquals(darkerGray, result);
    }
    @Test
    public void shouldReturnFieldWhite() {
        User u = new User("TestingUser");
        u.setDarkmode(false);

        Color white = Color.WHITE;
        Color result = u.getTextFieldColor();

        assertEquals(white, result);
    }

//----MovieDates -------------------------------------------------------------

    //Test of method getLocalStart -----------------
    @Test
    public void dateToLocalStart(){
        try {
            String start = "2022-03-01";
            String end = "2022-03-10";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date startDate = formatter.parse(start);
            Date endDate = formatter.parse(end);

            MovieDates test = new MovieDates(startDate, endDate);
            LocalDate s = test.getLocalStart();

            assertEquals(start, s.toString());

        }catch(ParseException e){}
    }
    //Test of method getLocalEnd -----------------
    @Test
    public void dateToLocalEnd(){
        try {
            String start = "2022-03-01";
            String end = "2022-03-10";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date startDate = formatter.parse(start);
            Date endDate = formatter.parse(end);

            MovieDates test = new MovieDates(startDate, endDate);
            LocalDate e = test.getLocalEnd();

            assertEquals(end, e.toString());

        }catch(ParseException e){}
    }
    //Test of method getTotalRunningDays -----------------
    @Test
    public void totalDays(){
        try {
            String start = "2022-03-01";
            String end = "2022-03-10";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date startDate = formatter.parse(start);
            Date endDate = formatter.parse(end);

            MovieDates test = new MovieDates(startDate, endDate);
            long running = test.getTotalRunningDays();

            assertEquals(10, running);

        }catch(ParseException e){}
    }

    //Test of method getTotalDaysLeft -----------------
    @Test
    public void totalDaysLeft(){
        try {
            String start = LocalDate.now().toString();
            String end = (LocalDate.now().plusDays(20).toString());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date startDate = formatter.parse(start);
            Date endDate = formatter.parse(end);

            MovieDates test = new MovieDates(startDate, endDate);
            LocalDate s = test.getLocalStart();
            LocalDate e = test.getLocalEnd();

            long running = test.getTotalDaysLeft();
            //long actual = s.until(e, ChronoUnit.DAYS);

            assertEquals(20, running);

        }catch(ParseException e){}
    }

    //Test of method getMovieDaysLeft -----------------
    @Test
    public void movieDaysLeft(){
        try {
            String start = LocalDate.now().toString();
            String end = (LocalDate.now().plusDays(20).toString());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date startDate = formatter.parse(start);
            Date endDate = formatter.parse(end);

            MovieDates test = new MovieDates(startDate, endDate, 2, 4);
            long running = test.getMovieDaysLeft();

            assertEquals(10, running);

        }catch(ParseException e){}
    }

    //Test internal 0 ------------------------------------
    @Test
    public void shouldBeTwenty(){
        try {
            String start = LocalDate.now().toString();
            String end = (LocalDate.now().plusDays(20).toString());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date startDate = formatter.parse(start);
            Date endDate = formatter.parse(end);

            MovieDates test = new MovieDates(startDate, endDate, 0, 0);
            long running = test.getMovieDaysLeft();

            assertEquals(20, running);

        }catch(ParseException e){}
    }
}
