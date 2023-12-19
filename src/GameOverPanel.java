import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class GameOverPanel extends JPanel {

    private int scoreCounter;
    private int playedSeconds;          
    private int tenthOfSecond;
    private final Font customFont;
    private JButton restartButton; 
    private boolean gameOver;


    private ImageIcon backgroundImage;

    public GameOverPanel() {

        this.setPreferredSize(new Dimension(GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT));
        this.setFocusable(false);
        this.setVisible(false);

        this.backgroundImage = new ImageIcon("src/Images/gamepanel-bg.png");
        this.customFont = getFont("KarmaFuture.ttf");
        this.setOpaque(false); //So that you cannot see a grey background
        createButton();
    }

    public void createButton(){
    
        this.restartButton = new JButton("Restart Game"); 

        restartButton.setPreferredSize(new Dimension(130, 30));
        restartButton.setBounds(100,100,200,50); 
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent action){
                GameLauncher.restart();
            }
        };
        restartButton.addActionListener(actionListener);

        this.add(Box.createVerticalStrut(700));
        this.add(restartButton);
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

    public Font getFont (String fontName){
        try {
            String path = "/Fonts/" + fontName;
            URL url = getClass().getResource(path);
            return Font.createFont(Font.TRUETYPE_FONT, url.openStream());
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public void showGameOverScreen(Graphics graphics) {

            graphics.setFont(customFont.deriveFont(Font.BOLD, 25));
            graphics.setColor(new Color(14, 102, 0));
            drawBackgroundImage(graphics);

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