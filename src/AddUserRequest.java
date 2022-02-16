public class AddUserRequest {
    private final String username;
    private final char[] password;

    public AddUserRequest(String username, char[] password) {
        this.username = username;
        this.password = password;
    }

    public byte[] getPasswordSalt() {
        return null;
    }
}
