import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;

public class CircleDetailsPanel extends JPanel {

    LinkedList<Movie> moviesInCircle = new LinkedList<>();

    Movie movie1 = new Movie("film1", 1, "description or genre", 1997, "url");
    Movie movie2 = new Movie("film2", 2, "description or genre", 2004, "url");
    Movie movie3 = new Movie("film2", 3, "description or genre", 2015, "url");

    public CircleDetailsPanel(JFrame frame) {

        moviesInCircle.add(movie1);
        moviesInCircle.add(movie2);
        moviesInCircle.add(movie3);

        setBorder(BorderFactory.createTitledBorder("Oscars romance"));
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setPreferredSize(new Dimension(frame.getWidth() - 20, frame.getHeight() - 65));

        for(Movie m : moviesInCircle) {
            content.add(new MovieCard(m));
        }

        JScrollPane scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane);

    }

}
