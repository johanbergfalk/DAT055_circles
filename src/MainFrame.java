import javax.swing.*;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.function.Function;

public class MainFrame extends JFrame {
    public MainFrame(String title){
        super.setTitle(title);

    }
    public static void main(String[] args) throws SQLException, URISyntaxException {
        MainFrame m = new MainFrame("Circles");
        new Login(m);
    }
    public void navigateTo(Function<MainFrame, JPanel> panelSupplier) {
        setContentPane(panelSupplier.apply(this));
        validate();
    }
}
