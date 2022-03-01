public class GradeComment {
    private User user;
    private Movie movie;
    private Circle circle;
    private float avgMovieGrade;
    private float avgCircleGrade;
    private String comment;


    /*
    metoder:
    get-user-comment
    get-all-circle-comments
    get movie avg grade
    get circle avg grade
     */
    public GradeComment(User u, Movie m, Circle c,String comment){
    this.user=u;
    this.movie=m;
    this.circle=c;
    this.comment=comment;
    }
    public String getUserComment(){

        return comment;
    }
    /*
    public Int getUserrating(){

        return DatabaseConn.getUserRating();
    }

     */

}
