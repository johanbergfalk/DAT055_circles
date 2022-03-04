package Circles.Controller;

/**
 * Application mainframe
 * @author Oscar Larsson
 * @version 2022-03-01
 */

import javax.swing.*;
import java.util.function.Function;

public class MainFrame extends JFrame {
    public MainFrame(String title) {
        super.setTitle(title);
    }

    public void navigateTo(Function<MainFrame, JPanel> panelSupplier) {
        setContentPane(panelSupplier.apply(this));
        validate();
    }
}
