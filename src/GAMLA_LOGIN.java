
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.sql.SQLException;


public class GAMLA_LOGIN implements ActionListener {
    loginModel model;
    DBConnection conn;
    JPanel loginPanel;
    String user;
    JTextField username;
    JPasswordField password;
    JLabel loginError;
    JPanel mainPanel;
    MainFrame m;

    public GAMLA_LOGIN() throws SQLException, URISyntaxException {
        m = new MainFrame();
        m.setTitle("Circles");
        //conn = new DBConnection();
        model = new loginModel();
        m.setMinimumSize(new Dimension(500,300));
        loginPanel = this.makeLoginPanel();
        m.add(loginPanel);
        m.setLocation(100,100);
        m.setVisible(true);
        m.setDefaultCloseOperation(m.EXIT_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String sourceClicked = e.getActionCommand();
        if(username.getText() != ""){
        switch(sourceClicked) {
            case "Login":
                byte[] h = conn.getHash(username.getText());
                byte[] s = conn.getSalt(username.getText());
                if(s != null){
                if (Passwords.isExpectedPassword(password.getPassword(), s, h)) {
                    this.user = username.getText();
                    password.setText("");
                    username.setText("");
                    loginError.setVisible(false);
                    this.mainPanel = this.makeMainPanel();
                    loginPanel.setVisible(false);
                    m.add(mainPanel);
                } else {
                    password.setText("");
                    username.setText("");
                    loginError.setVisible(true);
                }
                } else {
                    password.setText("");
                    username.setText("");
                    loginError.setVisible(true);
                }
                break;
            case "Register":
                if(username.getText().length() >= 4 && password.getPassword().length >= 4) {
                    byte[] sr = Passwords.getNextSalt();
                    byte[] hr = Passwords.hash(password.getPassword(), sr);
                    boolean success = DatabaseConn.addUser(username.getText(),hr, hr, sr);
                    password.setText("");
                    username.setText("");
                    if (success) {
                        JOptionPane.showMessageDialog(m, "You are now registered. Please log in.");
                    } else {
                        JOptionPane.showMessageDialog(m, "Username already taken.");
                    }
                    break;
                } else {
                    JOptionPane.showMessageDialog(m, "Username and password need to be atleast four charachters each.");
                }
        }
        }
        m.repaint();
    }

    private void terminate() throws SQLException {
        conn.close();
        System.exit(0);
    }

    private JPanel makeMainPanel(){
        JPanel users = new JPanel();
        users.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        String[] db = conn.getUsers();
        for (String s : db) {
            JLabel user = new JLabel(s);
            users.add(user, gbc);
        }
        JScrollPane allUsers = new JScrollPane(users);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(2,1,0,0));
        labelPanel.add(new JLabel("Logged in as: " + this.user));
        labelPanel.add(new JLabel("All registered users so far: "));
        mainPanel.add(labelPanel, BorderLayout.NORTH);
        mainPanel.add(allUsers, BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel makeLoginPanel(){

        JPanel finishedPanel = new JPanel();
        finishedPanel.setBackground(Color.WHITE);
        finishedPanel.setLayout(new GridLayout(3,1,0,10));
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(2,1,3,3));
        textPanel.setBackground(Color.WHITE);
        JLabel welcome = new JLabel("Welcome to Circles");
        welcome.setHorizontalAlignment(0);
        welcome.setVerticalAlignment(JLabel.BOTTOM);
        welcome.setFont(new Font("Arial Black",Font.BOLD,24));
        welcome.setForeground(Color.decode("#C6E2FF"));


        JLabel prompt = new JLabel ("Please login or register to continue:");
        prompt.setHorizontalAlignment(0);
        textPanel.add(welcome);
        textPanel.add(prompt);
        finishedPanel.add(textPanel);


        JPanel userpass = new JPanel();
        JPanel userpassFinal = new JPanel();
        userpassFinal.setBackground(Color.WHITE);
        userpassFinal.setLayout(new FlowLayout());
        JPanel emptyu1 = new JPanel();
        JPanel emptyu2 = new JPanel();
        emptyu2.setBackground(Color.WHITE);
        emptyu1.setBackground(Color.WHITE);
        userpassFinal.add(emptyu1);
        userpass.setLayout(new GridLayout(2,2,0,0));
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

        finishedPanel.add(userpassFinal);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,1,1,10));
        buttonPanel.setBackground(Color.WHITE);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        loginError = new JLabel("Error: Wrong username or password");
        loginError.setForeground(Color.RED);
        loginError.setHorizontalAlignment(0);
        loginError.setVisible(false);
        buttonPanel.add(loginError);
        JPanel alignPanel = new JPanel();
        JPanel empty1 = new JPanel();
        empty1.setBackground(Color.WHITE);
        JPanel empty2 = new JPanel();
        empty2.setBackground(Color.WHITE);
        alignPanel.setLayout(new BoxLayout(alignPanel,BoxLayout.X_AXIS));
        alignPanel.add(empty1);
        alignPanel.add(buttonPanel);
        alignPanel.add(empty2);
        finishedPanel.add(alignPanel);
        return finishedPanel;
    }








}
