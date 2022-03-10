import Circles.Model.*;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing for GradeComment class
 * @author Oscar Larsson
 * @version 2022-03-03
 */
class GradeCommentTest {
    User u = new User("TestingUser");
    MyCircles c = new MyCircles(u);
    LinkedList<Circle> circles = c.getUserCircles();
    Circle circle = circles.get(0);
    Movie m = DatabaseConn.getFirstMovie(circle);


    @Test
    void getUserRating() {
        //in databaseconn the setuserrating is used!
        //therefore we call the database to retrive the users rating and compare it towards what is stored in the database.
        GradeComment test = DatabaseConn.getUserRating(circle, u, m);
        assertEquals(5, Objects.requireNonNull(test).getUserRating());
    }

    @Test
    void setUserRating() {
        //TestingUser sets the grade five in a circle to a movie.
        //this is stored in the database and when databaseconn.getuserrating is executed it calls on setuserrating
        GradeComment test = DatabaseConn.getUserRating(circle, u, m);
        assertEquals(5, Objects.requireNonNull(test).getUserRating());
    }
    @Test
    void getUser() {
        //this is stored in the database and when databaseconn.getuserrating is executed it calls on setuser
        // and we should get the user that created the circle
        GradeComment test = DatabaseConn.getUserRating(circle, u, m);
        assertEquals("TestingUser", Objects.requireNonNull(test).getUser());
    }

    @Test
    void setUser() {
        //this is stored in the database and when databaseconn.getuserrating is executed it calls on setuser
        // and we should get the user that created the circle
        GradeComment test = DatabaseConn.getUserRating(circle, u, m);
        assertEquals(Objects.requireNonNull(test).getUser(),"TestingUser");
    }
    @Test
    void setMovie() {
        GradeComment g = new GradeComment();
        g.setMovie(m);
        assertEquals(m,g.getMovie());
    }
    @Test
    void getMovie() {
        GradeComment g = new GradeComment();
        g.setMovie(m);
        assertEquals(g.getMovie(),m);
    }
    @Test
    void getCircle() {
        //this is stored in the database and when databaseconn.getuserrating is executed it calls on setcircle
        // and we should get the circle
        GradeComment test = DatabaseConn.getUserRating(circle, u, m);
        assertEquals(circle, Objects.requireNonNull(test).getCircle());
    }

    @Test
    void setCircle() {
        //this is stored in the database and when databaseconn.getuserrating is executed it calls on setcircle
        GradeComment test = DatabaseConn.getUserRating(circle, u, m);
        assertEquals(Objects.requireNonNull(test).getCircle(),circle);
    }

    @Test
    void getAvgMovieGrade() {
        GradeComment test = DatabaseConn.avgMovieScore(circle,m);
        assertEquals(5, Objects.requireNonNull(test).getAvgMovieGrade());
    }

    @Test
    void setAvgMovieGrade() {
        GradeComment test = DatabaseConn.avgMovieScore(circle,m);
        assertEquals(Objects.requireNonNull(test).getAvgMovieGrade(),5);
    }

    @Test
    void getAvgCircleGrade() {
        GradeComment test = DatabaseConn.avgCircleScore(circle);
        assertEquals(Objects.requireNonNull(test).getAvgCircleGrade(),5);
    }

    @Test
    void setAvgCircleGrade() {
        GradeComment test = DatabaseConn.avgCircleScore(circle);
        assertEquals(5,Objects.requireNonNull(test).getAvgCircleGrade());
    }

    @Test
    void getComment() {
        GradeComment test = DatabaseConn.getUserRating(circle,u,m);
        assertEquals("Test",Objects.requireNonNull(test).getComment());
    }

    @Test
    void setComment() {
        GradeComment test = DatabaseConn.getUserRating(circle,u,m);
        assertEquals(Objects.requireNonNull(test).getComment(),"Test");
    }
}