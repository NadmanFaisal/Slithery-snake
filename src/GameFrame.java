import javax.swing.*;

public class GameFrame {
    private JFrame frame;
    private GamePanel gamePanel;

    public GameFrame() {
        initialize();
    }
    public void initialize() {
        ImageIcon snakeLogo = new ImageIcon("Snake_Logo.png");
        this.frame = new JFrame();
        this.frame.setTitle("Slithery Snake");
        this.gamePanel = new GamePanel();
        this.frame.add(gamePanel);
        this.gamePanel.setVisible(true);

        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);

        this.frame.setIconImage(snakeLogo.getImage());
    }
}
