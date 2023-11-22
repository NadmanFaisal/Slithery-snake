import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 600;
    private static final int UNIT_SIZE = 30;
    private static final int GAME_UNITS = (PANEL_HEIGHT * PANEL_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 100;
    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];
    private int bodyUnits;
    private int foodX;
    private int foodY;
    private String direction;
    private boolean running;
    private Random random;
    private Timer timer;

    public GamePanel() {
        this.bodyUnits = 6;
        this.direction = "Right";
        this.running = false;
        this.random = new Random();
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);

        startGame();

    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics) {

        //temporary grid
        for(int i = 0; i < PANEL_HEIGHT / UNIT_SIZE; i++) {
            graphics.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, PANEL_HEIGHT);
            graphics.drawLine(0, i * UNIT_SIZE, PANEL_WIDTH, i * UNIT_SIZE);
        }

        //food item
        graphics.setColor(Color.orange);
        graphics.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

        //snake
        for(int i = 0; i < bodyUnits; i++) {
            if(i == 0) {
                graphics.setColor(new Color(150, 0, 0));
                graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            } else {
                graphics.setColor(Color.red);
                graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }

    }

    public void startGame() {
        newFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void newFood() {
        foodX = random.nextInt(PANEL_WIDTH / UNIT_SIZE) * UNIT_SIZE; //will it ever appear at corner??
        foodY = random.nextInt(PANEL_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    public void movement() {
        for(int i = bodyUnits; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (direction.equals("Right")) {
            x[0] = x[0] + UNIT_SIZE;
        } else if (direction.equals("Left")) {
            x[0] = x[0] - UNIT_SIZE;
        } else if (direction.equals("Up")) {
            y[0] = y[0] - UNIT_SIZE;
        } else if (direction.equals("Down")) {
            y[0] = y[0] + UNIT_SIZE;
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(running == true) {
            movement();

        }
        repaint();

    }
}
