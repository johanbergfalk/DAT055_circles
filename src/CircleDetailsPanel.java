import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

public class CircleDetailsPanel extends JPanel {

    LinkedList<Movie> moviesInCircle = new LinkedList<>();

    public CircleDetailsPanel(JFrame frame) throws IOException {

        Movie movie1 = new Movie("die hard 2");
        Movie movie2 = new Movie("forrest gump");
        Movie movie3 = new Movie("ett päron till farsa");
        Movie movie4 = new Movie("catch me if you can");
        Movie movie5 = new Movie("die hard 3");
        Movie movie6 = new Movie("pulp fiction");
        Movie movie7 = new Movie("titanic");
        Movie movie8 = new Movie("dirty dancing");
        Movie movie9 = new Movie("grease");

        moviesInCircle.add(movie1);
        moviesInCircle.add(movie2);
        moviesInCircle.add(movie3);
        moviesInCircle.add(movie4);
        moviesInCircle.add(movie5);
        moviesInCircle.add(movie6);
        moviesInCircle.add(movie7);
        moviesInCircle.add(movie8);
        moviesInCircle.add(movie9);

        JPanel content = new JPanel();
        setBorder(BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5), "Oscars Romance"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        for(Movie m : moviesInCircle) {
            content.add(new MovieCard(m));
        }

        JScrollPane scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane);

    }

}
