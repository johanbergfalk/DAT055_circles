import java.awt.*;
import java.util.LinkedList;

public class User{

    private String username;
    private LinkedList<Integer> circles;
    private boolean darkMode = false;

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
        return this.darkMode;
    }

    public void setDarkmode(boolean darkmode){ this.darkMode = darkmode; }


    public Color getForegroundColor() {
        if (darkMode) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }
    public Color getBackgroundColor() {
        if (darkMode) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }
}
