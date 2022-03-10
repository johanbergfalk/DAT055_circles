import Circles.Model.Movie;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Testing for Movie class
 * @author Johan Bergfalk
 * @version 2022-03-03
 */

public class MovieTest {

    Movie m = new Movie("Forrest Gump");

    @Test
    void createMovie() {

        JSONObject movie = Movie.createMovie("Forrest Gump");
        //the found movies title should be forrest gump
        assertEquals("Forrest Gump", movie.getString("original_title"));

    }

    @Test
    void searchForMovies() {
        //a search for "her" should give the following results as strings
        LinkedList<String> movies = new LinkedList<>();
        movies.add("Göta Kanal 2 - kanalkampen");
        movies.add("Göta Kanal 3 - kanalkungens hemlighet");
        movies.add("Göta Kanal eller Vem drog ur proppen?");
        movies.add("Göta Kanal 4 - Vinna eller försvinna");

        assertEquals(movies, Movie.searchForMovies("göta kanal"));

    }

    @Test
    void movieSearch() {
        //a search for "her" should result in a list of these movies
        ArrayList<String> movies = new ArrayList<>();
        movies.add("Göta Kanal 2 - kanalkampen");
        movies.add("Göta Kanal 3 - kanalkungens hemlighet");
        movies.add("Göta Kanal eller Vem drog ur proppen?");
        movies.add("Göta Kanal 4 - Vinna eller försvinna");
        LinkedList<JSONObject> movieObj = new LinkedList<>();
        //create movie objects for each movie
        for (String s : movies) {
            JSONObject obj = Movie.createMovie(s);
            movieObj.add(obj);
        }
        //compare each title with the title of the movies list
        int i = 0;
        for(JSONObject obj : movieObj) {
            assertEquals(obj.getString("original_title"), movies.get(i));
            i++;
        }
    }


    @Test
    void getName() {
        assertEquals("Forrest Gump", m.getName());
    }

    @Test
    void setName() {
        m.setName("test");
        assertEquals("test", m.getName());
    }

    @Test
    void getId() {
        assertEquals(13, m.getId());
    }

    @Test
    void setId() {
        m.setId(1337);
        assertEquals(1337, m.getId());
    }

    @Test
    void getDescription() {
        assertEquals("A man with a low IQ has accomplished great things in his life and been present during significant historic events—in each case, far exceeding what anyone imagined he could do. But despite all he has achieved, his one true love eludes him.", m.getDescription());
    }

    @Test
    void setDescription() {
        m.setDescription("Really good");
        assertEquals("Really good", m.getDescription());
    }

    @Test
    void getYear() {
        assertEquals("1994-07-06", m.getYear());
    }

    @Test
    void setYear() {
        m.setYear("2022-03-03");
        assertEquals("2022-03-03", m.getYear());
    }

    @Test
    void getPosterURL() {
        assertEquals("https://image.tmdb.org/t/p/original/saHP97rTPS5eLmrLQEcANmKrsFl.jpg", m.getPosterURL());
    }

    @Test
    void setPosterURL() {
        m.setPosterURL("https://image.tmdb.org/t/p/original/dfjLE1HjdR9XhEpN04elCGUOJfA.jpg");
        assertEquals("https://image.tmdb.org/t/p/original/dfjLE1HjdR9XhEpN04elCGUOJfA.jpg", m.getPosterURL());
    }
}

