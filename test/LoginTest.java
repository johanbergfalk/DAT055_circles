import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

//----Login ----------------------------------------------------------------
    @Test
    public void loginFailsWhenNonExistingUser() throws ExecutionException, InterruptedException {
        Login login = new Login(new char[] {0}, "IDONTEXIST!!!");
        Login.Result result = login.validateuser().get();

        assertEquals(Login.Result.NO_SUCH_USER, result);
    }
}
