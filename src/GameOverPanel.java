import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class GameOverPanel extends JPanel {

    private int scoreCounter;
    private int playedSeconds;
    private int tenthOfSecond;

    private boolean gameOver;

    private JButton restartButton;

    private ImageIcon backgroundImage;

    public GameOverPanel() {
        this.setPreferredSize(new Dimension(GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT));
        this.setFocusable(false);
        this.setVisible(true);

        this.backgroundImage = new ImageIcon("src/Images/gamepanel-bg.png");

        restartButton = new JButton("Restart Game");
        restartButton.setPreferredSize(new Dimension(150, 50));
        add(restartButton);
        restartButton.setVisible(true);
    }

    public void setScoreCounter(int scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    public void setTime(int playedSeconds, int tenthOfSecond) {
        this.playedSeconds = playedSeconds;
        this.tenthOfSecond = tenthOfSecond;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }


    public void showGameOverScreen(Graphics graphics) {

            graphics.setFont(new Font(Font.SERIF, Font.BOLD, 30));
           // graphics.setColor(Color.PINK);
           // graphics.fillRect(0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);
            drawBackgroundImage(graphics);
            graphics.setColor(Color.RED);

            FontMetrics metrics = graphics.getFontMetrics();
            int gameOverWidth = metrics.stringWidth("Game Over!");
            int scoreWidth = metrics.stringWidth("Score: " + scoreCounter);
            int timeWidth = metrics.stringWidth("Time played: " + playedSeconds + "." + tenthOfSecond + "seconds");

            int xGameOver = (GamePanel.PANEL_WIDTH - gameOverWidth) / 2;
            int xScore = (GamePanel.PANEL_WIDTH - scoreWidth) / 2;
            int xTime = (GamePanel.PANEL_WIDTH - timeWidth) / 2;
            int y = GamePanel.PANEL_HEIGHT / 2;

            graphics.drawString("Game Over!", xGameOver, y - 30);
            graphics.drawString("Score: " + scoreCounter, xScore, y + 10); // Adjust Y position for spacing
            graphics.drawString("Time played: " + playedSeconds + "." + tenthOfSecond + " seconds", xTime, y + 50);

    }

    public void drawBackgroundImage (Graphics graphics){
        graphics.drawImage(backgroundImage.getImage(), 0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT, this);
    }
}