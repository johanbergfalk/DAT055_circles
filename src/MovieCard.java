import javax.swing.*;

import javax.swing.border.LineBorder;
import java.awt.*;


public class MovieCard extends JPanel {

    public MovieCard() {

        setLayout(new GridLayout(1,2));
        setBackground(Color.lightGray);
        setBorder(new LineBorder(Color.YELLOW)); // TODO REMOVE
        setPreferredSize(new Dimension(650,50));

        JPanel leftSide = new JPanel();
        leftSide.setLayout(new BorderLayout());
        leftSide.setBackground(Color.gray);
        leftSide.add(new JLabel("movie info"), BorderLayout.WEST);
        leftSide.add(new JLabel("movie title"), BorderLayout.EAST);

        JPanel rightSide = new JPanel();
        rightSide.setLayout(new GridLayout(4,1));
        rightSide.setBackground(Color.lightGray);
        rightSide.add(new JLabel("Vad tyckte du om filmen?"));
        rightSide.add(new JTextField());
        rightSide.add(new JLabel("Vad får filmen för betyg?"));
        rightSide.add(new JTextField());


        add(leftSide);
        add(rightSide);


    }

}


