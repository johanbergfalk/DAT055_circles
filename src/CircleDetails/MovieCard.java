package CircleDetails;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;



public class MovieCard extends JPanel {

    public MovieCard() {

        setLayout(new GridLayout(1,10));
        setPreferredSize(new Dimension(650,50));
        setBorder(new LineBorder(Color.black));
        setBackground(Color.gray);
        add(new JLabel("movie\ninfo"));
        add(new JLabel("description"));

    }

}


