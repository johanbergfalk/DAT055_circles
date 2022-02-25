import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settingspanel extends JPanel implements ActionListener {
    private MainFrame m;
    private User u;
    Settings s;

    public Settingspanel(MainFrame m, User u) {
        this.m = m;
        this.u = u;
        draw(u, m);
    }

    private void draw(User u, MainFrame m) {
        setLayout(new BorderLayout());
        add(new NavigationBar(m, u, 4), BorderLayout.NORTH);
        setBorder(BorderFactory.createTitledBorder(new EmptyBorder(10, 5, 5, 5), " "));

        Color textColor = u.getForegroundColor();
        setBackground(u.getBackgroundColor());

        JLabel header = new JLabel("Settings");
        header.setForeground(textColor);
        header.setFont(new Font("serif", Font.BOLD, 48));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setVerticalAlignment(SwingConstants.TOP);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 1, 10));
        JButton settings = new JButton(u.getDarkMode() ? "Enable Light Mode" : "Enable Dark Mode");
        settings.addActionListener(this);
        buttonPanel.setBackground(u.getBackgroundColor());
        JButton changepass = new JButton("change password"); //add func change password?
        changepass.addActionListener(this);
        buttonPanel.add(header);
        buttonPanel.add(settings);
        buttonPanel.add(changepass);

        JPanel alignPanel = new JPanel();
        JPanel empty1 = new JPanel();
        empty1.setBackground(u.getBackgroundColor());
        JPanel empty2 = new JPanel();
        empty2.setBackground(u.getBackgroundColor());
        alignPanel.setLayout(new BoxLayout(alignPanel, BoxLayout.X_AXIS));
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
            case "change password" -> {
                draw();
            }
        }
    }

    public void draw(){
        JLabel oldpass = new JLabel("Old Password:");
        JLabel newpass = new JLabel("New password:");
        JLabel repnewpass = new JLabel("Repeat password:");

        JPasswordField oldField = new JPasswordField();
        oldField.setColumns(15);
        JPasswordField newpassField = new JPasswordField();
        newpassField.setColumns(15);
        JPasswordField repnewpassField = new JPasswordField();
        repnewpassField.setColumns(15);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(3, 1, 10, 10));
        myPanel.add(oldpass);
        myPanel.add(oldField);
        myPanel.add(newpass);
        myPanel.add(newpassField);
        myPanel.add(repnewpass);
        myPanel.add(repnewpassField);
        int result = JOptionPane.showConfirmDialog(m, myPanel, "Change password", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            s = new Settings(oldField.getPassword(), newpassField.getPassword(), u.getUsername(), repnewpassField.getPassword());
            try {
                Settings.Result rs = s.changepass();
                switch (rs) {
                    case OK -> {
                        JOptionPane.showMessageDialog(m, "Successful password change!");
                    }
                    case EMPTY_FIELDS -> {
                        JOptionPane.showMessageDialog(m, "Can't leave fields empty!");
                        draw();
                    }
                    case NO_SUCH_USER, CANT_UPDATE -> {
                        JOptionPane.showMessageDialog(m, "Something went wrong try again!");
                        draw();
                    }
                    case PASSWORD_MATCH_ERROR -> {
                        JOptionPane.showMessageDialog(m, "Password match error, new passwords don't match!");
                        draw();
                    }
                    case OLD_PASSORD_ERROR -> {
                        JOptionPane.showMessageDialog(m, "That is not your old pass (nice try sneeky devil!) try again!");
                        draw();
                    }
                }
            } catch (NullPointerException exception) {
                exception.printStackTrace();
            }
        }
    }
}

