package Circles.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Creates a user that can create and join circles.
 * @author Robert Nilsson
 * @version 2022-03-03
 */
public class User{

    private final String username;
    private boolean darkMode = false;

    /**
     * Create a new User.
     *
     * @param username Enter username
     */
    public User(String username){
        this.username = username;
    }
//----Getters and setters------------------------------------------
    public String getUsername(){ return this.username; }
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

    public Color getCardColor(){
        if (darkMode){
            return Color.DARK_GRAY;
        } else {
            return UIManager.getColor("Panel.background");
        }
    }

    public Color getTextFieldColor(){
        if(darkMode){
            return Color.decode("#333333"); //Slightly darker than dark gray
        } else {
            return Color.WHITE;
        }
    }
}
