import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 600;
    private static final int UNIT = 30;
    private static final int GAME_UNITS = (PANEL_HEIGHT * PANEL_HEIGHT) / (UNIT * UNIT);
    private static final int TIMER_DELAY = 100;
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
        for(int i = 0; i < PANEL_HEIGHT / UNIT; i++) {
            graphics.drawLine(i * UNIT, 0, i * UNIT, PANEL_HEIGHT);
            graphics.drawLine(0, i * UNIT, PANEL_WIDTH, i * UNIT);
        }

        //food item
        graphics.setColor(Color.orange);
        graphics.fillOval(foodX, foodY, UNIT, UNIT);

        //snake
        for(int i = 0; i < bodyUnits; i++) {
            if(i == 0) {
                graphics.setColor(new Color(150, 0, 0));
                graphics.fillRect(x[i], y[i], UNIT, UNIT);
            } else {
                graphics.setColor(Color.red);
                graphics.fillRect(x[i], y[i], UNIT, UNIT);
            }
        }

    }

    public void startGame() {
        newFood();
        running = true;
        timer = new Timer(TIMER_DELAY, this);
        timer.start();

    }

    public void newFood() {
        foodX = random.nextInt(PANEL_WIDTH / UNIT) * UNIT; //will it ever appear at corner??
        foodY = random.nextInt(PANEL_HEIGHT / UNIT) * UNIT;
    }

    public void movement() {
        for(int i = bodyUnits; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (direction.equals("Right")) {
            x[0] = x[0] + UNIT;
        } else if (direction.equals("Left")) {
            x[0] = x[0] - UNIT;
        } else if (direction.equals("Up")) {
            y[0] = y[0] - UNIT;
        } else if (direction.equals("Down")) {
            y[0] = y[0] + UNIT;
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
