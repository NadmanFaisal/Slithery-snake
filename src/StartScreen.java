import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URL;


public class StartScreen extends MouseAdapter {
    private boolean active;
    private boolean repaint;
    private int panelWidth;
    private int panelHeight;
    private ImageIcon backgroundImage;

    // play button
    private RoundRectangle2D playButton;
    private String buttonText = "Start";
    private boolean btnHighlight = false; // Boolean to check if mouse is hovering on button
    private Font font;

    public StartScreen(int panelWidth, int panelHeight) {
        this.active = false;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.backgroundImage = new ImageIcon("src/Images/Background.jpeg");
        this.font = getFont("KarmaFuture.ttf");
        designButton();
        this.repaint = false;
    }

    public void designButton() {
        // Position of the button on the screen
        int buttonWidth = 110;
        int buttonHeight = 60;
        int xCor = panelWidth/2 - buttonWidth/2;
        int yCor = (panelHeight/2 - buttonHeight/2) + 110;

        playButton = new RoundRectangle2D.Double(xCor, yCor, buttonWidth, buttonHeight, 50, 30);
    }

    // This method draws the button and adds the text inside
    public void drawStartMenu(Graphics graphics, int size, ImageIcon logo) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(backgroundImage.getImage(), 0, 0, panelWidth, panelHeight, null);
        graphics.drawImage(logo.getImage(), 125,125,size, size, null);

        graphics2D.setFont(font.deriveFont(Font.BOLD, 30));


        // Sets the color of the button. The button changes to dark green if the mouse is hovering over it.
        if (btnHighlight) {
            graphics2D.setColor(new Color(0, 128, 0));
        } else {
            graphics2D.setColor(new Color(34, 139, 34));
        }

        // Border of the button
        graphics2D.fill(playButton);
        graphics.setColor(new Color(51, 102, 51));
        int borderThickness = 5;
        graphics2D.setStroke(new BasicStroke(borderThickness));
        graphics2D.draw(playButton);


        // Adding start button text and positioning it inside the button
        FontMetrics metrics = graphics.getFontMetrics();
        int textX = (int) (playButton.getX() + ((playButton.getWidth() - metrics.stringWidth(buttonText))/2));
        int textY = (int) (playButton.getY() + (playButton.getHeight() / 2) + 15);
        graphics.setColor(new Color(255, 255, 240));
        graphics.drawString(buttonText, textX, textY - 5);
    }

    // Detects if a mouse click occurs within the coordinates of the play button.
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint(); // gets the coordinate of the mouse event/click
        if (!active) {
            if (playButton.contains(p)) { // Checks if the mouse click occurred within the playButton area.
                active = true;
            }
        }
    }

    // Detects mouse movements within the play button.
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint(); // gets the coordinate of the mouse event
        if (playButton.contains(p)) { // Checks if the mouse pointer is within the playButton area.
            btnHighlight = true;
        } else {
            btnHighlight = false;
        }
        repaint = true;
    }

    public Font getFont(String fontName) {
        try {
            String path = "/Fonts/" + fontName;
            URL url = getClass().getResource(path);
            return Font.createFont(Font.TRUETYPE_FONT, url.openStream());
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
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



