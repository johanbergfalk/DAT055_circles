package Circles.Model;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class for handling login logic and validate if a user entered correct password and username.
 *@author Oscar
 *@version 2022-03-02
 */
public class Login {
private char[] password;
private String username;
private byte[] getsalt;
private byte[] gethash;
   public enum Result{
        OK,
        EMPTY_FIELDS,
        NO_SUCH_USER,
    }

    /**
     *Login constructor takes a users entered password and username.
     * @param password
     * @param username
     */
    public Login(char[] password, String username) {
            this.password = password;
            this.username = username;
    }

    /**
     * Method for validating a user, it will create a new thread when executing.
     *
     * @return deepending on outcome it will throw a Result of type enum, telling what went wrong or if a sucessful login occurred.
     * @throws NullPointerException
     */
    public Future<Result> validateuser() throws NullPointerException {
            getsalt = DatabaseConn.getSalt(username);
            gethash = DatabaseConn.getHash(username);
            return Executors.newSingleThreadExecutor().submit(() -> {
                if (username.trim().isEmpty() || password.length == 0) {
                    return Result.EMPTY_FIELDS;
                }
                if (getsalt == null || gethash == null) {
                    return Result.NO_SUCH_USER;
                }
                if (Passwords.isExpectedPassword(password, getsalt, gethash)) {
                    return Result.OK;
                }else {
                    return Result.NO_SUCH_USER;
                }
            });
        }
    }
