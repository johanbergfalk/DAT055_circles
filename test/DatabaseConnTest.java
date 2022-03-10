import Circles.Model.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Testing for DatabaseConn class
 * @author Filip Svensson
 * @version 2022-03-03
 */
class DatabaseConnTest {

    @Test
    void registerUser() throws SQLException {
        byte[] hash = {1};
        byte[] salt = {1};
        Boolean result = DatabaseConn.registerUser("Test_User_To_Be_Removed", hash, salt);
        assertTrue(result);
        User user = new User("Test_User_To_Be_Removed");
        DatabaseConn.removeTestUser(user);

    }

    @Test
    void updateUserpass() {
        char[] password = {1, 2, 3};
        byte[] salt = Passwords.getNextSalt();
        byte[] hash = Passwords.hash(password, salt);
        boolean result = DatabaseConn.updateUserpass("Filip", hash, salt);
        assertTrue(result);
        result = DatabaseConn.updateUserpass("THIS_USER_DOESNT_EXIST", hash, salt);
        assertFalse(result);
    }

    @Test
    void getHash() {
        byte[] hash = DatabaseConn.getHash("Filip");
        byte[] samehash = DatabaseConn.getHash("Filip");
        assertArrayEquals(hash, samehash);
        byte[] nohash = DatabaseConn.getHash("THIS_USER_DOESNT_EXIST");
        assertNull(nohash);
    }

    @Test
    void getSalt() {
        byte[] salt = DatabaseConn.getSalt("Filip");
        byte[] samesalt = DatabaseConn.getSalt("Filip");
        assertArrayEquals(salt, samesalt);
        byte[] nosalt = DatabaseConn.getSalt("THIS_USER_DOESNT_EXIST");
        assertNull(nosalt);
    }

    @Test
    void getUserMode() {
        Boolean result = DatabaseConn.getUserMode("Filip");
        assertTrue(result);
        result = DatabaseConn.getUserMode("THIS_USER_DOESNT_EXIST");
        assertFalse(result);
    }

    @Test
    void setUserMode() {
        Boolean result = DatabaseConn.setUserMode("Filip", true);
        assertTrue(result);

        result = DatabaseConn.setUserMode("THIS_USER_DOESNT_EXIST", true);
        assertFalse(result);
    }

    @Test
    void GradeCommentMethods() {
        User user = new User("TestingUser");
        LinkedList<Circle> c = DatabaseConn.getUserCircles("TestingUser");
        Circle circle = c.getFirst();
        LinkedList<Movie> m = DatabaseConn.getCircleMovies(circle);
        Movie movie = m.getFirst();

        GradeComment result = DatabaseConn.avgMovieScore(circle, movie);
        assertNotNull(result);

        result = DatabaseConn.avgCircleScore(circle);
        assertNotNull(result);

        LinkedList<GradeComment> result2 = DatabaseConn.getAllMovieRatings(circle, movie);
        assertNotNull(result2);

        result = DatabaseConn.getUserRating(circle, user, movie);
        assertNotNull(result);
        circle = c.getLast();
        result = DatabaseConn.getUserRating(circle, user, movie);
        assertNull(result);
    }

    @Test
    void addMovieReview_isReviewed() throws SQLException {
        User user = new User("TestingUser");
        LinkedList<Circle> c = DatabaseConn.getUserCircles("TestingUser");
        Circle circle = c.getFirst();
        LinkedList<Movie> m = DatabaseConn.getCircleMovies(circle);
        Movie movie = m.getLast();

        Boolean result = DatabaseConn.addMovieReview(user, circle, movie, 5, "TEST");
        assertTrue(result);

        result = DatabaseConn.isReviewed(user, circle, movie);
        assertTrue(result);

        result = DatabaseConn.addMovieReview(user, circle, movie, 5, "TEST");
        assertFalse(result);

        DatabaseConn.removeTestReview(user, circle, movie);

    }


    @Test
    void CirclesAndMoviesTest() throws SQLException {

        DatabaseConn.removeTestCircle("test_circle_to_be_removed");


        LinkedList<Circle> resultCircleList = DatabaseConn.getUserCircles("Filip");
        assertNotNull(resultCircleList);

        resultCircleList = DatabaseConn.getAllCircles();
        assertNotNull(resultCircleList);

        Circle circle = resultCircleList.getFirst();
        LinkedList<Movie> resultMovieList = DatabaseConn.getCircleMovies(circle);
        assertNotNull(resultMovieList);

        Circle tempCircle = new Circle("test_circle_to_be_removed", "blah", "blah", new Date(), new Date());
        LinkedList<String> tempMembers = new LinkedList<>();
        tempMembers.add("Filip");
        tempCircle.setMembers(tempMembers);


        int resultInt;
        resultInt = DatabaseConn.addCircle(tempCircle);
        assertEquals(1, resultInt);

        resultInt = DatabaseConn.getCircleID(tempCircle);
        assertNotEquals(-1, resultInt);
        tempCircle.setId(resultInt);


        Movie tempMovie = new Movie("Shutter Island");

        Boolean resultBool = DatabaseConn.addMovie(tempMovie);
        assertTrue(resultBool);

        resultBool = DatabaseConn.addMovieCircle(tempCircle, tempMovie);
        assertTrue(resultBool);

        Movie tempMovie2 = DatabaseConn.getFirstMovie(tempCircle);
        assertEquals(tempMovie.getId(), tempMovie2.getId());

        tempMovie2 = DatabaseConn.getMovie(tempMovie2.getId());
        assertEquals(tempMovie.getId(), tempMovie2.getId());

        User tempUser = new User("TempToBeDeleted");
        byte[] hash = {1};
        byte[] salt = {1};
        DatabaseConn.registerUser("TempToBeDeleted", hash, salt);
        resultBool = DatabaseConn.joinCircle(tempCircle, tempUser);
        assertTrue(resultBool);

        resultBool = DatabaseConn.leaveCircle(tempCircle, tempUser);
        assertTrue(resultBool);


        DatabaseConn.removeTestUser(tempUser);
        DatabaseConn.removeTestMovie(tempMovie);
        DatabaseConn.removeTestCircle("test_circle_to_be_removed");

    }
}

