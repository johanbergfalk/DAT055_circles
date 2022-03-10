import Circles.Model.User;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Internal tests of class User
 * @author Robert Nilsson
 * @version 2022-03-03
 */
class UserTest {

    //Test of method getForegroundColor -----------------
    @Test
    public void shouldReturnWhiteForeground(){
        User u = new User("TestingUser");
        u.setDarkmode(true);

        Color white = Color.WHITE;
        Color result = u.getForegroundColor();

        assertEquals(white, result);
    }
    @Test
    public void shouldReturnBlackForeground(){
        User u = new User("TestingUser");
        u.setDarkmode(false);

        Color black = Color.BLACK;
        Color result = u.getForegroundColor();

        assertEquals(black, result);
    }
    //Test of method getBackgroundColor ---------------------
    @Test
    public void shouldReturnBlackBackground(){
        User u = new User("TestingUser");
        u.setDarkmode(true);

        Color black = Color.BLACK;
        Color result = u.getBackgroundColor();

        assertEquals(black, result);
    }
    @Test
    public void shouldReturnWhiteBackground(){
        User u = new User("TestingUser");
        u.setDarkmode(false);

        Color white = Color.WHITE;
        Color result = u.getBackgroundColor();

        assertEquals(white, result);
    }
    //Test of method getCardColor ---------------------
    @Test
    public void shouldReturnDarkGrey() {
        User u = new User("TestingUser");
        u.setDarkmode(true);

        Color gray = Color.DARK_GRAY;
        Color result = u.getCardColor();

        assertEquals(gray, result);
    }
    @Test
    public void shouldReturnPanel() {
        User u = new User("TestingUser");
        u.setDarkmode(false);

        Color panel = UIManager.getColor("Panel.background");
        Color result = u.getCardColor();

        assertEquals(panel, result);
    }
    //Test of method getTextFieldColor ----------------------
    @Test
    public void shouldReturnDarkerGray() {
        User u = new User("TestingUser");
        u.setDarkmode(true);

        Color darkerGray = Color.decode("#333333");
        Color result = u.getTextFieldColor();

        assertEquals(darkerGray, result);
    }
    @Test
    public void shouldReturnFieldWhite() {
        User u = new User("TestingUser");
        u.setDarkmode(false);

        Color white = Color.WHITE;
        Color result = u.getTextFieldColor();

        assertEquals(white, result);
    }
    @Test
    public void testDarkModeAndName(){
        User u = new User("TestingUser");
        u.setDarkmode(true);
        assertTrue(u.getDarkMode());
        u.setDarkmode(false);
        assertFalse(u.getDarkMode());
        assertEquals("TestingUser", u.getUsername());

    }
}
