import java.util.Arrays;

public class Settings {

    private char[] password;
    private char[] newpass;
    private char[] newotherpass;
    private String username;
    private byte[] salt;
    private byte[] hashedpassword;
    private byte[] getsalt;
    private byte[] gethash;

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
            getsalt=DatabaseConn.getSalt(username);
            gethash=DatabaseConn.getHash(username);

        if (getsalt == null || gethash == null) {
            return Settings.Result.NO_SUCH_USER;
        }
        if (!Passwords.isExpectedPassword(password, getsalt, gethash)){
            return Result.OLD_PASSORD_ERROR;
        }
        else {
            salt = Passwords.getNextSalt();
            hashedpassword = Passwords.hash(newpass, salt);
            if (DatabaseConn.updateUserpass(this.username, hashedpassword, salt)) {
                return Settings.Result.OK;
            } else {
                return Settings.Result.CANT_UPDATE;
            }
        }
    }



}
