package Circles.Controller;

import Circles.Model.Circle;
import Circles.Model.DatabaseConn;
import Circles.Model.Settings;
import Circles.Model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *class for handling settings control function and swing design for the panel
 *@author Oscar
 *@version 2022-03-02
 */
public class SettingsPanel extends JPanel implements ActionListener {
    private MainFrame m;
    private User u;
    private Settings s;

    /**
     * Constructor which sets the instance variables and will call on private func draw for the swingdesign of the settingspanel.
     * @param m current Jframe
     * @param u user object
     */
    public SettingsPanel(MainFrame m, User u) {
        this.m = m;
        this.u = u;
        draw(u, m);
    }
    private void draw(User u, MainFrame m) {
        setLayout(new BorderLayout());
        Circle temp = new Circle();
        add(new NavigationBar(m, u, 4, temp), BorderLayout.NORTH);
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
        JButton changepass = new JButton("Change password");
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

    /**
     Function which listen when an event occurs, in our case we listen if buttons are pressed and take certain action.
     in case button Enable Light Mode is pressed: we set darkmode false and update the panel.
     in case button Enable Dark Mode is pressed: we set darkmode true and update the panel.
     in case button Change password: function draw is called upon to draw change password panel.
     * @param e
     */
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
            case "Change password" -> {
                draw();
            }
        }
    }

    /**
     * Function that draw swing design for the password change joptionpane and takes certain actions depending on outcome of function changepass
     * in class settings.
     */
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
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(m, "Can't leave fields empty!");
                        draw();
                    }
                    case NO_SUCH_USER, CANT_UPDATE -> {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(m, "Something went wrong try again!");
                        draw();
                    }
                    case PASSWORD_MATCH_ERROR -> {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(m, "Password match error, new passwords don't match!");
                        draw();
                    }
                    case OLD_PASSWORD_ERROR -> {
                        Toolkit.getDefaultToolkit().beep();
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

