import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LandingPagePanel extends JPanel {

    private User user;

    public LandingPagePanel(MainFrame frame, User user) {

        add(new NavigationBar(frame, user, 1));
        setBorder(BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5), ""));

    }
}
