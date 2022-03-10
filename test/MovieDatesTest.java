import Circles.Model.MovieDates;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Internal tests of class MovieDates
 * @author Robert Nilsson
 * @version 2022-03-03
 */
class MovieDatesTest {

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