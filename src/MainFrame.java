import javax.swing.*;
import java.awt.*;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class MainFrame extends JFrame{

public MainFrame(String title){
    this.setTitle(title);
}
    public static void main(String[] args) throws SQLException, URISyntaxException {
        new loginControl();
    }
}
