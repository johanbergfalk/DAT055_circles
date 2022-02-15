import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable {

    private String username;
    private LinkedList<Integer> circles;
    private boolean nightModeActive = false;

    /**
     * Create a new User
     * @param username Enter username
     */
    public User(String username){
        this.username = username;

    }

//----Getters and setters------------------------------------------
    public String getUsername(){
        return this.username;
    }

    public LinkedList<Integer> getCircles(){
        return this.circles;
    }

    public boolean getNightModeActive(){
        return this.nightModeActive;
    }

    public void setCircles(Integer circle){
        this.circles.add(circle);
    }



}
