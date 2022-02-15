
import java.sql.*;


public classDatabaseConn {

    private static DatabaseConn instance;
    private Connection c;

    private DatabaseConn(){
        try {
            String url = "jdbc:postgresql://ec2-34-251-245-108.eu-west-1.compute.amazonaws.com/ddc0n5csqqi7j1";
            String username = "pqubleerdfotaw";
            String password = "669355566f42eaf8ccd90c172ce3b3a68a02367ab19babcf61d4d3d63cb99d36";
            this.c = DriverManager.getConnection(url, username, password);

        } catch (Exception e){ System.out.println("Error establishing database connection.");}
    }

    private static DatabaseConn getInstance() throws SQLException{
        if (instance == null) {
            instance = new DatabaseConn();
        }
        return instance;
    }

    /* FUNCTIONS RELATED TO USER DATA */
    public static boolean addUser(String username, byte[] obj, byte[] hash, byte[] salt){
        try {
            PreparedStatement ps = getInstance().c.prepareStatement("INSERT INTO users VALUES (?,?,?,?)");
            ps.setString(1, username);
            ps.setBytes(2, obj);
            ps.setBytes(3, hash);
            ps.setBytes(4, salt);
            ps.executeUpdate();
            return true;
        } catch (SQLException se){
            return false;
        }
    }

    public static byte[] getUser(String username){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT obj FROM users WHERE username = ?");
            ps.setString(1, username);
            ResultSet r = ps.executeQuery();
            r.next();
            return r.getBytes(1);
        } catch (SQLException se){
            return null;
        }
    }

    public static byte[] getHash(String username){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT hash FROM users WHERE username = ?");
            ps.setString(1, username);
            ResultSet r = ps.executeQuery();
            r.next();
            return r.getBytes(1);
        } catch (SQLException se){
            return null;
        }
    }

    public static byte[] getSalt(String username){
        try{
            PreparedStatement ps = getInstance().c.prepareStatement("SELECT salt FROM users WHERE username = ?");
            ps.setString(1, username);
            ResultSet r = ps.executeQuery();
            r.next();
            return r.getBytes(1);
        } catch (SQLException se){
            return null;
        }
    }
}

