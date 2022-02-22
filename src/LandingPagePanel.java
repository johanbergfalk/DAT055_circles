import javax.swing.*;

public class LandingPagePanel extends JPanel {
    User user;
    public LandingPagePanel(MainFrame frame, User user) {
        add(new NavigationBar(frame));
        this.user = user;
        System.out.println(user.getUsername());
    }
}
