import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class MainFrame extends JFrame {
    public MainFrame(String title){
        super.setTitle(title);

    }
    public static void main(String[] args)  {
        MainFrame m = new MainFrame("Circles");
        m.setMinimumSize(new Dimension(800,600));
        m.setLocation(100,100);
        m.setDefaultCloseOperation(m.EXIT_ON_CLOSE);
        m.setContentPane(new Loginpanel(m));
        m.setVisible(true);
    }
    public void navigateTo(Function<MainFrame, JPanel> panelSupplier) {
        setContentPane(panelSupplier.apply(this));
        validate();
    }

}
