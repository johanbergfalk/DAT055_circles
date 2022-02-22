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

    public Movie(String name, int id, String description, String year, String posterURL) {

        this.name = name;
        this.id = id;
        this.description = description;
        this.year = year;
        this.posterURL = posterURL;

    }

    public Movie(String title) {

        try {

            //converts spaces in the title with %20 for url formatting
            title = title.replaceAll("\\s+","%20");

            URL url = new URL("https://api.themoviedb.org/3/search/movie?api_key=9235f4be07271782e1c1021ed0762621&language=en-US&query=" + title + "&page=1&include_adult=false");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = br.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject obj = new JSONObject(responseStrBuilder.toString());

            JSONArray results = new JSONArray(obj.getJSONArray("results"));

            JSONObject movie = (JSONObject) results.get(0);


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

        catch(IOException e) {
            System.out.println(e);
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
