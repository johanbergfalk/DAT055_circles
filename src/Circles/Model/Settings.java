package Circles.Model;

import java.util.Arrays;

/**
 * Class for handle settings logic.
 *@author Oscar
 *@version 2022-03-02
 */
public class Settings {

    private final char[] password;
    private final char[] newpass;
    private final char[] newotherpass;
    private final String username;

    public enum Result{
        OK,
        EMPTY_FIELDS,
        CANT_UPDATE,
        NO_SUCH_USER,
        PASSWORD_MATCH_ERROR,
        OLD_PASSWORD_ERROR,
    }

    /**
     * Settings constructor sets instance variable values.
     * @param pass users current password
     * @param newpass users new password
     * @param username Username of the user
     * @param newotherpass user confirmation of new password
     */

    public Settings(char[] pass, char[] newpass, String username, char[] newotherpass){
        this.password=pass;
        this.newpass=newpass;
        this.username=username;
        this.newotherpass=newotherpass;
    }

    /**
     * function for changing a users password and update it to the database.
     * also check that the users have provided values for the parameters username, password, newpass,newotherpass.
     * @return result of type enum depending on outcome.
     */

    public Result changepass(){
        if (username.trim().isEmpty() || password.length == 0 || newotherpass.length==0 || newpass.length==0) {
            return Settings.Result.EMPTY_FIELDS;
        }
        if(!(Arrays.equals(newpass,newotherpass))){
            return Result.PASSWORD_MATCH_ERROR;
        }
        byte[] getsalt = DatabaseConn.getSalt(username);
        byte[] gethash = DatabaseConn.getHash(username);

        if (getsalt == null || gethash == null) {
            return Settings.Result.NO_SUCH_USER;
        }
        if (!Passwords.isExpectedPassword(password, getsalt, gethash)){
            return Result.OLD_PASSWORD_ERROR;
        }
        else {
            byte[] salt = Passwords.getNextSalt();
            byte[] hashedpassword = Passwords.hash(newpass, salt);
            if (DatabaseConn.updateUserpass(this.username, hashedpassword, salt)) {
                return Settings.Result.OK;
            } else {
                return Settings.Result.CANT_UPDATE;
            }
        }
    }
}
