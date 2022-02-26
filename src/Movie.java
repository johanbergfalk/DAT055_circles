import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import org.json.*;

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

        JSONObject movie = movieSearch(title);

        this.name = movie.getString("original_title");
        this.id = movie.getInt("id");
        this.description = movie.getString("overview");
        this.year = movie.getString("release_date");
        String poster = movie.getString("poster_path");
        this.posterURL = "https://image.tmdb.org/t/p/original" + poster;

        System.out.println(this.name);
        System.out.println(this.id);
        System.out.println(this.year);
        System.out.println(this.posterURL);

    }

    //Tom konstruktor används internt i databaseconn.
    public Movie(){}

    /**
     * Search for a movie to display its title in the GUI
     * @param title the movie title to find in tmdb database
     * @return the movie title as a string to display in the GUI
     */
    public static String searchForMovieToAdd(String title) {

        JSONObject movie = movieSearch(title);
        return movie.getString("original_title");

    }

    /**
     * Search for a movie in the tmdb database using API
     * @param title the movie title to find in tmdb database
     * @return a JSON object containing information about the movie
     */
    public static JSONObject movieSearch(String title) {

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
            JSONObject obj = new JSONObject(responseStrBuilder.toString());

            //the movies found are stored as arrays under the results key
            JSONArray results = new JSONArray(obj.getJSONArray("results"));

            //if multiple movies are found the first one in the list is returned
            return (JSONObject) results.get(0);

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
