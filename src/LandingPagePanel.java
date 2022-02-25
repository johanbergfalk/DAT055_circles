import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LandingPagePanel extends JPanel {

    public LandingPagePanel(MainFrame frame, User user) {

        setLayout(new BorderLayout());
        setBackground(user.getBackgroundColor());
        add(new NavigationBar(frame, user, 1), BorderLayout.NORTH);
        setBorder(BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5), " "));

        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("LandingImage.jpg");
            BufferedImage myPicture = ImageIO.read(input);
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            add(picLabel, BorderLayout.CENTER);
        } catch (IOException e){}


    }
}
