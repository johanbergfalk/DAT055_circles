
import java.sql.*;
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


    /* FUNCTIONS RELATED TO LOGIN AND REGISTER */
    public static boolean registerUser(String username, byte[] hash, byte[] salt) {
            try {
                PreparedStatement ps = getInstance().c.prepareStatement("INSERT INTO login VALUES (?,?,?)");
                ps.setString(1, username);
                ps.setBytes(2, hash);
                ps.setBytes(3, salt);
                ps.executeUpdate();
                return true;
            } catch (SQLException se) {
                return false;
            }
    }

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


    /* FUNCTIONS RELATED TO CIRCLES */
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
                temp.setDescription("description");
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
                printc(temp);
            }
            return circles;
        } catch (SQLException | ParseException e){
            return null;
        }
    }

    public static boolean addCircle(Circle c){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("INSERT INTO circles VALUES(default,?,?,?,?,?,default)");
            ps.setString(1, c.getName());
            ps.setString(2, c.getCreator());
            ps.setString(3, c.getDescription());
            java.util.Date start_t = c.getStartTime();
            ps.setString(4, start_t.toString());
            java.util.Date end_t = c.getStopTime();
            ps.setString(5, end_t.toString());
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


