package Circles.Model;

/**
 * Class for handling GradeComments associated with this and other users.
 * @author oscar
 * @version 2022-03-02
 *
 */
public class GradeComment {

    private Movie movie;
    private Circle circle;

    /**
     * Gets a user rating
     * @return userrating
     */
    public int getUserRating() {
        return userRating;
    }
    /**
     * sets a user rating
     * @param userRating that should be set
     */
    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    private int userRating;
    private float avgMovieGrade;
    private float avgCircleGrade;
    private String comment;
    private String user;

    /**
     * Empty constructor Used in databaseconn
     */
    public GradeComment(){}

    /**
     * Gets current user
     * @return user
     */
    public String getUser() {
        return user;
    }
    /**
     * Sets current user
     * @param user that should be set
     */
    public void setUser(String user) {
        this.user = user;
    }
    /**
     * Sets movie
     * @param movie
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    /**
     * Gets current movie
     * @return movie
     */
    public Movie getMovie(){return this.movie;}
    /**
     * Gets circle
     * @return circle
     */
    public Circle getCircle() {
        return circle;
    }
    /**
     * set circle
     * @param circle that should be set
     */
    public void setCircle(Circle circle) {
        this.circle = circle;
    }
    /**
     * get avg movie grade
     * @return avg movie grade
     */
    public float getAvgMovieGrade() {
        return avgMovieGrade;
    }
    /**
     * set avg movie grade
     * @param avgMovieGrade that should be set
     */
    public void setAvgMovieGrade(float avgMovieGrade) {
        this.avgMovieGrade = avgMovieGrade;
    }
    /**
     * get avg circle grade
     * @return avgcirclegrade
     */
    public float getAvgCircleGrade() {
        return avgCircleGrade;
    }
    /**
     * set avg circle grade
     * @param avgCircleGrade that should be set
     */
    public void setAvgCircleGrade(float avgCircleGrade) {
        this.avgCircleGrade = avgCircleGrade;
    }
    /**
     * gets comment
     * @return comment
     */
    public String getComment() {
        return comment;
    }
    /**
     * sets comment
     * @param comment that should be set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
