import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.LinkedList;

public class CircleDetailsPanel extends JPanel {

    LinkedList<Movie> moviesInCircle;

    public CircleDetailsPanel(MainFrame frame, User user, Circle c) {

        setLayout(new BorderLayout());
        setBackground(user.getBackgroundColor());
        Color textColor = user.getForegroundColor();
        add(new NavigationBar(frame, user, 1), BorderLayout.NORTH);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5), "Circle title goes here");
        titledBorder.setTitleColor(textColor);
        setBorder(titledBorder);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        moviesInCircle = DatabaseConn.getCircleMovies(c);

        if(moviesInCircle != null) {
            for(Movie m : moviesInCircle) {
                content.add(new MovieCard(m));
            }
        }
        else {
            content.add(new JLabel("No movies has been added to this circle yet"));
        }

        JScrollPane scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);

    }

}
