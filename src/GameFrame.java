import javax.swing.*;

public class GameFrame {
    private JFrame frame;
    private Timer stopwatchTimer;
    private JLabel stopwatchLabel;
    private JPanel stopwatchPanel;
    private int playedSeconds;  

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
        this.stopwatchPanel = new JPanel();
        this.stopwatchLabel = new JLabel();
        this.stopwatchLabel.setText("Time: 0 seconds"); 
        this.stopwatchPanel.add(stopwatchLabel);
        this.frame.add(stopwatchPanel);
        //this.stopwatchTimer = new Timer(1000, null);
    }
}
