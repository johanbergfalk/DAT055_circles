import javax.swing.*;
import java.awt.*;

public class LoadingScreenPanel extends JPanel {
    public LoadingScreenPanel(User u){
        setBackground(u.getBackgroundColor());
        setLayout(new GridBagLayout());

        JPanel holder = new JPanel();
        holder.setLayout(new GridLayout(2,1));
        holder.setBackground(u.getBackgroundColor());

        JLabel loading = new JLabel("Loading Circles");
        loading.setForeground(Color.decode("#C6E2FF"));
        loading.setFont(new Font("Arial Black", Font.BOLD, 24));
        loading.setHorizontalAlignment(SwingConstants.CENTER);

        holder.add(loading);

        JProgressBar animation = new JProgressBar();
        animation.setIndeterminate(true);

        holder.add(animation);
        add(holder);
    }
}
