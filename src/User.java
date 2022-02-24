import java.awt.*;
import java.util.LinkedList;

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


    public Color getForegroundColor() {
        if (darkmode) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }
    public Color getBackgroundColor() {
        if (darkmode) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }
}
