
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Date;
import java.util.Properties;

import org.jdatepicker.*;
import org.jdatepicker.impl.*;

public class AddNewCirclePanel extends JPanel {

    private String name;
    private Date startDate;
    private Date endDate;
    private String description;

    public AddNewCirclePanel(JFrame frame) {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel leftPanel = new JPanel();
        createLeftPanel(leftPanel);

        JPanel rightPanel = new JPanel();
        createRightPanel(rightPanel);

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sp.setResizeWeight(0.5);
        sp.setEnabled(false);
        sp.setDividerSize(1);
        sp.setBackground(Color.gray);

        sp.add(leftPanel);
        sp.add(rightPanel);
        add(sp);

    }

    private void createLeftPanel(JPanel left) {
        left.setPreferredSize(new Dimension(400, 600));
        left.setBorder(BorderFactory.createEmptyBorder(15,5,5,5));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Create new circle");
        title.setFont(new Font("title.getFont()", Font.PLAIN, 16));
        left.add(title);

        left.add(Box.createRigidArea(new Dimension(15,40)));

        left.setSize(new Dimension(400,100));
        left.add(new JLabel("Name of the circle"));
        JTextField tf1 = new JTextField();
        tf1.setPreferredSize(new Dimension(346,35));
        tf1.setMaximumSize(new Dimension(Integer.MAX_VALUE, tf1.getPreferredSize().height) );
        left.add(tf1);

        left.add(Box.createRigidArea(new Dimension(15,40)));

        left.add(new JLabel("Start date for circle"));
        DatePicker d1 = new DatePicker();
        left.add((Component) d1.getDatePicker());

        left.add(Box.createRigidArea(new Dimension(15,20)));

        left.add(new JLabel("End date for circle"));
        DatePicker d2 = new DatePicker();
        left.add((Component) d2.getDatePicker());

        left.add(Box.createRigidArea(new Dimension(15,20)));

        left.add(new JLabel("Describe the circle"));
        left.add(new JTextArea());

        left.add(Box.createRigidArea(new Dimension(15,20)));

    }

    private void createRightPanel(JPanel right) {

        right.setPreferredSize(new Dimension(400, 600));
        right.setBorder(BorderFactory.createEmptyBorder(15,5,5,5));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        right.add(Box.createRigidArea(new Dimension(15,40)));

        JLabel title = new JLabel("Add movies to circle");
        title.setFont(new Font("title.getFont()", Font.PLAIN, 16));
        right.add(title);

        right.add(Box.createRigidArea(new Dimension(15,20)));

        right.add(new JLabel("Search for movie to add"));
        JTextField searchMovie = new JTextField();
        searchMovie.setPreferredSize(new Dimension(250,35));
        searchMovie.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchMovie.getPreferredSize().height));
        right.add(searchMovie);

        right.add(new JButton("Search"));

        right.add(Box.createRigidArea(new Dimension(15,20)));

        DualListBox dual = new DualListBox();
        dual.addSourceElements(new String[] { "One", "Two", "Three" });

        right.add(dual);

        right.add(Box.createRigidArea(new Dimension(15,60)));

    }

}
