import javax.swing.*;

public class LandingPagePanel extends JPanel {

    private User user;

    public LandingPagePanel(MainFrame frame, User user) {

        add(new NavigationBar(frame, user));

    }
}
