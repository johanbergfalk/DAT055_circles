public class GradeComment {
    private User user;
    private Movie movie;
    private Circle circle;

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    private int userRating;
    private float avgMovieGrade;
    private float avgCircleGrade;
    private String comment;


    public GradeComment(){}



    public GradeComment(User u, Movie m, Circle c,String comment){
        this.user=u;
        this.movie=m;
        this.circle=c;
        this.comment=comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public float getAvgMovieGrade() {
        return avgMovieGrade;
    }

    public void setAvgMovieGrade(float avgMovieGrade) {
        this.avgMovieGrade = avgMovieGrade;
    }

    public float getAvgCircleGrade() {
        return avgCircleGrade;
    }

    public void setAvgCircleGrade(float avgCircleGrade) {
        this.avgCircleGrade = avgCircleGrade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
