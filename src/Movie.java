import java.net.URL;

public class Movie {

    private String name;
    private int id;
    private String description;
    private int year;
    private URL posterURL;

    public Movie(String name, int id, String description, int year, URL posterURL) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.year = year;
        this.posterURL = posterURL;
    }

    //------ GETTERS & SETTERS

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDescription() {return description; }

    public void setDescription(String description) { this.description = description; }

    public int getYear() { return year; }

    public void setYear(int year) { this.year = year; }

    public URL getPosterURL() { return posterURL; }

    public void setPosterURL(URL posterURL) { this.posterURL = posterURL; }

}
