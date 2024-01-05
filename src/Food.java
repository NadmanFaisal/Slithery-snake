import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Food {
    // Attributes of panel that would contain the food.
    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    private final int TOP_PANEL_HEIGHT;
    private final int UNIT;

    // Attributes for x and y coordinate of the food
    private int foodX;
    private int foodY;

    private Random random;
    private ImageIcon foodImage;
    public Food(int PANEL_WIDTH, int PANEL_HEIGHT, int TOP_PANEL_HEIGHT, int UNIT, String filePath) {
        this.PANEL_WIDTH = PANEL_WIDTH;
        this.PANEL_HEIGHT = PANEL_HEIGHT;
        this.TOP_PANEL_HEIGHT = TOP_PANEL_HEIGHT;
        this.UNIT = UNIT;
        this.random = new Random();

        //Assigning random values in constructor so food will not appear on top panel.
        this.foodX = random.nextInt(PANEL_WIDTH / UNIT) * UNIT;
        this.foodY = random.nextInt(PANEL_HEIGHT / UNIT) * UNIT + TOP_PANEL_HEIGHT;

        this.foodImage = new ImageIcon(filePath);
    }

    //Method to assign new random value to the coordinates of food, so food appears at a new position.
    public void newFood() {
        this.foodX = random.nextInt(PANEL_WIDTH / UNIT) * UNIT;
        this.foodY = random.nextInt(PANEL_HEIGHT / UNIT) * UNIT + TOP_PANEL_HEIGHT;
    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    // Method to draw the food image.
    public void drawFood(Graphics graphics) {
        graphics.drawImage(foodImage.getImage(), foodX, foodY, null);
    }
}
