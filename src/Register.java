public class Register {
    String username;
    char[] password;
    byte[] salt;
    byte[] hashedpassword;
    boolean sucess;

    public Register(String username, char[] password) {
        this.username = username;
        this.password = password;
        salt = Passwords.getNextSalt();
        hashedpassword = Passwords.hash(this.password, salt);
    }

    public void reg() {
        new Thread(() -> {
            if (DatabaseConn.registerUser(this.username, hashedpassword, salt)) {
                sucess = true;
            } else {
                sucess = false;
            }
        }).start();
    }

    public boolean Get_regstatus() {
        return sucess;
    }
}