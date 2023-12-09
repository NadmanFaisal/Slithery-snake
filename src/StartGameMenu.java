import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

    public class StartGameMenu extends MouseAdapter {
        public boolean active;
        private  int panelWidth;
        private int panelHeight;
        private ImageIcon backgroundImage;

        // play button
        private RoundRectangle2D playButton;
        private String buttonText = "Start";
        private boolean btnHighlight = false; // Boolean to check if mouse is hovering on button
        private Font font;
        private GamePanel gamePanel;

        public StartGameMenu(GamePanel gamePanel, int panelWidth, int panelHeight, ImageIcon backgroundImage) {
            this.gamePanel = gamePanel;
            this.panelWidth = panelWidth;
            this.panelHeight= panelHeight;
            this.backgroundImage = backgroundImage;
            designButton();
        }
        public void designButton (){
            // Position of the button on the screen
            int buttonWidth = 300;
            int buttonHeight = 150;
            int xCor = 100;
            int yCor = panelHeight / 2 - buttonHeight / 2;

            playButton = new RoundRectangle2D.Double(xCor,yCor,buttonWidth,buttonHeight, 50, 30);
            font = new Font(Font.MONOSPACED,Font.BOLD,90);
        }

        // This method draws the button and adds the text inside
        public void drawStartMenu(Graphics graphics) {
            Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D.drawImage(backgroundImage.getImage(),0,0,panelWidth,panelHeight,null);
            graphics2D.setFont(font);

            // Sets the color of the button. The button changes to dark green if the mouse is hovering over it.
            if (btnHighlight) {
                graphics2D.setColor(new Color(0,128,0));
            }else{
                graphics2D.setColor(new Color(34,139,34));
            }

            // Border of the button
            graphics2D.fill(playButton);
            graphics.setColor(new Color(51,102,51));
            int borderThickness = 8;
            graphics2D.setStroke(new BasicStroke(borderThickness));
            graphics2D.draw(playButton);


            // Adding start button text and positioning it inside the button
            int strWidth = graphics.getFontMetrics(font).stringWidth(buttonText);
            int strHeight = graphics.getFontMetrics(font).getHeight();
            graphics.setColor(new Color(255,255,240));
            graphics.drawString(buttonText, (int) (playButton.getX() + playButton.getWidth() / 2 - strWidth / 2),
                    (int) (playButton.getY() + playButton.getHeight() / 2 + strHeight / 4));


        }
        // Detects if a mouse click occurs within the coordinates of the play button.
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint(); // gets the coordinate of the mouse event/click
            if (playButton.contains(p)) { // Checks if the mouse click occurred within the playButton area.
                active = true;
                gamePanel.startGame();
            }
        }
        // Detects mouse movements within the play button.
        @Override
        public void mouseMoved (MouseEvent mouseEvent){
            Point p = mouseEvent.getPoint(); // gets the coordinate of the mouse event
            if(playButton.contains(p)){ // Checks if the mouse pointer is within the playButton area.
                btnHighlight= true;
            }else {
                btnHighlight = false;
            }
            gamePanel.repaint();
        }
    }


