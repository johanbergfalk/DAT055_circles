import javax.swing.*;

public class LandingPagePanel extends JPanel {

    private String username;

    public LandingPagePanel(MainFrame frame) {

        add(new NavigationBar(frame));
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
