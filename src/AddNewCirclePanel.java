
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        sp.setResizeWeight(0.35);
        sp.setEnabled(false);
        sp.setDividerSize(0);

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

        left.add(padding(5,10,5,15));

        left.setSize(new Dimension(400,100));
        left.add(new JLabel("Name of the circle"));
        JTextField tf1 = new JTextField();
        tf1.setPreferredSize(new Dimension(20,150));
        left.add(tf1);

        left.add(padding(15,15,15,15));

        left.add(new JLabel("Start date for circle"));
        DatePicker d1 = new DatePicker();
        left.add((Component) d1.getDatePicker());

        left.add(padding(25,25,25,25));

        left.add(new JLabel("End date for circle"));
        DatePicker d2 = new DatePicker();
        left.add((Component) d2.getDatePicker());

        left.add(padding(15,15,15,15));

        left.add(new JLabel("Describe the circle"));
        left.add(new JTextField());

    }

    private void createRightPanel(JPanel right) {
        right.setPreferredSize(new Dimension(400, 600));
    }

    private JPanel padding(int top, int left, int bottom, int right) {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(top,left,bottom,right));
        return panel;
    }
}
