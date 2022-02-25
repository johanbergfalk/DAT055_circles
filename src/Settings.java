import java.util.Arrays;

public class Settings {

    private final char[] password;
    private final char[] newpass;
    private final char[] newotherpass;
    private final String username;

    enum Result{
        OK,
        EMPTY_FIELDS,
        CANT_UPDATE,
        NO_SUCH_USER,
        PASSWORD_MATCH_ERROR,
        OLD_PASSORD_ERROR,
    }

    public Settings(char[] pass, char[] newpass, String username, char[] newotherpass){
        this.password=pass;
        this.newpass=newpass;
        this.username=username;
        this.newotherpass=newotherpass;
    }

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
            return Result.OLD_PASSORD_ERROR;
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
