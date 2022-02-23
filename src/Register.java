import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Register {
   private String username;
   private char[] password;
   private char[] otherpass;
   private byte[] salt;
   private byte[] hashedpassword;
    enum Result{
       OK,
       EMPTY_FIELDS,
       DUPLICATE,
   }
    public Register(String username, char[] password, char[] other) {
        this.username = username;
        this.password = password;
        this.otherpass = other;
    }
    public Boolean val_rep_pass(){
        return(Arrays.equals(password,otherpass));
    }
    public Future<Register.Result> reg() throws NullPointerException {
        return Executors.newSingleThreadExecutor().submit(() -> {
            if (username.trim().isEmpty() || password.length == 0 || otherpass.length==0) {
                return Register.Result.EMPTY_FIELDS;
            }else {
                salt = Passwords.getNextSalt();
                hashedpassword = Passwords.hash(password, salt);
                if (DatabaseConn.registerUser(this.username, hashedpassword, salt)) {
                    return Result.OK;
                } else {
                    return Result.DUPLICATE;
                }
            }
        });
    }
}