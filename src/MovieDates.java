import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Date to LocalDate manipulation
 * a set of functions to calculate days between dates *
 */
public class MovieDates {

    private final long totalRunningDays;
    private final long totalDaysLeft;
    private long movieDaysLeft = 1;
    private final LocalDate localStart;
    private final LocalDate localEnd;



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

    //Converter LocalDate -> Date
    private Date convertToDateViaInstant(LocalDate d) {
        return java.util.Date.from(d.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
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

    private int checkPos(int i){
        if(i <= 0){
            return 1;
        }else return i;
    }

    private int checkMovieTotal(int i, int j){
        if(j < i){
            j = i;
            return j;
        }else return j;
    }


//----Getters and Setters-----------------------------
    public long getTotalRunningDays(){
        return this.totalRunningDays;
    }

    public long getTotalDaysLeft(){
        return totalDaysLeft;
    }

    public long getMovieDaysLeft(){
        return movieDaysLeft;
    }

    public LocalDate getLocalStart(){
        return this.localStart;
    }

    public LocalDate getLocalEnd(){
        return this.localEnd;
    }
}
