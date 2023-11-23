import javax.swing.*;

public class GameFrame {
    private JFrame frame;
    public GameFrame() {
        initialize();
    }
    public void initialize() {
        this.frame = new JFrame();
        this.frame.setTitle("Snake Game");
        this.frame.add(new GamePanel());
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
    }
}
