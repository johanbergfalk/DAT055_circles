package Circles.Controller;

import Circles.Model.Circle;
import Circles.Model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 *Class for welcoming a user to the program.
 *@author Oscar
 *@version 2022-03-05
 */

public class LandingPagePanel extends JPanel {

    public LandingPagePanel(MainFrame frame, User user) {

        setLayout(new BorderLayout());
        setBackground(user.getBackgroundColor());
        Circle temp = new Circle();
        add(new NavigationBar(frame, user, 1, temp), BorderLayout.NORTH);
        setBorder(BorderFactory.createTitledBorder(new EmptyBorder(10,5,5,5), " "));


        JPanel textPanel = new JPanel();
        textPanel.setBackground(user.getBackgroundColor());
        textPanel.setLayout(new GridLayout(2,1,10,10));

        JLabel welcome = new JLabel();
        welcome.setFont(new Font("Arial Black", Font.BOLD, 48));
        welcome.setForeground(Color.decode("#C6E2FF"));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        String username = user.getUsername();
        if(username.length() > 10){
            welcome.setText("Welcome, " + username.substring(0,9) + "...!");
        } else {
            welcome.setText("Welcome, " + username + "!");
        }

        JLabel prompt = new JLabel("Use the menu above to start navigating");
        prompt.setFont(new Font("serif", Font.BOLD, 24));
        prompt.setForeground(user.getForegroundColor());
        prompt.setHorizontalAlignment(SwingConstants.CENTER);
        textPanel.add(welcome);
        textPanel.add(prompt);

        JPanel bufferpanel = new JPanel();
        bufferpanel.setBackground(user.getBackgroundColor());
        bufferpanel.setLayout(new GridBagLayout());
        bufferpanel.add(textPanel);

        add(bufferpanel, BorderLayout.CENTER);
    }
}
