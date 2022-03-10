import Circles.Model.DatabaseConn;
import Circles.Model.Passwords;
import Circles.Model.Settings;
import Circles.Model.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing for Settings class
 * @author Filip Svensson
 * @version 2022-03-03
 */
class SettingsTest {

    @Test
    void test() throws SQLException {
        User user = new User("To_Be_Removed");
        char[] password = {4,5,6};
        char[] password2 = {4,5,6};
        byte[] salt = Passwords.getNextSalt();
        byte[] hash = Passwords.hash(password, salt);
        DatabaseConn.registerUser("To_Be_Removed", hash, salt);


        //Actual test:
        char[] newPassword = {1,2,3};
        Settings settings = new Settings(password2, newPassword, "To_Be_Removed", newPassword);
        assertEquals(Settings.Result.OK, settings.changepass());

        DatabaseConn.removeTestUser(user);


    }
}