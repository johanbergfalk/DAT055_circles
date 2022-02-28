import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CircleCard extends JPanel {

    private final Circle circle;

    public CircleCard(MainFrame frame, User user, Circle i){

        this.circle = i;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(780,150));
        setMaximumSize(new Dimension(780,150));
        setMinimumSize(new Dimension(780,150));
        setBorder(new LineBorder(Color.YELLOW));

        //Left side of the card
        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(390,150));
        left.setMaximumSize(new Dimension(390,150));
        left.setMinimumSize(new Dimension(390,150));

        createLeft(circle, left);

        //Right side of the card
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(390,150));
        right.setMaximumSize(new Dimension(390,150));
        right.setMinimumSize(new Dimension(390,150));

        new Thread(() -> {
            createRight(circle, right, frame, user);
            validate();
        }).start();

        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        pane.setResizeWeight(0.5);
        pane.setEnabled(false);
        pane.setDividerSize(0);
        pane.add(left);
        pane.add(right);
        add(pane);
    }

//----Methods-----------------------------------------------------------

    private void createLeft(Circle i, JPanel left){
        left.setLayout(new GridLayout(3,0));

        JPanel title =  new JPanel();
        JLabel name = new JLabel(i.getName(), JLabel.LEFT);
        name.setFont(new Font("Arial", Font.BOLD, 15));
        title.add(name);
        left.add(title);

        //Description
        JTextArea description = new JTextArea();
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setText(i.getDescription());
        description.setBackground(this.getBackground());
        JScrollPane scrollPane = new JScrollPane(description, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        left.add(scrollPane);

        MovieDates days = new MovieDates(i.getStartTime(), i.getStopTime());

        JPanel leftBottom = new JPanel();
        leftBottom.setLayout(new GridLayout(4,0));
        leftBottom.add(new JLabel(""));
        leftBottom.add(new JLabel("Running from:      " + days.getLocalStart()));
        leftBottom.add(new JLabel("To:                           " + days.getLocalEnd()));
        leftBottom.add(new JLabel("Score:                     " + i.getScore()));
        left.add(leftBottom);

    }

    private void createRight(Circle i, JPanel right, MainFrame frame, User u){
        right.setLayout(new GridLayout(0,2));

        //TODO metod som hämtar namnet på första filmen i cirkeln
        Movie m = new Movie("Napoleon Dynamite");

        JLabel poster = getPoster(m);
        right.add(poster);


        JPanel membersAndButton = new JPanel();
        membersAndButton.setLayout(new GridLayout(2,0));

        //Members
        JTextArea members = new JTextArea();
        members.setEditable(false);
        members.setLineWrap(true);
        members.setWrapStyleWord(true);
        String mem = "Members: \n";
        for(String p : i.getMembers()){
            mem += p + "\n";
        }
        members.setText(mem);
        members.setBackground(this.getBackground());
        JScrollPane scrollPane = new JScrollPane(members, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        membersAndButton.add(scrollPane);


        //Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,0));
        JPanel temp = new JPanel();
        buttonPanel.add(temp.add(new JLabel("")));
        JPanel temp2 = new JPanel();
        buttonPanel.add(temp2.add(new JLabel("Created by: " + i.getCreator())));
        JButton detailsButton = new JButton("Circle details");
        detailsButton.addActionListener(event -> frame.navigateTo(k -> new CircleDetailsPanel(k, u, circle)));
        buttonPanel.add(detailsButton);
        membersAndButton.add(buttonPanel);
        right.add(membersAndButton);

    }
    private JLabel getPoster(Movie m) {

            try {
                URL posterUrl = new URL(m.getPosterURL());
                ImageIcon icon = new ImageIcon(posterUrl);
                Image scaleImage = icon.getImage().getScaledInstance(72, 108, Image.SCALE_DEFAULT);
                return new JLabel(new ImageIcon(scaleImage));

            } catch(
            Exception e)

            {
                e.printStackTrace();
                return new JLabel("No image");
            }

    }
}
