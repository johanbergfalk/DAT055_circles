package Circles.Controller;

import Circles.Model.Register;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;
/**
 Class for register controller functions and swing design.
 *@author Oscar
 *@version 2022-03-02
*/

public class RegisterPanel extends JPanel implements ActionListener {
   private JTextField username;
   private JPasswordField password;
   private JPasswordField passwordrep;
   private MainFrame m;
   private Register r;

    /**
     * constructor that takes current Jframe.
     * makes the registerpanel swing design and add actionlisteners to buttons login and register.
     * if button login is pressed user will be taken to loginpanel.
     * @param m
     */
    public RegisterPanel(MainFrame m) {
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


        JLabel prompt = new JLabel("Please register (if already registered click login):");
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
        userpass.setLayout(new GridLayout(3, 3, 0, 0));
        userpass.setBackground(Color.WHITE);
        JLabel uname = new JLabel("Username: ");
        uname.setHorizontalAlignment(0);
        JLabel pass = new JLabel("Password: ");
        pass.setHorizontalAlignment(0);
        username = new JTextField();
        username.setColumns(15);
        password = new JPasswordField();
        password.setColumns(15);
        JLabel passrep = new JLabel("Repeat Password: ");
        passrep.setHorizontalAlignment(0);
        passwordrep = new JPasswordField();
        passwordrep.setColumns(15);

        userpass.add(uname);
        userpass.add(username);
        userpass.add(pass);
        userpass.add(password);
        userpass.add(passrep);
        userpass.add(passwordrep);

        userpassFinal.add(userpass);
        userpassFinal.add(emptyu2);

        add(userpassFinal);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 1, 10));
        buttonPanel.setBackground(Color.WHITE);
        JButton loginButton = new JButton("Abort");
        loginButton.addActionListener(event -> m.navigateTo(LoginPanel::new));
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);


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
     in case button register is pressed we handle register logic in class register and depending on outcome it will either prompt user with error
     or take user to loginpanel.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action == "Register") {
            r = new Register(username.getText(), password.getPassword(),passwordrep.getPassword());
            Boolean pass_rep_match;
            pass_rep_match = r.val_rep_pass();
            if(!pass_rep_match){
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(m, "Passwords don't match!");
                password.setText("");
                passwordrep.setText("");
            }else{
                try {
                    Register.Result result = r.reg().get();
                    switch (result) {
                        case OK -> {
                            JOptionPane.showMessageDialog(m, "Successful registration!" + " " + " WE Welcome YOU" + " " + username.getText());
                            username.setText("");
                            password.setText("");
                            passwordrep.setText("");
                            m.navigateTo(LoginPanel::new);
                        }
                        case EMPTY_FIELDS ->  {
                            Toolkit.getDefaultToolkit().beep();
                            JOptionPane.showMessageDialog(m, "Can't leave fields password or username blank Please try again!");
                        }
                        case DUPLICATE ->   {
                            Toolkit.getDefaultToolkit().beep();
                            JOptionPane.showMessageDialog(m, "Username" + " " + username.getText() + " " + " Already taken! Please try again!");
                            username.setText("");
                            password.setText("");
                            passwordrep.setText("");
                        }
                    }
                } catch (NullPointerException | ExecutionException | InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}