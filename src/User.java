import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class User{

    private String username;
    private LinkedList<Integer> circles;
    private boolean darkmode = false;

    /**
     * Create a new User
     * @param username Enter username
     */
    public User(String username){
        this.username = username;
    }

//----Getters and setters------------------------------------------
    public String getUsername(){ return this.username; }

    public LinkedList<Integer> getCircles(){ return this.circles; }

    public void setCircles(Integer circle){
        this.circles.add(circle);
    }

    public boolean getDarkMode(){
        return this.darkmode;
    }

    public void setDarkmode(boolean darkmode){ this.darkmode = darkmode; }
}
