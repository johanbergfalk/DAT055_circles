
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.util.Date;
import java.util.Properties;

import org.jdatepicker.*;
import org.jdatepicker.impl.*;

public class AddNewCirclePanel extends JPanel {

    private JTextField name;
    private DatePicker startDate;
    private DatePicker endDate;
    private JTextArea description;

    public AddNewCirclePanel(JFrame frame, User u) {

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
        name = new JTextField();
        name.setPreferredSize(new Dimension(346,35));
        name.setMaximumSize(new Dimension(Integer.MAX_VALUE, name.getPreferredSize().height) );
        left.add(name);

        left.add(Box.createRigidArea(new Dimension(15,40)));

        left.add(new JLabel("Start date for circle"));
        startDate = new DatePicker();
        left.add((Component) startDate.getDatePicker());

        left.add(Box.createRigidArea(new Dimension(15,20)));

        left.add(new JLabel("End date for circle"));
        endDate = new DatePicker();
        left.add((Component) endDate.getDatePicker());

        left.add(Box.createRigidArea(new Dimension(15,20)));

        left.add(new JLabel("Describe the circle"));
        description = new JTextArea();
        left.add(description);

        left.add(Box.createRigidArea(new Dimension(15,20)));

    }

    private void createRightPanel(JPanel right) {

        right.setPreferredSize(new Dimension(400, 600));
        right.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(200, 75));
        titlePanel.setMinimumSize(new Dimension(200, titlePanel.getPreferredSize().height));
        titlePanel.setMaximumSize(new Dimension(200, titlePanel.getPreferredSize().height));
        titlePanel.setLayout(new BorderLayout());
        JLabel title = new JLabel("Add movies to circle");
        title.setFont(new Font("title.getFont()", Font.PLAIN, 16));
        titlePanel.add(title, BorderLayout.CENTER);
        right.add(titlePanel);

        right.add(Box.createRigidArea(new Dimension(15,20)));

        right.add(new JLabel("Search for movie to add"));
        JTextField searchMovie = new JTextField();
        searchMovie.setPreferredSize(new Dimension(250,35));
        searchMovie.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchMovie.getPreferredSize().height));
        right.add(searchMovie);

        DualListBox dual = new DualListBox();

        JButton search = new JButton("Search");
        search.addActionListener(e -> { dual.addSourceElements(new String[] { Movie.searchForMovieToAdd(searchMovie.getText()) }); });
        right.add(search);

        right.add(Box.createRigidArea(new Dimension(15,20)));
        dual.addSourceElements(new String[] { "One", "Two", "Three" });

        right.add(dual);

        right.add(Box.createRigidArea(new Dimension(15,60)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton createCirlceButton = new JButton("Create circle");
        //createCirlceButton.addActionListener(e -> new Circle(name.getText(), "User", description.getText()), startDate.getSelectedDate(startDate.getDatePicker()), endDate.getSelectedDate(endDate.getDatePicker()));
        buttonPanel.add(createCirlceButton);

        JButton abort = new JButton("Abort");
        buttonPanel.add(abort);

        right.add(buttonPanel);

    }


}
