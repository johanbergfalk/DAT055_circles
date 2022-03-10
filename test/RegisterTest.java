import Circles.Model.DatabaseConn;
import Circles.Model.Register;
import Circles.Model.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Testing for Register class
 * @author Oscar Larsson
 * @version 2022-03-04
 */
class RegisterTest {
    char[] password = {4,5,6};
    char[] password2 = {4,5,6};
    char[] passwordempty = {};
    User user = new User("To_Be_Removed");
    @Test
    void val_rep_pass() {
        Register r = new Register(user.getUsername(),password,password2);
        Boolean result = r.val_rep_pass();
        assertEquals(true,result);
    }

    @Test
    void reg() {
        Register r = new Register(user.getUsername(),password,password2);
        try {
            DatabaseConn.removeTestUser(user);
            Register.Result rs = r.reg().get();
            assertEquals(Register.Result.OK, rs);

            Register.Result rs2 = r.reg().get();
            assertEquals(Register.Result.DUPLICATE, rs2);

            Register reg = new Register(user.getUsername(),password,passwordempty);
            Register.Result rs3 = reg.reg().get();
            assertEquals(Register.Result.EMPTY_FIELDS, rs3);

        }catch(NullPointerException | ExecutionException | InterruptedException | SQLException exception){
            exception.printStackTrace();
        }
    }
}