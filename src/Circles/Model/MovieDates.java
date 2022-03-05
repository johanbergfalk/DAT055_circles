package Circles.Model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Date to LocalDate manipulation.
 * A set of functions to calculate days between dates, and also Date-to-LocalDate conversion.
 * @author Robert Nilsson
 * @version 2022-03-02
 */
public class MovieDates {

    private final long totalRunningDays;
    private final long totalDaysLeft;
    private long movieDaysLeft = 1;
    private final LocalDate localStart;
    private final LocalDate localEnd;

    /**
     * Sets up methods for retrieving:
     * how long a circle is running, in days,
     * how long until a circle is closed, in days
     * how long until a movie in a circle is closed, in days.
     *
     * @param start startdate of circle
     * @param end enddate of circle
     * @param moviePos position of movie in the circle
     * @param totalMovies how many movies are in the circle
     */
    public MovieDates(Date start, Date end, int moviePos, int totalMovies){

        int pos = checkPos(moviePos);
        int m = checkMovieTotal(pos, totalMovies);
        int movies = checkPos(m);
        LocalDate s = convertToLocalDateViaInstant(start);
        LocalDate e = convertToLocalDateViaInstant(end);

        localStart = s;
        localEnd = e;

        totalRunningDays = countTotalDays(s, e);
        totalDaysLeft = countDaysLeft(e);
        movieDaysLeft = movieDaysLeft(s, e, pos, movies);

    }

    /**
     * Sets up methods for retrieving:
     * how long a circle is running, in days,
     * how long until a circle is closed, in days.
     *
     * @param start startdate of Circle
     * @param end enddate of Circle
     */
    public MovieDates(Date start, Date end){
        LocalDate s = convertToLocalDateViaInstant(start);
        LocalDate e = convertToLocalDateViaInstant(end);

        localStart = s;
        localEnd = e;

        totalRunningDays = countTotalDays(s, e);
        totalDaysLeft = countDaysLeft(e);
    }

//----Methods----------------------------------------


    //Converter Date -> LocalDate
    private LocalDate convertToLocalDateViaInstant(Date d) {
        return d.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    //Returns total days between two dates
    private long countTotalDays(LocalDate s, LocalDate e){
        return s.until(e, ChronoUnit.DAYS);
    }

    //Returns days left to ending date
    private long countDaysLeft(LocalDate e){
        LocalDate now = LocalDate.now();
        return countTotalDays(now, e);
    }

    //Returns days left for a specific movie until voting "closes"
    private long movieDaysLeft(LocalDate s, LocalDate e, int p, int t){
        long total = countTotalDays(s, e);
        long each = (total / t);
        long endInDays = (each * p);
        return countDaysLeft(s.plusDays(endInDays));
    }

    //To avoid 0
    private int checkPos(int i){
        if(i <= 0){
            return 1;
        }else return i;
    }

    //To avoid 0
    private int checkMovieTotal(int i, int j){
        if(j < i){
            j = i;
            return j;
        }else return j;
    }


//----Getters and Setters-----------------------------

    /**
     *
     * @return Amount of days the circle is running
     */
    public long getTotalRunningDays(){
        return this.totalRunningDays + 1;
    }

    /**
     *
     * @return Amount of days from now, until the circle closes
     */
    public long getTotalDaysLeft(){
        return totalDaysLeft;
    }

    /**
     *
     * @return Amount of days from now, until the movie closes for review
     */
    public long getMovieDaysLeft(){
        return movieDaysLeft;
    }

    /**
     *
     * @return start date as LocalDate
     */
    public LocalDate getLocalStart(){
        return this.localStart;
    }

    /**
     *
     * @return end date as LocalDate
     */
    public LocalDate getLocalEnd(){
        return this.localEnd;
    }
}
