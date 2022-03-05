package Circles.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class for constructing a new movie object.
 * @author Johan Bergfalk
 * @version 2022-03-04
 */
public class Movie {

    private String name;
    private int id;
    private String description;
    private String year;
    private String posterURL;

    /**
     * Constructor for a movie, sets name, id, description, year, poster based on title.
     * @param title the movie title to find in tmdb database
     */
    public Movie(String title) {

        JSONObject movie = createMovie(title);

        this.name = movie.getString("original_title");
        this.id = movie.getInt("id");
        this.description = movie.getString("overview");
        this.year = movie.getString("release_date");
        Object poster = movie.get("poster_path");
        //the poster is null if a posterURL is not available
        if(poster instanceof String) {
            this.posterURL = "https://image.tmdb.org/t/p/original" + poster;
        }
        //if no posterURL, set default poster
        else {
            this.posterURL = "https://motivatevalmorgan.com/wp-content/uploads/2016/06/default-movie-1-2-204x300.jpg";
        }
    }

    //Empty constructor used internally in Circles.Controller.Model.DatabaseConn
    public Movie(){}

    /**
     * Used when creating a movie from the class constructor.
     * @param title the movie title to find in tmdb database
     * @return a JSON object containing information about the movie
     */
    public static JSONObject createMovie(String title) {

        //the full JSON of movies and extra data
        JSONObject obj = searchTmdb(title);

        //the movies found are stored as arrays under the results key
        JSONArray results = new JSONArray(obj.getJSONArray("results"));

        //if multiple movies are found the first one in the list is returned
        return (JSONObject) results.get(0);

    }

    /**
     * Search for a movie to display its title in the GUI.
     * @param title the movie title to find in tmdb database
     * @return the movie title as a string to display in the GUI
     */
    public static LinkedList<String> searchForMovies(String title) {

        //gets the movie titles
        LinkedList<JSONObject> movies = movieSearch(title);

        LinkedList<String> movieTitles = new LinkedList<>();

        //saves the movie titles into the new linkedlist
        for (JSONObject obj : movies) {
            movieTitles.add(obj.getString("original_title"));
        }

        return movieTitles;
    }

    // Search for a movie in Tmdb database using API
    private static LinkedList<JSONObject> movieSearch(String title) {

        //the full JSON of movies and extra data
        JSONObject obj = searchTmdb(title);

        //the movies found are stored as arrays under the results key
        JSONArray results = new JSONArray(obj.getJSONArray("results"));

        //iterator and linkedlist to store each JSONObject in a linkedlist
        Iterator<Object> resultsIterator = results.iterator();
        LinkedList<JSONObject> resultSet = new LinkedList<>();

        //used to keep track of number of iterations
        int itCount = 0;
        //gets the first five or less results from the results
        while(resultsIterator.hasNext() && itCount < 5) {
            resultSet.add((JSONObject) resultsIterator.next());
            itCount++;
        }

        //return first 5 movies found or the results found fewer than 5
        return resultSet;

    }

    /**
     * Method that returns the result from an API search in the TMDB database.
     * @param title movie title
     * @return JSOBObject containing the all the results from the search
     */
    public static JSONObject searchTmdb(String title) {
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

}
