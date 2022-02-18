import java.io.*;
import java.net.*;
import org.json.*;

public class Movie {

    private String name;
    private int id;
    private String description;
    private String year;
    private String posterURL;

    public Movie(String name, int id, String description, String year, String posterURL) {

        this.name = name;
        this.id = id;
        this.description = description;
        this.year = year;
        this.posterURL = posterURL;
    }

    public Movie(String title) throws IOException {

        URL url = new URL("https://api.themoviedb.org/3/movie/" + title + "?api_key=9235f4be07271782e1c1021ed0762621");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = br.readLine()) != null)
            responseStrBuilder.append(inputStr);
        JSONObject obj = new JSONObject(responseStrBuilder.toString());

        this.name = obj.getString("original_title");
        this.id = obj.getInt("id");
        //this.description = obj.getString("genres.name");
        String year = obj.getString("release_date");
        String poster = obj.getString("poster_path");
        this.posterURL = "https://image.tmdb.org/t/p/original" + poster;

        System.out.println(this.name);
        System.out.println(this.id);
        System.out.println(year);
        System.out.println(this.posterURL);
    }

    public static String findMovieId(String title) {

        JSONObject obj = new JSONObject("src/Resources/movie_ids_02_17_2022.json");

        return obj.getString("id");
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