package Circles.Controller;
import Circles.Model.DatabaseConn;
import Circles.Model.Login;
import Circles.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

/**
 * Class for handling control and swing design.
 *@author Oscar
 *@version 2022-03-02
 */
public class LoginPanel extends JPanel implements ActionListener {
    private JTextField username;
    private JPasswordField password;
    private MainFrame m;
    private Login l;
    private User user;

    /**
     * Constructor for the loginpanel takes the frame and handles swing design.
     * if button register is pressed user will be taken to registerpanel.
     * @param m mainframe
     */
    public LoginPanel(MainFrame m) {
        setPreferredSize(new Dimension(m.getWidth(), m.getHeight()));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(3, 1, 0, 10));
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(2, 1, 3, 3));
        textPanel.setBackground(Color.WHITE);
        JLabel welcome = new JLabel("Welcome to Circles");
        welcome.setHorizontalAlignment(0);
        welcome.setVerticalAlignment(JLabel.BOTTOM);
        welcome.setFont(new Font("Arial Black", Font.BOLD, 32));
        welcome.setForeground(Color.decode("#C6E2FF"));


        JLabel prompt = new JLabel("Please login or register to continue:");
        prompt.setHorizontalAlignment(0);
        textPanel.add(welcome);
        textPanel.add(prompt);
        add(textPanel);


        JPanel userpass = new JPanel();
        JPanel userpassFinal = new JPanel();
        userpassFinal.setBackground(Color.WHITE);
        userpassFinal.setLayout(new FlowLayout());
        JPanel emptyu1 = new JPanel();
        JPanel emptyu2 = new JPanel();
        emptyu2.setBackground(Color.WHITE);
        emptyu1.setBackground(Color.WHITE);
        userpassFinal.add(emptyu1);
        userpass.setLayout(new GridLayout(2, 2, 0, 0));
        userpass.setBackground(Color.WHITE);
        JLabel uname = new JLabel("Username: ");
        uname.setHorizontalAlignment(0);
        JLabel pass = new JLabel("Password: ");
        pass.setHorizontalAlignment(0);
        username = new JTextField();
        username.setColumns(15);
        password = new JPasswordField();
        password.setColumns(15);
        userpass.add(uname);
        userpass.add(username);
        userpass.add(pass);
        userpass.add(password);

        userpassFinal.add(userpass);
        userpassFinal.add(emptyu2);

        add(userpassFinal);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 1, 10));
        buttonPanel.setBackground(Color.WHITE);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(event -> m.navigateTo(RegisterPanel::new));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        JPanel alignPanel = new JPanel();
        JPanel empty1 = new JPanel();
        empty1.setBackground(Color.WHITE);
        JPanel empty2 = new JPanel();
        empty2.setBackground(Color.WHITE);
        alignPanel.setLayout(new BoxLayout(alignPanel, BoxLayout.X_AXIS));
        alignPanel.add(empty1);
        alignPanel.add(buttonPanel);
        alignPanel.add(empty2);
        add(alignPanel);
        this.m = m;
    }

    /**
     Function which listen when an event occurs, in our case we listen if a button is pressed and take certain action.
     In case button login is pressed we handle login logic in class login and depending on outcome it will either prompt user with error
     or take user to landing page.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action == "Login") {
            l = new Login(password.getPassword(), username.getText());
            try {
                Login.Result result = l.validateuser().get();
                switch (result) {
                    case OK -> {
                        user = new User(username.getText());
                        user.setDarkmode(DatabaseConn.getUserMode(user.getUsername()));
                        m.navigateTo(frame -> new LandingPagePanel(frame, user));
                    }
                    case EMPTY_FIELDS -> {
                        getToolkit().beep();
                        JOptionPane.showMessageDialog(m, "Can't leave fields password or username blank Please try again!");
                    }
                    case NO_SUCH_USER -> {
                        getToolkit().beep();
                        JOptionPane.showMessageDialog(m, "Invalid Username OR password" + " " + "Please try again!");
                        username.setText("");
                        password.setText("");
                    }
                }
            } catch (NullPointerException | ExecutionException | InterruptedException exception){
                exception.printStackTrace();
            }
        }
    }
}