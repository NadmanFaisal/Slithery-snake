import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends GamePanel {

    public GameOverPanel () {
         
    }

    public void showGameOverScreen(Graphics graphics){

    graphics.setColor(Color.pink);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        graphics.setColor(Color.black);
        graphics.setFont(new Font(Font.SERIF, Font.BOLD, 30));

        FontMetrics metrics = graphics.getFontMetrics();
        int gameOverWidth = metrics.stringWidth("Game Over!");
        int scoreWidth = metrics.stringWidth("Score: " + getScoreCounter());
        int timeWidth = metrics.stringWidth("Time played: " + getPlayedSeconds() + "." + getTenthOfSecond() + "seconds"); 

        int xGameOver = (getWidth() - gameOverWidth) / 2; 
        int xScore = (getWidth() - scoreWidth) / 2; 
        int xTime = (getWidth() - timeWidth) / 2; 
        int y = getHeight() / 2; 

        graphics.drawString("Game Over!", xGameOver, y - 30);
        graphics.drawString("Score: " + getScoreCounter(), xScore, y + 10); // Adjust Y position for spacing
        graphics.drawString("Time played: " + getPlayedSeconds() + "." +  getTenthOfSecond() + " seconds", xTime, y + 50); 
         }
}