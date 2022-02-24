import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settingspanel extends JPanel implements ActionListener {
    private MainFrame m;
    private User u;

    public Settingspanel(MainFrame m, User u) {
        this.m = m;
        this.u = u;
        draw(u,m);
    }

    private void draw(User u, MainFrame m){

        setLayout(new BorderLayout());
        add(new NavigationBar(m, u, 1), BorderLayout.NORTH);
        setBorder(BorderFactory.createTitledBorder(new EmptyBorder(10, 5, 5, 5), " "));

        Color textColor = u.getForegroundColor();
        setBackground(u.getBackgroundColor());
        setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));

        JPanel paneltest = new JPanel();
        paneltest.setBorder(new LineBorder(Color.BLUE));
        paneltest.setLayout(new BoxLayout(paneltest, BoxLayout.Y_AXIS));
        paneltest.setBackground(u.getBackgroundColor());

        JLabel header = new JLabel("Settings");
        header.setForeground(textColor);
        header.setFont(new Font("serif", Font.BOLD, 48));


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton settings = new JButton(u.getDarkMode() ? "Enable Light Mode" : "Enable Dark Mode");
        settings.addActionListener(this);
        buttonPanel.setBackground(u.getBackgroundColor());
        buttonPanel.add(header);
        buttonPanel.add(settings);
        paneltest.add(buttonPanel);

        add(paneltest, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "Enable Light Mode": {
                u.setDarkmode(false);
                removeAll();
                draw(u, m);
                validate();
                break;
            }
            case "Enable Dark Mode": {
                u.setDarkmode(true);
                removeAll();
                draw(u, m);
                validate();
                break;
            }
        }
    }
}
