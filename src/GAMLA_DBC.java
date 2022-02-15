import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class GAMLA_DBC{
    Connection connection;


    public GAMLA_DBC() throws SQLException, URISyntaxException {
        this.connection = getConnection();
    }

    public boolean addUser(String username, byte[] hash, byte[] salt){
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users VALUES (?,?,?)");
            ps.setString(1,username);
            ps.setBytes(2,hash);
            ps.setBytes(3,salt);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void close() throws SQLException {
        connection.close();
    }

    public byte[] getHash(String username){
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT password FROM users WHERE login = ?");
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            rs.next();

            byte[] hash = rs.getBytes(1);
            return hash;
        } catch (SQLException e){
            return null;
        }
    }

    public byte[] getSalt(String username){
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT salt FROM users WHERE login = ?");
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            byte[] salt = rs.getBytes(1);
            return salt;
        } catch (SQLException e){
            return null;
        }
    }

    public String[] getUsers(){
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT login FROM users");
           String[] result = new String[500];
           int i = 0;
           while(rs.next()){
               result[i++] = rs.getString("login");
           }
           return result;
        } catch (SQLException e){
            return null;
        }
    }


    private static Connection getConnection() throws URISyntaxException, SQLException {
            URI dbUri = new URI("postgres://pqubleerdfotaw:669355566f42eaf8ccd90c172ce3b3a68a02367ab19babcf61d4d3d63cb99d36@ec2-34-251-245-108.eu-west-1.compute.amazonaws.com:5432/ddc0n5csqqi7j1");

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

            return DriverManager.getConnection(dbUrl, username, password);
    }

}