package Circles.Model;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;


/**
 * Class that handles the methods that requires database connection. Maintains singleton state.
 * @author Filip Svensson
 */
public class DatabaseConn {
    //Singleton
    private static DatabaseConn instance;
    private Connection c;


    /* These functions ensures singleton principle. */
    private DatabaseConn() {
        try {
            String url = "jdbc:postgresql://ec2-34-251-245-108.eu-west-1.compute.amazonaws.com/ddc0n5csqqi7j1";
            String username = "pqubleerdfotaw";
            String password = "669355566f42eaf8ccd90c172ce3b3a68a02367ab19babcf61d4d3d63cb99d36";
            this.c = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            System.out.println("Error establishing database connection.");
        }
    }

    private static DatabaseConn getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConn();
        }
        return instance;
    }




    /* FUNCTIONS RELATED TO USER */
    /**
     * Registers a user in the database.
     * <p>
     * @param username username to be added
     * @param hash hash generated from encryption algorithm
     * @param salt salt used to generate the hash
     * @return returns true if registration is successful, otherwise false.
     */
    public static boolean registerUser(String username, byte[] hash, byte[] salt) {
        try {
            PreparedStatement ps = getInstance().c.prepareStatement("INSERT INTO login VALUES (?,?,?)");
            ps.setString(1, username);
            ps.setBytes(2, hash);
            ps.setBytes(3, salt);
            ps.executeUpdate();

            PreparedStatement ps2 = getInstance().c.prepareStatement("INSERT INTO users VALUES (?,?)");
            ps2.setString(1, username);
            ps2.setBoolean(2, false);
            ps2.executeUpdate();

            return true;
        } catch (SQLException se) {
            return false;
        }
    }

    /**
     * Updates a users password
     * @param username name of the user
     * @param hash hash generated from encryption algorithm
     * @param salt salt used to generate hash
     * @return returns true if successful, false if there was an error.
     */
    public static boolean updateUserpass(String username, byte[] hash, byte[] salt) {
        try {
            PreparedStatement ps = getInstance().c.prepareStatement("UPDATE login SET hash = ?, salt= ? WHERE username=?");
            ps.setBytes(1, hash);
            ps.setBytes(2, salt);
            ps.setString(3, username);
            int result = ps.executeUpdate();
            if(result == 1){
                return true;
            }
            return false;
        } catch (SQLException se) {
            return false;
        }
    }

    /**
     * Used to retrieve a users hash. Used during login to verify credentials.
     * @param user username
     * @return if successful it returns a byte array containing the hash, otherwise it returns null
     */
    public static byte[] getHash(String user){
        try {
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT hash FROM login WHERE username = ?");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getBytes("hash");
        } catch (SQLException se){
            return null;
        }
    }

    /**
     * Used to retrieve a users salt. Used during login to verify credentials.
     * @param user username
     * @return If successfull it returns a byte array containing the salt, otherwise it returns null.
     */
    public static byte[] getSalt(String user){
        try {
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT salt FROM login WHERE username = ?");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getBytes(1);
        } catch (SQLException se){
            return null;
        }
    }

    /**
     * Retrieves the settings for the user.
     * @param user username
     * @return Returns the value of the darkmode setting as boolean, false by default if not successful.
     */
    public static boolean getUserMode(String user){
        try {
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT darkmode FROM users WHERE username = ?");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getBoolean("darkmode");
        } catch (SQLException se){
            return false;
        }
    }

    /**
     * Sets the settings for provided username
     * @param user username
     * @param mode mode to be set
     * @return returns a boolean with true or false if the change was successful or not.
     */
    public static boolean setUserMode(String user, boolean mode){
        try {
            PreparedStatement ps = getInstance().c.prepareStatement("UPDATE users SET darkmode = ? WHERE username = ?");
            ps.setBoolean(1, mode);
            ps.setString(2, user);
            int result = ps.executeUpdate();
            if(result == 1){
                return true;
            }
            return false;
        } catch (SQLException se){
            return false;
        }
    }




    /* FUNCTIONS RELATED TO RATINGS AND REVIEWS */
    /**
     * Retrieve the rating given by a user for a movie
     * @param c Circle that the movie belongs to
     * @param u User that is the author
     * @param m Movie rated
     * @return returns GradeComment, null if not successful.
     */
    public static GradeComment getUserRating(Circle c, User u, Movie m){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT * FROM MovieReview WHERE(username = ? AND circleid = ? AND movieid = ?)");
            ps.setString(1, u.getUsername());
            ps.setInt(2, c.getId());
            ps.setInt(3, m.getId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            GradeComment result = new GradeComment();
            result.setUser(u.getUsername());
            result.setComment(rs.getString("comment"));
            result.setMovie(m);
            result.setCircle(c);
            result.setUserRating(rs.getInt("rating"));
            return result;
        } catch (SQLException e){
            return null;
        }
    }

    /**
     * Gets the average score for a given movie in a circle.
     * @param c Circle that the movie belongs to
     * @param m Movie rated
     * @return Returns a GradeComment object. Null if not successful.
     */
    public static GradeComment avgMovieScore (Circle c, Movie m){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT AVG(rating) AS movieavg FROM MovieReview WHERE(circleid = ? AND movieid = ?)");
            ps.setInt(1, c.getId());
            ps.setInt(2, m.getId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            GradeComment result = new GradeComment();
            result.setAvgMovieGrade(rs.getFloat("movieavg"));
            return result;
        } catch (SQLException e){
            return null;
        }
    }

    /**
     * Gets average score for all movies in this circle
     * @param c Circle
     * @return Returns a GradeComment object. Null if not successful.
     */
    public static GradeComment avgCircleScore (Circle c){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT AVG(rating) AS circleavg FROM MovieReview WHERE(circleid = ?)");
            ps.setInt(1, c.getId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            GradeComment result = new GradeComment();
            result.setAvgCircleGrade(rs.getFloat("circleavg"));
            return result;
        } catch (SQLException e){
            return null;
        }
    }

    /**
     * Retrieve all ratings for this movie
     * @param c Circle that the movie belongs to
     * @param m Movie rated
     * @return Returns a LinkedList consisting of GradeComment objects. Null if not successful.
     */
    public static LinkedList<GradeComment> getAllMovieRatings(Circle c, Movie m){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT * FROM MovieReview WHERE circleid = ? AND movieid = ?");
            ps.setInt(1, c.getId());
            ps.setInt(2, m.getId());
            LinkedList<GradeComment> ratings = new LinkedList<>();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                GradeComment temp = new GradeComment();
                temp.setMovie(m);
                temp.setCircle(c);
                temp.setUser(rs.getString("username"));
                temp.setComment(rs.getString("comment"));
                temp.setUserRating(rs.getInt("rating"));
                ratings.add(temp);
            }
            return ratings;
        } catch (SQLException e){
            return null;
        }
    }

    /**
     * Add a review for a movie in a circle
     * @param U User that made the review
     * @param circle Circle that the movie belongs to
     * @param movie Movie in question
     * @param rating Given rating
     * @param comment Given comment
     * @return Returns true if successful, false in all other cases.
     */
    public static boolean addMovieReview(User U, Circle circle, Movie movie, int rating, String comment){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("INSERT INTO moviereview VALUES (?,?,?,?,?)");

            ps.setString(1, U.getUsername());
            ps.setInt(2, circle.getId());
            ps.setInt(3, movie.getId());
            ps.setInt(4, rating);
            ps.setString(5, comment);
            ps.execute();
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    /**
     * Check whether a user has reviewed specified movie in this circle
     * @param U User in question
     * @param circle Circle in question
     * @param movie Movie to check for reviews made by this user
     * @return Returns true if requested review is found, false in all other cases.
     */
    public static boolean isReviewed(User U, Circle circle, Movie movie){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT username,circleid,movieid FROM moviereview WHERE (username=? AND circleid=? AND movieid=?)");

            ps.setString(1, U.getUsername());
            ps.setInt(2, circle.getId());
            ps.setInt(3, movie.getId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("circleid") == circle.getId();
        } catch (SQLException e){
            return false;
        }
    }




    /* FUNCTIONS RELATED TO CIRCLES */
    /**
     * Used to retrieve the circles a user has joined.
     * @param username username
     * @return Returns a LinkedList of circles. Null if not successful.
     */
    public static LinkedList<Circle> getUserCircles(String username){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT id FROM Circlemembers WHERE member = ?");
            ps.setString(1, username);
            ResultSet circleID = ps.executeQuery();
            LinkedList<Circle> circles = new LinkedList<Circle>();
            while(circleID.next()){
                Circle temp = new Circle();
                int id = circleID.getInt(1);
                ps = getInstance().c.prepareStatement("SELECT * FROM Circles WHERE id = ?");
                ps.setInt(1, id);
                ResultSet circle = ps.executeQuery();
                circle.next();
                temp.setId(id);
                temp.setName(circle.getString("name"));
                temp.setCreator(circle.getString("creator"));
                temp.setDescription(circle.getString("description"));
                temp.setScore(circle.getFloat("score"));
                String start_t = circle.getString("timestart");
                java.util.Date starttime = new SimpleDateFormat("yyyy-MM-dd").parse(start_t);
                temp.setStartTime(starttime);
                String end_t = circle.getString("timeend");
                java.util.Date endtime = new SimpleDateFormat("yyyy-MM-dd").parse(end_t);
                temp.setStopTime(endtime);
                ps = getInstance().c.prepareStatement("SELECT member FROM CircleMembers WHERE id = ?");
                ps.setInt(1, id);
                ResultSet rs_member = ps.executeQuery();
                LinkedList<String> members = new LinkedList<String>();
                while(rs_member.next()){
                    members.add(rs_member.getString(1));
                }
                temp.setMembers(members);
                circles.add(temp);
            }
            return circles;
        } catch (SQLException | ParseException e){
            return null;
        }
    }

    /**
     * Used to retreive all circles currently in the database.
     * @return Returns a Linkedlist of circles, null if not successful.
     */
    public static LinkedList<Circle> getAllCircles(){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT * FROM Circles");
            ResultSet rs = ps.executeQuery();
            LinkedList<Circle> circles = new LinkedList<Circle>();
            while(rs.next()){
                Circle temp = new Circle();
                int id = rs.getInt("id");
                temp.setId(id);
                ps = getInstance().c.prepareStatement("SELECT * FROM Circles WHERE id = ?");
                ps.setInt(1, id);
                ResultSet current_circle = ps.executeQuery();
                current_circle.next();
                temp.setName(current_circle.getString("name"));
                temp.setCreator(current_circle.getString("creator"));
                temp.setDescription(current_circle.getString("description"));
                temp.setScore(current_circle.getFloat("score"));
                String start_t = current_circle.getString("timestart");
                java.util.Date starttime = new SimpleDateFormat("yyyy-MM-dd").parse(start_t);
                String end_t = current_circle.getString("timeend");
                java.util.Date endtime = new SimpleDateFormat("yyyy-MM-dd").parse(end_t);
                temp.setStartTime(starttime);
                temp.setStopTime(endtime);
                ps = getInstance().c.prepareStatement("SELECT member FROM CircleMembers WHERE id = ?");
                ps.setInt(1, id);
                ResultSet rs_member = ps.executeQuery();
                LinkedList<String> members = new LinkedList<String>();
                while(rs_member.next()){
                    members.add(rs_member.getString(1));
                }
                temp.setMembers(members);
                circles.add(temp);
            }
            return circles;
        } catch (SQLException | ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Used to retrieve the movies a circle has.
     * @param c Circle object
     * @return Returns a Linkedlist of movies, null if not successful.
     */
    public static LinkedList<Movie> getCircleMovies(Circle c){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT movieid FROM MovieInCircle WHERE circleid = ?");
            ps.setInt(1, c.getId());
            ResultSet idnumbers = ps.executeQuery();
            LinkedList<Movie> movies = new LinkedList<>();
            while(idnumbers.next()){
                PreparedStatement ps2 = getInstance().c.prepareStatement("SELECT * FROM Movies WHERE id = ?");
                ps2.setInt(1, idnumbers.getInt(1));
                ResultSet movie = ps2.executeQuery();
                Movie temp = new Movie();
                movie.next();
                temp.setName(movie.getString("name"));
                temp.setId(movie.getInt("id"));
                temp.setDescription(movie.getString("description"));
                temp.setYear(movie.getString("year"));
                temp.setPosterURL(movie.getString("posterurl"));
                movies.add(temp);
            }
            return movies;
        } catch (SQLException e){
            return null;
        }
    }
    /**
     * Adds a new circle to the database.
     * @param c Circles.Controller.Model.Circle to be added
     * @return Returns (1,0,-1) for (success, already exists, wrong input)
     */
    public static int addCircle(Circle c){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("INSERT INTO circles VALUES(default,?,?,?,?,?,default)");
            ps.setString(1, c.getName());
            ps.setString(2, c.getCreator());
            ps.setString(3, c.getDescription());
            java.util.Date start_t = c.getStartTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //Datum
            String start_time = dateFormat.format(start_t);
            ps.setString(4, start_time);
            java.util.Date end_t = c.getStopTime();
            String end_time = dateFormat.format(end_t);
            ps.setString(5, end_time);
            ps.execute();
            ps = getInstance().c.prepareStatement("SELECT id FROM Circles WHERE name = ?");
            ps.setString(1, c.getName());
            ResultSet rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            LinkedList<String> members = c.getMembers();
            for(String member : members){
                ps = getInstance().c.prepareStatement("INSERT INTO CircleMembers VALUES(?,?)");
                ps.setInt(1, id);
                ps.setString(2, member);
                ps.execute();
            }
            return 1;
        } catch (SQLException e){
            return 0;
        } catch (NullPointerException e){
            return -1;
        }
    }

    /**
     * Function used to retrieve the PostgreSQL generated ID for the circle.
     * @param c The circle whose ID is asked for
     * @return Returns the id as integer, -1 if not successful.
     */
    public static int getCircleID(Circle c){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT id FROM Circles WHERE name = ? AND creator = ?");
            ps.setString(1, c.getName());
            ps.setString(2, c.getCreator());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * Add a movie to a circle in the database.
     * @param circle Circle to add to
     * @param movie Movie to be added
     * @return returns true if successful, false if not.
     */
    public static boolean addMovieCircle(Circle circle, Movie movie){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("INSERT INTO MovieinCircle VALUES (?,?)");
            ps.setInt(1, circle.getId());
            ps.setInt(2, movie.getId());
            ps.execute();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Insert a member into a circle
     * @param c Circle to join
     * @param u User that wants to join
     * @return returns true if successful, false otherwise
     */
    public static boolean joinCircle(Circle c, User u){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("INSERT INTO CircleMembers VALUES(?,?)");
            ps.setInt(1, c.getId());
            ps.setString(2, u.getUsername());
            ps.execute();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes a member from a circle
     * @param c Circle target to member removal
     * @param u User to be removed
     * @return returns true if successful, false otherwise
     */
    public static boolean leaveCircle(Circle c, User u){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("DELETE FROM CircleMembers WHERE id = ? AND member = ?");
            ps.setInt(1, c.getId());
            ps.setString(2, u.getUsername());
            ps.execute();
            return true;
        } catch (SQLException e){
            return false;
        }
    }




    /* FUNCTIONS RELATED TO MOVIES */
    /**
     * Adds a movie to the database.
     * @param movie Movie to be added
     * @return returns true if successful, false if not.
     */
    public static boolean addMovie(Movie movie){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("INSERT INTO Movies VALUES (?,?,?,?,?)");
            ps.setString(1, movie.getName());
            ps.setInt(2, movie.getId());
            ps.setString(3, movie.getDescription());
            ps.setString(4, movie.getYear());
            ps.setString(5, movie.getPosterURL());
            ps.execute();
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    /**
     * Returns a movie object from the database
     * @param id The API id of the movie
     * @return Returns a Movie-object
     */
    public static Movie getMovie(int id){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT * FROM Movies WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Movie m = new Movie();
            m.setName(rs.getString("name"));
            m.setId(rs.getInt("id"));
            m.setDescription(rs.getString("description"));
            m.setYear(rs.getString("year"));
            m.setPosterURL(rs.getString("posterurl"));
            return m;
        } catch (SQLException e){
            return null;
        }
    }

    /**
     * Returns the first movie in the circle. Used to retrieve poster urls for display.
     * @param c Circle to check.
     * @return Returns the movie object.
     */
    public static Movie getFirstMovie(Circle c){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT * FROM movieInCircle WHERE circleid = ?");
            ps.setInt(1, c.getId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("movieid");
            Movie m = DatabaseConn.getMovie(id);
            return m;
        } catch (SQLException e){
            return null;
        }
    }






    /*
    FOLLOWING METHODS IS ONLY USED TO REMOVE INSERTS BY THE TESTING FILES, AND NOT
    USED BY THE APPLICATION
     */
    public static void removeTestReview(User u, Circle c, Movie m) throws SQLException {
        PreparedStatement ps = getInstance().c.prepareStatement("DELETE FROM moviereview WHERE username = ? AND circleid = ? AND movieid = ?");
        ps.setString(1, u.getUsername());
        ps.setInt(2, c.getId());
        ps.setInt(3, m.getId());
        ps.execute();
    }

    public static void removeTestUser(User u) throws SQLException {
        String username = u.getUsername();
        PreparedStatement ps = getInstance().c.prepareStatement("DELETE FROM Users WHERE username = ?");
        ps.setString(1, username);
        ps.execute();
        ps = getInstance().c.prepareStatement("DELETE FROM Login WHERE username = ?");
        ps.setString(1, username);
        ps.execute();
    }

    public static void removeTestCircle(String name) throws SQLException {
        PreparedStatement ps = getInstance().c.prepareStatement("DELETE FROM Circles WHERE name = ?");
        ps.setString(1, name);
        ps.execute();
    }
    public static void removeTestMovie(Movie m) throws SQLException {
        PreparedStatement ps = getInstance().c.prepareStatement("DELETE FROM Movies WHERE id = ?");
        ps.setInt(1, m.getId());
        ps.execute();
    }

}








