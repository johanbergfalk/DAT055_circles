
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

public class AddNewCirclePanel extends JPanel {

    private JTextField name;
    private DatePicker startDate;
    private DatePicker endDate;
    private JTextArea description;


    public AddNewCirclePanel(MainFrame frame, User user) {


        setLayout(new BorderLayout());
        setBackground(user.getBackgroundColor());
        setForeground(user.getForegroundColor());
        Color textColor = this.getForeground();
        TitledBorder titledBorder = BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5), "Create new Circle");
        titledBorder.setTitleColor(textColor);
        setBorder(titledBorder);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(this.getBackground());
        createLeftPanel(leftPanel, user);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(this.getBackground());
        createRightPanel(frame, rightPanel, user);

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sp.setResizeWeight(0.5);
        sp.setEnabled(false);
        sp.setDividerSize(1);
        sp.setBackground(this.getBackground());

        sp.add(leftPanel);
        sp.add(rightPanel);
        add(sp);

    }

    private void createLeftPanel(JPanel left, User u) {
        left.setPreferredSize(new Dimension(400, 600));
        left.setBorder(BorderFactory.createEmptyBorder(15,5,5,5));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));


        JLabel title = new JLabel("Create new circle");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(this.getForeground());
        title.setFont(new Font("title.getFont()", Font.PLAIN, 16));
        left.add(title);

        left.add(Box.createRigidArea(new Dimension(15,40)));

        left.setSize(new Dimension(400,100));
        JLabel nameLabel = new JLabel("Name of the circle:");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setForeground(this.getForeground());
        left.add(nameLabel);
        name = new JTextField();
        name.setForeground(this.getForeground());
        name.setBackground(u.getTextFieldColor());
        name.setPreferredSize(new Dimension(346,35));
        name.setMaximumSize(new Dimension(Integer.MAX_VALUE, name.getPreferredSize().height) );
        left.add(name);

        left.add(Box.createRigidArea(new Dimension(15,40)));

        JLabel startLabel = new JLabel("Start date for circle");
        startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startLabel.setForeground(this.getForeground());
        left.add(startLabel);
        startDate = new DatePicker();

        left.add((Component) startDate.getDatePicker());

        left.add(Box.createRigidArea(new Dimension(15,20)));

        JLabel endLabel = new JLabel("End date for circle");
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        endLabel.setForeground(this.getForeground());
        left.add(endLabel);
        endDate = new DatePicker();

        left.add((Component) endDate.getDatePicker());

        left.add(Box.createRigidArea(new Dimension(15,20)));

        JLabel descLabel = new JLabel("Describe the circle");
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descLabel.setForeground(this.getForeground());
        left.add(descLabel);
        description = new JTextArea();
        description.setBackground(u.getTextFieldColor());
        description.setForeground(this.getForeground());
        description.setBorder(new LineBorder(this.getForeground()));
        left.add(description);

        left.add(Box.createRigidArea(new Dimension(15,20)));

    }

    private void createRightPanel(MainFrame frame, JPanel right, User u) {

        right.setPreferredSize(new Dimension(400, 600));
        right.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(this.getBackground());
        titlePanel.setPreferredSize(new Dimension(200, 75));
        titlePanel.setMinimumSize(new Dimension(200, titlePanel.getPreferredSize().height));
        titlePanel.setMaximumSize(new Dimension(200, titlePanel.getPreferredSize().height));
        titlePanel.setLayout(new BorderLayout());
        JLabel title = new JLabel("Add movies to circle");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(this.getForeground());
        title.setFont(new Font("title.getFont()", Font.PLAIN, 16));
        titlePanel.add(title, BorderLayout.CENTER);
        right.add(titlePanel);

        right.add(Box.createRigidArea(new Dimension(15,20)));

        JLabel searchLabel = new JLabel("Search for movie to add");
        searchLabel.setForeground(this.getForeground());
        searchLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        right.add(searchLabel);
        JTextField searchMovie = new JTextField();
        searchMovie.setForeground(this.getForeground());
        searchMovie.setBackground(u.getTextFieldColor());
        searchMovie.setPreferredSize(new Dimension(250,35));
        searchMovie.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchMovie.getPreferredSize().height));
        right.add(searchMovie);

        DualListBox dual = new DualListBox();
        dual.setBackground(this.getBackground());

        JButton search = new JButton("Search");
        search.setAlignmentX(Component.CENTER_ALIGNMENT);
        //search.addActionListener(e -> { dual.addSourceElements(new String[] { Movie.searchForMovieToAdd(searchMovie.getText()) }); });

        search.addActionListener(e -> {
            dual.clearSourceListModel();
            for (String s : Movie.searchForMovies(searchMovie.getText())) {
                dual.addSourceElements(new String[]{s});
            }
        });

        right.add(search);

        right.add(Box.createRigidArea(new Dimension(15,20)));

        right.add(dual);

        right.add(Box.createRigidArea(new Dimension(15,60)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(this.getBackground());

        JButton createCirlceButton = new JButton("Create circle");
        createCirlceButton.addActionListener(e -> { addNewCircle(frame, dual, u);});
        buttonPanel.add(createCirlceButton);

        JButton abort = new JButton("Abort");
        abort.addActionListener(event -> frame.navigateTo(m -> new BrowseCirclesPanel(m, u)));
        buttonPanel.add(abort);

        right.add(buttonPanel);

    }

    /**
     * Method that creates a new circle and adds the circle to the database
     * @param f frame
     * @param d listbox containing chosen movies
     * @param u active user
     */
    private void addNewCircle(MainFrame f, DualListBox d, User u) {

        Date d1 = startDate.getSelectedDate(startDate.getDatePicker());
        Date d2 = endDate.getSelectedDate(endDate.getDatePicker());

        Iterator movies = d.destListIterator();
        Circle c;
        //check that all the fields are filled in
        if(name.getText() != null && u.getUsername() != null && description.getText() != null && startDate.getSelectedDate(startDate.getDatePicker()) != null && endDate.getSelectedDate(endDate.getDatePicker()) != null && movies.hasNext() && d1.before(d2)) {
            c = new Circle(name.getText(), u.getUsername(), description.getText(), startDate.getSelectedDate(startDate.getDatePicker()), endDate.getSelectedDate(endDate.getDatePicker()));
            int circleId;
            int addCircle = DatabaseConn.addCircle(c);
            switch (addCircle) {
                case 1 -> {
                    circleId = DatabaseConn.getCircleID(c);
                    c.setId(circleId);
                    while (movies.hasNext()) {
                        String current = (String) movies.next();

                        Movie m = new Movie(current);
                        if (DatabaseConn.getMovie(m.getId()) == null) {
                            DatabaseConn.addMovie(m);
                        }

                        DatabaseConn.addMovieCircle(c, m);
                    }
                    JOptionPane.showMessageDialog(f, "Circle " + c.getName() + " created!");
                    f.navigateTo(m -> new BrowseCirclesPanel(m, u));
                }
                case 0 -> JOptionPane.showMessageDialog(f, "Circle already exists, choose different name!");
                case -1 -> JOptionPane.showMessageDialog(f, "Please fill in all the fields");
            }
        }
        else {
            JOptionPane.showMessageDialog(f, "Please fill in all the fields, enter correct date and add some movies!");
        }

    }

}
