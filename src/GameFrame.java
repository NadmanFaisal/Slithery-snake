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
        topPanel.setBackground(Color.green);

        JButton playButton = new JButton("Play");
        topPanel.add(playButton);

        this.frame.add(topPanel, BorderLayout.NORTH);
        this.frame.add(new GamePanel(), BorderLayout.CENTER);
        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.setLocationRelativeTo(null);
    }
}
