
import java.sql.*;
import java.util.LinkedList;
import java.util.function.Consumer;


public class DatabaseConn {

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
    public static void getUserCircles(String username){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT id FROM Circlemembers WHERE member = ?");
            ps.setString(1, username);
            ResultSet circleID = ps.executeQuery();
            while(circleID.next()){
                Circle temp = new Circle();
                int id = circleID.getInt(1);
                ps = getInstance().c.prepareStatement("SELECT * FROM Circles WHERE id = ?");
                ps.setString(1,Integer.toString(id));
                ResultSet circle = ps.executeQuery();
                circle.next();

            }
        } catch (SQLException e){

        }
    }

}


