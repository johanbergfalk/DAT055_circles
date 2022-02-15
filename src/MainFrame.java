import javax.swing.*;
import java.awt.*;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.function.Function;
public class MainFrame extends JFrame {

    public MainFrame(String titel) throws HeadlessException {
        super(titel);

    }

    public static void main(String[] args) throws SQLException, URISyntaxException {

    }

    public void navigateTo(Function<MainFrame, JPanel> panelSupplier) {
        setContentPane(panelSupplier.apply(this));
        validate();
    }
}
