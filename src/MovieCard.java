import javax.swing.*;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;


public class MovieCard extends JPanel {

    public MovieCard(Movie m) {

        setLayout(new BorderLayout());
        setSize(new Dimension(650, 150));
        setBorder(new LineBorder(Color.GRAY));

        JPanel left = new JPanel();
        left.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        left.setLayout(new BorderLayout());
        left.add(new JButton(m.getPosterURL()), BorderLayout.WEST);
        left.add(new JLabel(m.getName()), BorderLayout.NORTH);
        left.add(new JLabel(String.valueOf(m.getYear())), BorderLayout.CENTER);
        left.add(new JLabel(m.getDescription()), BorderLayout.PAGE_END);

        //if(circle == active)
        JPanel right = circleActive();

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sp.setResizeWeight(0.25);
        sp.setEnabled(false);
        sp.setDividerSize(0);

        sp.add(left);
        sp.add(right);
        add(sp, BorderLayout.CENTER);

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

}


