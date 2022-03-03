import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.LinkedList;
import org.json.*;

/**
 * Class for constructing a new movie object
 * @author Johan Bergfalk
 * @version 2022-03-02
 */
public class Movie {

    private String name;
    private int id;
    private String description;
    private String year;
    private String posterURL;
    private BufferedImage poster;

    /**
     * Constructor for a movie, sets name, id, description, year, poster based on title
     * @param title the movie title to find in tmdb database
     */
    public Movie(String title) {

        JSONObject movie = createMovie(title);

        this.name = movie.getString("original_title");
        this.id = movie.getInt("id");
        this.description = movie.getString("overview");
        this.year = movie.getString("release_date");
        Object poster = movie.get("poster_path");
        //the poster is null if a posterURL is not present
        if(poster instanceof String) {
            this.posterURL = "https://image.tmdb.org/t/p/original" + poster;
        }
        //if no posterURL, set default poster
        else {
            this.posterURL = "https://motivatevalmorgan.com/wp-content/uploads/2016/06/default-movie-1-2-204x300.jpg";
        }
    }

    //Empty constructor for use within databaseconn
    public Movie(){}

    /**
     * Search for a movie to display its title in the GUI
     * @param title the movie title to find in tmdb database
     * @return the movie title as a string to display in the GUI
     */
    public static LinkedList<String> searchForMovies(String title) {

        LinkedList<JSONObject> movies = movieSearch(title);
        LinkedList<String> movieTitles = new LinkedList<>();

        for (JSONObject obj : movies) {
            movieTitles.add(obj.getString("original_title"));
        }

        return movieTitles;
    }

    /**
     * Used when creating a movie from the class constructor
     * @param title the movie title to find in tmdb database
     * @return a JSON object containing information about the movie
     */
    private static JSONObject createMovie(String title) {

        //the full JSON of movies and extra data
        JSONObject obj = searchTmdb(title);

        //the movies found are stored as arrays under the results key
        JSONArray results = new JSONArray(obj.getJSONArray("results"));

        //if multiple movies are found the first one in the list is returned
        return (JSONObject) results.get(0);

    }

    /**
     * Search for a movie in the tmdb database using API
     * @param title the movie title to find in tmdb database
     * @return a JSON object containing information about the <= 5 movies found in the database
     */
    private static LinkedList<JSONObject> movieSearch(String title) {

        //the full JSON of movies and extra data
        JSONObject obj = searchTmdb(title);

        //the movies found are stored as arrays under the results key
        JSONArray results = new JSONArray(obj.getJSONArray("results"));

        int itCount = 0;
        Iterator<Object> resultsIterator = results.iterator();
        LinkedList<JSONObject> resultSet = new LinkedList<>();

        //gets the first five or less results from the resultset
        while(resultsIterator.hasNext() && itCount < 5) {
            resultSet.add((JSONObject) resultsIterator.next());
            itCount++;
        }

        //return first 5 movies found or the results found fewer than 5
        return resultSet;

    }

    /**
     * Method that returns the result from an API search in the TMDB database
     * @param title movie title
     * @return JSOBObject containing the all the results from the search
     */
    private static JSONObject searchTmdb(String title) {
        try {
            //converts spaces in the title with %20 for url formatting
            title = title.replaceAll("\\s+","%20");

            //API connection, read in the file using StringBuilder
            URL url = new URL("https://api.themoviedb.org/3/search/movie?api_key=9235f4be07271782e1c1021ed0762621&language=en-US&query=" + title + "&page=1&include_adult=false");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = br.readLine()) != null)
                responseStrBuilder.append(inputStr);

            //the full JSON of movies and extra data
            return new JSONObject(responseStrBuilder.toString());

        }

        catch(IOException e) {
            System.out.println(e);
            return new JSONObject("empty");
        }
    }

    //------ GETTERS & SETTERS

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDescription() {return description; }

    public void setDescription(String description) { this.description = description; }

    public String getYear() { return year; }

    public void setYear(String year) { this.year = year; }

    public String getPosterURL() { return posterURL; }

    public void setPosterURL(String posterURL) { this.posterURL = posterURL; }

    public BufferedImage getPoster() { return poster; }

    public void setPoster(BufferedImage poster) { this.poster = poster; }

}
