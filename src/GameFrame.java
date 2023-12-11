import javax.swing.*;
import java.awt.*;

public class GameFrame {
    private JFrame frame;
    public GameFrame() {
        initialize();
    }
    public void initialize() {
        this.frame = new JFrame();
        this.frame.setTitle("Slithery Snake");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setResizable(false);

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(0,50));
        topPanel.setBackground(new Color(49, 85, 69));

        JButton playButton = new JButton("Play");
        playButton.setBounds(50,10, 50,30);
        playButton.setFocusable(false);
        topPanel.add(playButton);

        ImageIcon logo = new ImageIcon("D:/GitLab Home/group-11/src/Untitled design (1).png");
        Image logoImage = logo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedLogo = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(resizedLogo);

        topPanel.setLayout(new BorderLayout());
        topPanel.add(logoLabel, BorderLayout.WEST);

        this.frame.add(topPanel, BorderLayout.NORTH);
        this.frame.add(new GamePanel(), BorderLayout.CENTER);
        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.setLocationRelativeTo(null);
    }
}
