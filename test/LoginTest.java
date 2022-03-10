import Circles.Model.Login;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Internal tests of login Class
 * @author Felix Naredi
 * @version 2022-03-02
 */
public class LoginTest {

//----Circles.Controller.Model.Login ----------------------------------------------------------------
    @Test
    public void loginFailsWhenNonExistingUser() throws ExecutionException, InterruptedException {
        Login login = new Login(new char[] {0}, "IDONTEXIST!!!");
        Login.Result result = login.validateuser().get();

        assertEquals(Login.Result.NO_SUCH_USER, result);
    }
}
