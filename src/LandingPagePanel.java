import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LandingPagePanel extends JPanel {

    public LandingPagePanel(MainFrame frame, User user) {

        setLayout(new BorderLayout());
        add(new NavigationBar(frame, user, 1), BorderLayout.NORTH);
        setBorder(BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5), " "));

        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        try {
            BufferedImage myPicture = ImageIO.read(new File("LandingImage.jpg"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            add(picLabel, BorderLayout.CENTER);
        } catch (IOException e){}


    }
}
