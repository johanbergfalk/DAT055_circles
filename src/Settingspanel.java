import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        add(new NavigationBar(m, u, 4), BorderLayout.NORTH);
        setBorder(BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5),""));

        Color textColor = u.getForegroundColor();
        setBackground(u.getBackgroundColor());

        JLabel header = new JLabel("Settings");
        header.setForeground(textColor);
        header.setFont(new Font("serif", Font.BOLD, 48));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setVerticalAlignment(SwingConstants.TOP);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6,1,1,10));
        JButton settings = new JButton(u.getDarkMode() ? "Enable Light Mode" : "Enable Dark Mode");
        settings.addActionListener(this);
        buttonPanel.setBackground(u.getBackgroundColor());
        //JButton changepass = new JButton("change password"); //add func change password?
        buttonPanel.add(header);
        buttonPanel.add(settings);
       // buttonPanel.add(changepass);


        JPanel alignPanel = new JPanel();
        JPanel empty1 = new JPanel();
        empty1.setBackground(u.getBackgroundColor());
        JPanel empty2 = new JPanel();
        empty2.setBackground(u.getBackgroundColor());
        alignPanel.setLayout(new BoxLayout(alignPanel,BoxLayout.X_AXIS));
        alignPanel.setBackground(u.getBackgroundColor());
        alignPanel.add(empty1);
        alignPanel.add(buttonPanel);
        alignPanel.add(empty2);
        add(alignPanel);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Enable Light Mode" -> {
                u.setDarkmode(false);
                DatabaseConn.setUserMode(u.getUsername(), u.getDarkMode());
                removeAll();
                draw(u, m);
                validate();
            }
            case "Enable Dark Mode" -> {
                u.setDarkmode(true);
                DatabaseConn.setUserMode(u.getUsername(), u.getDarkMode());
                removeAll();
                draw(u, m);
                validate();
            }
        }
    }
}
