
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;


public class MovieCard extends JPanel {
    private User user;
    public MovieCard(Movie m, User u) {
        this.user = u;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 150));
        setBorder(new LineBorder(u.getForegroundColor()));
        setBackground(u.getCardColor());
        setForeground(u.getForegroundColor());

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(this.getBackground());
        //new thread to allow dynamic loading of API-data
        new Thread(() -> {
            createLeftPanel(leftPanel, m);
            validate();
        }).start();

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(this.getBackground());
        createRightPanel(rightPanel, m);

        //adds the left and right panel into a splitpane
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        sp.setResizeWeight(0.25);
        sp.setEnabled(false);
        sp.setDividerSize(0);
        sp.setBackground(this.getBackground());


        sp.setLeftComponent(leftPanel);
        sp.setRightComponent(rightPanel);
        add(sp);

    }

    private void createLeftPanel(JPanel left, Movie m) {
        left.setPreferredSize(new Dimension(350, 150));
        left.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        left.setLayout(new BorderLayout());
        left.add(getPoster(m), BorderLayout.WEST);
        JLabel title = new JLabel(m.getName());
        title.setForeground(this.getForeground());
        left.add(title, BorderLayout.NORTH);

        //text area for the description of a movie
        JTextArea ta = new JTextArea();
        ta.setBackground(this.getBackground());
        ta.setForeground(this.getForeground());
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
        JLabel releaseRate = new JLabel("Release date: " + m.getYear() + ".            Rate within XXX days");
        releaseRate.setForeground(this.getForeground());
        left.add(releaseRate, BorderLayout.SOUTH);
    }

    private void createRightPanel(JPanel right, Movie m) {
        //IF CIRCLE ACTIVE
        right.setPreferredSize(new Dimension(250, 150));
        right.setLayout(new BorderLayout());


        JPanel contents = new JPanel();
        contents.setBackground(this.getBackground());
        contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
        //contents.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel review = new JLabel("Leave a review");
        review.setAlignmentX(Component.CENTER_ALIGNMENT);
        review.setForeground(this.getForeground());
        contents.add(review);

        JTextArea input = new JTextArea();
        input.setBorder(new LineBorder(this.getForeground()));
        input.setBackground(user.getTextFieldColor());
        input.setForeground(this.getForeground());

        input.setText("Write your review here...");
        input.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(input.getText().trim().equals("Write your review here...")) {
                    input.setText("");
                }
            }
            public void focusLost(FocusEvent e) {
                if(input.getText().trim().equals("")) {
                    input.setText("Write your review here...");
                }
            }
        });

        input.setLineWrap(true);
        input.setWrapStyleWord(true);

        contents.add(new JScrollPane(input));

        JLabel rate = new JLabel("Rate");
        rate.setAlignmentX(Component.CENTER_ALIGNMENT);
        rate.setBackground(this.getBackground());
        rate.setForeground(this.getForeground());
        contents.add(rate);

        JPanel sliderPanel = new JPanel();
        sliderPanel.setBackground(this.getBackground());
        sliderPanel.setForeground(this.getForeground());
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));

        //swing related to slider
        JSlider slider = new JSlider(0,10, 1);
        slider.setValue(0);
        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBackground(this.getBackground());
        slider.setForeground(this.getForeground());
        slider.setOpaque(false);
        sliderPanel.add(slider);

        //adds padding between slider and label
        sliderPanel.add(Box.createRigidArea(new Dimension(5,5)));

        //swing related to label displaying the value of slider
        JLabel sliderValue = new JLabel(String.valueOf(slider.getValue()));
        sliderValue.setBackground(this.getBackground());
        sliderValue.setForeground(this.getForeground());
        slider.addChangeListener(e -> sliderValue.setText(String.valueOf(slider.getValue())));
        sliderPanel.add(sliderValue);
        sliderPanel.add(Box.createRigidArea(new Dimension(10,5)));
        contents.add(sliderPanel);

        //TODO Om input.gettext() == Write your comment here... så har man inte skrivit något. Kolla med en if typ
        JButton submit = new JButton("Submit");
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        contents.add(submit);

        right.add(contents);
        //IF CIRCLE DATE PASSED
        //TODO SHOW RATING AND COMMENTS

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
            return new JLabel("No image");
        }
    }
}


