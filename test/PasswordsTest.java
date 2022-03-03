import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordsTest {
    @Test
    public void testPassword(){
        byte[] salt = Passwords.getNextSalt();
        char[] password = {'n','e','w','p','a','s','s','w','o','r','d'};
        char[] password2 = {'n','e','w','p','a','s','s','w','o','r','d'};
        byte[] hash = Passwords.hash(password, salt);

        boolean result = Passwords.isExpectedPassword(password2, salt, hash);

        assertTrue(result);
    }
}