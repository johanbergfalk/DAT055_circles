
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;



public class DatabaseConn {
    //Singleton
    private static DatabaseConn instance;
    private Connection c;

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
            ps.executeUpdate();
            return true;
        } catch (SQLException se) {
            return false;
        }
    }

    public static GradeComment getUserRating(Circle c, User u, Movie m){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT * FROM MovieReview WHERE(username = ? AND circleid = ? AND movieid = ?)");
            ps.setString(1, u.getUsername());
            ps.setInt(2, c.getId());
            ps.setInt(3, m.getId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            GradeComment result = new GradeComment();
            result.setUser(u);
            result.setComment(rs.getString("comment"));
            result.setMovie(m);
            result.setCircle(c);
            return result;
        } catch (SQLException e){
            return null;
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
            return rs.getBytes(1);
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
            return rs.getBoolean(1);
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
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getBoolean(1);
        } catch (SQLException se){
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
                //printc(temp);
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
     * @param c Circle to be added
     * @return returns true if successful, false if not.
     */
    public static boolean addCircle(Circle c){
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
        } catch (SQLException e){
            return false;
        }
        return true;
    }

    public static int getCircleID(Circle c){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT id FROM Circles WHERE name = ? AND creator = ?");
            ps.setString(1, c.getName());
            ps.setString(2, c.getCreator());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e){
            return -1;
        }

    }
    /**
     * Removes the circle from the database.
     * @param c Circle to be removed
     * @return returns true if successful, false if not
     */
    public static boolean deleteCircle(Circle c){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("DELETE FROM Circles WHERE id = ?");
            ps.setInt(1, c.getId());
            ps.execute();
            return true;
        } catch (SQLException e){
            return false;
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
            return false;
        }
    }

    /**
     * Removes a movie from a circle in the database.
     * @param circle Circle to operate on
     * @param movie Movie to be removed
     * @return returns true if successful, false if not
     */
    public static boolean removeMovieCircle(Circle circle, Movie movie){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("DELETE FROM MovieInCircle WHERE (circleid = ? AND movieid = ?)");
            ps.setInt(1, circle.getId());
            ps.setInt(2, movie.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    public static boolean joinCircle(Circle c, User u){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("INSERT INTO CircleMembers VALUES(?,?)");
            ps.setInt(1, c.getId());
            ps.setString(2, u.getUsername());
            ps.execute();
            return true;
        } catch (SQLException e){
            return false;
        }
    }


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
    /*
    /**
     * Updates a circles parameters except creator and ID. Does update all members in the database.
     * @param c Circle to be updated
     * @return Returns true if successful, false if not.
     *//*
    public static boolean updateCircle(Circle c){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("UPDATE Circles SET name = ?, description = ?, timestart = ?, timeend = ?, score = ? WHERE id = ?");
            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            java.util.Date start_t = c.getStartTime();
            ps.setString(3, start_t.toString());
            java.util.Date end_t = c.getStopTime();
            ps.setString(4, end_t.toString());
            ps.setFloat(5, c.getScore());
            ps.setInt(6, c.getId());
            ps.executeUpdate();
            LinkedList<String> members = c.getMembers();
            PreparedStatement mem = getInstance().c.prepareStatement("DELETE FROM CircleMembers WHERE id = ?");
            int id = c.getId();
            ps.setInt(1, id);
            ps.execute();
            for(int i = 0; i < members.size(); i++){
                mem = getInstance().c.prepareStatement("INSERT INTO CircleMembers VALUES (?,?)");
                mem.setInt(1, id);
                mem.setString(2, members.get(i));
            }
            return true;
        } catch (SQLException e){
            return false;
        }
    }
    */
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

    public static Movie getFirstMovie(Circle c){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT * FROM movieInCircle WHERE circleid = ?");
            ps.setInt(1, c.getId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("movieid");
            System.out.println(id);
            Movie m = DatabaseConn.getMovie(id);
            return m;
        } catch (SQLException e){
            return null;
        }
    }
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




    // Enkel funktion för att skriva ut innehållet i en cirkel. Används för testning.
    public static void printc(Circle c){
        System.out.println("Id: " + c.getId());
        System.out.println("Name: " + c.getName());
        System.out.println("Creator: " + c.getCreator());
        System.out.println("Description: " + c.getDescription());
        System.out.println("Start time: " + c.getStartTime());
        System.out.println("Stop time: " + c.getStopTime());
        System.out.println("Score: " + c.getScore());
        System.out.println("====Members====");
        LinkedList<String> members = c.getMembers();
        for(int i = 0; i < members.size(); i++){
            System.out.println(members.get(i));
        }
        System.out.println("\n");
    }
}


