import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class CircleCard extends JPanel {

    private Circle circle;

    public CircleCard(Circle i, MainFrame frame){

        this.circle = i;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(frame.getWidth(), 100));
        setBorder(new LineBorder(Color.GRAY));

        //Left side of the card
        JPanel left = new JPanel();
        createLeft(circle, left);

        //Right side of the card
        JPanel right = new JPanel();
        createRight(circle, right, frame);

        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        pane.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        pane.setResizeWeight(0.5);
        pane.setDividerSize(0);
        add(pane);




    }

//----Methods-----------------------------------------------------------

    private void createLeft(Circle i, JPanel left){
        left.setLayout(new GridLayout(2,0));

        JPanel leftTop = new JPanel();
        leftTop.setLayout(new GridLayout(2,0));
        JPanel title =  new JPanel();
        title.setLayout(new GridLayout(1,2));
        title.add(new JLabel(i.getName()));
        title.setBackground(Color.LIGHT_GRAY);
        leftTop.add(title);
        JPanel description = new JPanel();
        description.setLayout(new GridLayout(2,0));
        description.add(new JLabel("Description:"));
        description.add(new JLabel(i.getDescription()));
        leftTop.add(description);
        left.add(leftTop);

        DateFormat df = new SimpleDateFormat("yy-MM-dd");
        String dateFrom = df.format(i.getStartTime());
        String dateTo = df.format(i.getStopTime());

        JPanel leftBottom = new JPanel();
        leftBottom.setLayout(new GridLayout(4,0));
        leftBottom.add(new JLabel(""));
        leftBottom.add(new JLabel("Running from:      " + "20" + dateFrom));
        leftBottom.add(new JLabel("To:                           " + "20" + dateTo));
        leftBottom.add(new JLabel("Score:                     " + i.getScore()));
        left.add(leftBottom);

    }

    private void createRight(Circle i, JPanel right, MainFrame frame){
        right.setLayout(new GridLayout(2,0));

        JPanel rightTop = new JPanel();
        rightTop.setLayout(new BoxLayout(rightTop, BoxLayout.Y_AXIS));
        JPanel members = new JPanel();
        members.add(new JLabel("Members:"));
        rightTop.add(members);
        for(String k : i.getMembers()){
            JPanel t = new JPanel();
            t.add(new JLabel(k));
            rightTop.add(t);
        }
        JScrollPane scrollMembers = new JScrollPane(rightTop ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel rightBottom = new JPanel();
        rightBottom.setLayout(new GridLayout(4,0));
        rightBottom.add(new JLabel(""));
        rightBottom.add(new JLabel(""));
        rightBottom.add(new JLabel(""));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0,2));
        buttonPanel.add(new JLabel(""));
        JButton detailsButton = new JButton("Circle details");
        detailsButton.addActionListener(event -> frame.navigateTo(CircleDetailsPanel :: new));
        buttonPanel.add(detailsButton);
        rightBottom.add(buttonPanel);

        right.add(scrollMembers);
        right.add(rightBottom);

    }

}
