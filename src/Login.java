/*
     ===== Kommer köra som vi kom fram till med felix och lägga alla nya threads i databaseconn.
             Men eftersom vi inte behöver lagra objekt nu utan kan skriva get/set funktion för allt så kommer
             metoderna i databas conn se annorlunda ut än tänkt från början. Så kika där efterhand hur dom
             utformas. Kan hända att vi kommer få ändra en del i våra tables med. ///Filip ==================

             new Thread(() -> {

             DatabaseConn.addUser(username.getText(), null, null, null, (result) -> {
             if (result) {
             System.out.println("GREAT!");
             } else {
             System.out.println("nope :(");

             SwingUtilities.invokeLater(() -> {
             username.setText("There was an error");
             });
             }
             });
             }).start();
             break;
             */
public class Login {

private char[] password;
private String username;
private byte[] getsalt;
private byte[] gethash;
private Boolean sucess;

    public Login(char[] password, String username){
        this.password=password;
        this.username=username;
    }

    public void validateuser() {
        new Thread(() -> {
            getsalt = DatabaseConn.getSalt(username);
            gethash = DatabaseConn.getHash(username);
            if (Passwords.isExpectedPassword(password, getsalt, gethash)) {
                sucess = true;
            }else {
                sucess=false;
            }
        }).start();
    }
    public boolean Get_succes(){
        return this.sucess;
    }
}

//klicka på registered skicka till registered
//klicka på login validate!!
