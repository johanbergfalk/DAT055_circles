import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Registerpanel extends JPanel implements ActionListener {
   private JTextField username;
   private JPasswordField password;
    private JPasswordField passwordrep;
   private MainFrame m;
   private Register r;

    public Registerpanel(MainFrame m) {
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
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(event -> m.navigateTo(Loginpanel::new));
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this);
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


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action == "Register") {
            if (!Arrays.equals(password.getPassword(), passwordrep.getPassword())) {
                System.out.println("password match error");
            } else {
                r = new Register(username.getText(), password.getPassword());
                r.reg();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if (r.Get_regstatus()) {
                    username.setText("");
                    password.setText("");
                    passwordrep.setText("");
                    JOptionPane.showMessageDialog(m, "Successful registration!" + username.getText());
                    m.navigateTo(Loginpanel::new);
                } else {
                    System.out.println("not");
                }
            }
        }
    }



    //TODO
    // if sucess a prompt displayed with a verification that a user is reged
    // take back to login screen when click ok.
    // if failed then show error.

}