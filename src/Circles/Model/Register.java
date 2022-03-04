package Circles.Model;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class that handles Register logic.
 * @author Oscar
 * @version 2022-03-02
 */
public class Register {
   private String username;
   private char[] password;
   private char[] otherpass;
   private byte[] salt;
   private byte[] hashedpassword;

   public enum Result{
       OK,
       EMPTY_FIELDS,
       DUPLICATE,
    }

    /**
     * Register constructor which takes username, password that a user entered
     * and confirmation password to the first entered password called other.
     * @param username
     * @param password
     * @param other
     */
    public Register(String username, char[] password, char[] other) {
        this.username = username;
        this.password = password;
        this.otherpass = other;
    }

    /**
     * function which checks user entered password against confirmation password other.
     *
     * @return a boolean: true if password match confirmation password other. False if it's not a match
     */
    public Boolean val_rep_pass(){
        return(Arrays.equals(password,otherpass));
    }

    /**
     * function for registering a user in the database, it will create a new thread when executing.
     * function also checks that the user has provided a username, password and confirmation password.
     * @return result of type enum depending on the outcome when we tries to add the user to the database.
     * @throws NullPointerException
     */

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