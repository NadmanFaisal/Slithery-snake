import javax.swing.*;

public class GameFrame {
    private JFrame frame;
    private GamePanel gamePanel; //NEW
    private GameOverPanel gameOverPanel; //NEW
    private Timer stopwatchTimer;
    private JLabel stopwatchLabel;
    private JPanel stopwatchPanel;
    private int playedSeconds;  

    public GameFrame() {
        initialize();
    }
    public void initialize() {
        this.frame = new JFrame();
        this.frame.setTitle("Slithery Snake");

        this.gamePanel = new GamePanel();//NEW
        this.gameOverPanel = new GameOverPanel(); //NEW

        this.frame.add(gamePanel);//NEW
    //  this.frame.add(gameOverPanel); //NEW
        this.gamePanel.setVisible(true); //NEW
        //this.gameOverPanel.setVisible(false); //NEW

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
    }

    public GamePanel getGamePanel(){ //NEW
        return gamePanel; 
    }

     public GameOverPanel getGameOverPanel(){ //NEW
        return gameOverPanel; 
    }
}
