package CircleDetails;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CircleDetailsPanel extends JPanel {

    public CircleDetailsPanel(MainFrame frame) {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setPreferredSize(new Dimension(650,500));

        content.add(new MovieCard());
        content.add(new MovieCard());
        content.add(new MovieCard());
        content.add(new MovieCard());
        content.add(new MovieCard());
        content.add(new MovieCard());

        JScrollPane scrollPane = new JScrollPane(content ,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        add(scrollPane);

    }

}
