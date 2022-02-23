import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CircleCard extends JPanel {

    private Circle circle;

    public CircleCard(MainFrame frame, User user, Circle i){

        this.circle = i;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800,150));
        setBorder(new LineBorder(Color.YELLOW));

        //Left side of the card
        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(this.getWidth()/2,150));
        createLeft(circle, left);

        //Right side of the card
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(this.getWidth()/2,150));
        new Thread(() -> {
            createRight(circle, right, frame, user);
            validate();
        }).start();

        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        pane.setBorder(BorderFactory.createTitledBorder("Created by: " + circle.getCreator()));
        pane.setResizeWeight(0.5);
        pane.setEnabled(false);
        pane.setDividerSize(0);
        pane.add(left);
        pane.add(right);
        add(pane);
    }

//----Methods-----------------------------------------------------------

    private void createLeft(Circle i, JPanel left){
        left.setLayout(new GridLayout(2,0));

        JPanel leftTop = new JPanel();
        leftTop.setPreferredSize(new Dimension(left.getWidth(), this.getHeight()/2));
        leftTop.setLayout(new GridLayout(2,0));
        JPanel title =  new JPanel();
        title.setLayout(new GridLayout(1,2));
        title.add(new JLabel(i.getName()));
        title.setBackground(Color.LIGHT_GRAY);
        leftTop.add(title);
        JPanel description = new JPanel();
        description.setBackground(Color.LIGHT_GRAY);
        description.setLayout(new GridLayout(2,0));
        description.add(new JLabel("Description:"));
        description.add(new JLabel(i.getDescription()));
        leftTop.add(description);
        left.add(leftTop);

        DateFormat df = new SimpleDateFormat("yy-MM-dd");
        String dateFrom = df.format(i.getStartTime());
        String dateTo = df.format(i.getStopTime());

        JPanel leftBottom = new JPanel();
        leftBottom.setPreferredSize(new Dimension(left.getWidth(), left.getHeight()/2));
        leftBottom.setLayout(new GridLayout(4,0));
        leftBottom.add(new JLabel(""));
        leftBottom.add(new JLabel("Running from:      " + "20" + dateFrom));
        leftBottom.add(new JLabel("To:                           " + "20" + dateTo));
        leftBottom.add(new JLabel("Score:                     " + i.getScore()));
        left.add(leftBottom);


    }

    private void createRight(Circle i, JPanel right, MainFrame frame, User u){
        right.setLayout(new GridLayout(2,0));

        JPanel rightTop = new JPanel();
        rightTop.setPreferredSize(new Dimension(right.getWidth(), this.getHeight()/2));
        rightTop.setLayout(new BoxLayout(rightTop, BoxLayout.Y_AXIS));
        JPanel members = new JPanel();
        members.setBackground(Color.LIGHT_GRAY);
        members.add(new JLabel("Members:"));
        rightTop.add(members);
        for(String k : i.getMembers()){
            JPanel t = new JPanel();
            t.setBackground(Color.LIGHT_GRAY);
            t.add(new JLabel(k));
            rightTop.add(t);
        }
        JScrollPane scrollMembers = new JScrollPane(rightTop ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel rightBottom = new JPanel();
        rightBottom.setPreferredSize(new Dimension(right.getWidth(), right.getHeight()));
        rightBottom.setLayout(new GridLayout(1,2));
        JPanel rightPoster = new JPanel();
        JPanel rightRight = new JPanel();

        //TODO metod som hämtar namnet på första filmen i cirkeln
        Movie m = new Movie("Napoleon Dynamite");

        JLabel poster = getPoster(m);

        rightPoster.add(poster);
        rightBottom.add(rightPoster);

        rightRight.setLayout(new GridLayout(4,0));
        rightRight.add(new JLabel(""));
        rightRight.add(new JLabel(""));
        rightRight.add(new JLabel(""));


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JLabel(""));
        JButton detailsButton = new JButton("Circle details");
        detailsButton.addActionListener(event -> frame.navigateTo(k -> new CircleDetailsPanel(k, u, circle)));
        buttonPanel.add(detailsButton);
        rightRight.add(buttonPanel);
        rightBottom.add(rightRight);

        right.add(scrollMembers);
        right.add(rightBottom);


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
