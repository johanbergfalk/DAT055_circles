import javax.imageio.ImageIO;
import javax.swing.*;

import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;


public class MovieCard extends JPanel {

    public MovieCard(Movie m) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 150));
        setBorder(new LineBorder(Color.YELLOW));

        JPanel left = new JPanel();
        left.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        left.setLayout(new BorderLayout());
        left.add(getPoster(m), BorderLayout.WEST);
        left.add(new JLabel(m.getName()), BorderLayout.NORTH);

        //text area for the description of a movie
        JTextArea ta = new JTextArea();
        ta.setSize(new Dimension(180,60));
        ta.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setText(m.getDescription());
        ta.setBackground(this.getBackground());
        left.add(ta, BorderLayout.PAGE_END);
        JScrollPane sp1 = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp1.setBorder(BorderFactory.createEmptyBorder());
        left.add(sp1);

        left.add(new JLabel("Release date: " + m.getYear()), BorderLayout.PAGE_END);


        //if(circle == active)
        JPanel right = circleActive();

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sp.setResizeWeight(0.25);
        sp.setEnabled(false);
        sp.setDividerSize(0);

        sp.add(left);
        sp.add(right);
        add(sp);

    }

    private JPanel circleActive() {

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.add(new JLabel("Lämna en recension på filmen"));
        right.add(new JTextField());
        right.add(new JLabel("Vad ger du filmen för betyg?"));
        right.add(new JTextField());
        right.setBackground(Color.lightGray);
        right.add(new JButton("Submit"));
        return right;
    }

    private JPanel circleEnded() {
        JPanel right = new JPanel();

        return right;
    }

    private JLabel getPoster(Movie m) {

        try {
            URL posterUrl = new URL(m.getPosterURL());
            ImageIcon icon = new ImageIcon(posterUrl);
            Image scaleImage = icon.getImage().getScaledInstance(72, 108,Image.SCALE_DEFAULT);
            JLabel label = new JLabel(new ImageIcon(scaleImage));
            return label;

        } catch (Exception e) {
            e.printStackTrace();
            return new JLabel("hej");
        }
    }
}


