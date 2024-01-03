import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URL;

public class GameOverScreen extends MouseAdapter {

    private int panelWidth;
    private int panelHeight;
    private boolean active;
    private boolean repaint;

    private ImageIcon backgroundImage;

    // restart button
    private RoundRectangle2D restartButton;
    private String buttonText = "Restart";
    private boolean btnHighlight = false; // Boolean to check if mouse is hovering on button
    private final Font customFont;

    public GameOverScreen(int panelWidth, int panelHeight) {
        this.active = false;
        this.repaint = false;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.backgroundImage = new ImageIcon("src/Images/Background.jpeg");
        this.customFont = getFont("KarmaFuture.ttf");
        createButton();
    }

    public void createButton(){

        // Position of the button on the screen
        int buttonWidth = 110;
        int buttonHeight = 60;
        int xCor = panelWidth/2 - buttonWidth/2;
        int yCor = (panelHeight/2 - buttonHeight/2) + 110;

        restartButton = new RoundRectangle2D.Double(xCor, yCor, buttonWidth, buttonHeight, 50, 30);
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

    public void showGameOverScreen(Graphics graphics, int score, int playedSeconds, int tenthOfSecond) {
        drawBackground(graphics);
        drawGameResult(graphics, score, playedSeconds, tenthOfSecond);
        drawButton(graphics);
        drawTextInsideButton(graphics);
    }

    public void drawBackground(Graphics graphics){
        graphics.drawImage(backgroundImage.getImage(), 0, 0, panelWidth, panelHeight, null);
    }

    public void drawGameResult(Graphics graphics, int score, int playedSeconds, int tenthOfSecond){
        graphics.setFont(customFont.deriveFont(Font.BOLD, 30));
        graphics.setColor(new Color(14, 102, 0));
        FontMetrics metrics = graphics.getFontMetrics();
        int gameOverWidth = metrics.stringWidth("Game Over!");
        int scoreWidth = metrics.stringWidth("Score: " + score);
        int timeWidth = metrics.stringWidth("Time played: " + playedSeconds + "." + tenthOfSecond + "seconds");

        int xGameOver = (panelWidth - gameOverWidth) / 2;
        int xScore = (panelWidth - scoreWidth) / 2;
        int xTime = (panelWidth - timeWidth) / 2;
        int y = panelHeight / 2;

        graphics.drawString("Game Over!", xGameOver, y - 30);
        graphics.drawString("Score: " + score, xScore, y + 10); // Adjust Y position for spacing
        graphics.drawString("Time played: " + playedSeconds + "." + tenthOfSecond + " seconds", xTime, y + 50);
    }

    //Displays the restart button on the Game Over screen. 
    public void drawButton(Graphics graphics){
        Graphics2D graphics2D = (Graphics2D) graphics;

        // Sets the color of the button. The button changes to dark green if the mouse is hovering over it.
        if (btnHighlight) {
            graphics2D.setColor(new Color(0, 128, 0));
        } else {
            graphics2D.setColor(new Color(34, 139, 34));
        }

        // Border of the button
        graphics2D.fill(restartButton);
        graphics.setColor(new Color(51, 102, 51));
        int borderThickness = 5;
        graphics2D.setStroke(new BasicStroke(borderThickness));
        graphics2D.draw(restartButton);

    }

     // Adding start button text and positioning it inside the button
    public void drawTextInsideButton(Graphics graphics){
        graphics.setFont(customFont.deriveFont(Font.BOLD, 23));
        graphics.setColor(new Color(255, 255, 240));

        int textX = (int) (restartButton.getX() + 5);
        int textY = (int) (restartButton.getY() + 35);
        graphics.drawString(buttonText, textX, textY);
    }

    // Detects if a mouse click occurs within the coordinates of the play button.
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint(); // gets the coordinate of the mouse event/click
        if (!active && restartButton.contains(p)) {
            active = true;
        }
    }

    // Detects mouse movements within the play button.
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint(); // gets the coordinate of the mouse event
        if (restartButton.contains(p)) { // Checks if the mouse pointer is within the playButton area.
            btnHighlight = true;
        } else {
            btnHighlight = false;
        }
        repaint = true;
    }
    public boolean isRepaint() {
        return repaint;
    }

    public void setRepaint(boolean repaint) {
        this.repaint = repaint;
    }
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}


