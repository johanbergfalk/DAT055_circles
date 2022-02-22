
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;


public class MovieCard extends JPanel {

    public MovieCard(Movie m) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 150));
        setBorder(new LineBorder(Color.YELLOW));

        JPanel leftPanel = new JPanel();
        //new thread to allow dynamic loading of API-data
        new Thread(() -> {
            createLeftPanel(leftPanel, m);
            validate();
        }).start();

        JPanel rightPanel = new JPanel();
        createRightPanel(rightPanel, m);

        //adds the left and right panel into a splitpane
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sp.setResizeWeight(0.25);
        sp.setEnabled(false);
        sp.setDividerSize(0);

        sp.add(leftPanel);
        sp.add(rightPanel);
        add(sp);

    }

    private void createLeftPanel(JPanel left, Movie m) {
        left.setPreferredSize(new Dimension(350, 150));
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
    }

    private void createRightPanel(JPanel right, Movie m) {
        //IF CIRCLE ACTIVE
        right.setPreferredSize(new Dimension(250, 150));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.add(new JLabel("Lämna en recension på filmen"));
        right.add(new JTextField());
        right.add(new JLabel("Vad ger du filmen för betyg?"));
        right.add(new JTextField());
        right.setBackground(Color.lightGray);
        right.add(new JButton("Submit"));

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


