import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private int ToxicfoodX;
    private int ToxicfoodY;
    private String direction;
    private boolean running;
    private Random random;
    private Timer timer;
    private int foodCounter;
    private int num; //better name

    public GamePanel() {
        this.bodyUnits = 6;
        this.foodCounter = 0;
        this.direction = "Right";
        this.running = false;
        this.random = new Random();
        this.num = random.nextInt(10);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        startGame();

    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (running) { //Does this work??
            draw(graphics);
        }
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
        //Food one item
        if (foodCounter == num) {
            graphics.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            graphics.fillOval(ToxicfoodX, ToxicfoodY, UNIT, UNIT);
        }



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

    public void newToxicFood() {
        ToxicfoodX = random.nextInt(PANEL_WIDTH / UNIT) * UNIT;
        ToxicfoodY = random.nextInt(PANEL_HEIGHT / UNIT) * UNIT;
    }

    public void movement() {
        for(int i = bodyUnits; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case "Right" -> x[0] = x[0] + UNIT;
            case "Left" -> x[0] = x[0] - UNIT;
            case "Up" -> y[0] = y[0] - UNIT;
            case "Down" -> y[0] = y[0] + UNIT;
        }

    }

    public void checkFood() {
        if((x[0] == foodX && y[0] == foodY)) {
            bodyUnits = bodyUnits + 1;
            foodCounter = foodCounter + 1;
            if(foodCounter != num) {
                newFood();
            } else {
                newToxicFood();
            }
        } else if (x[0] == ToxicfoodX && y[0] == ToxicfoodY) {
            bodyUnits = bodyUnits / 2;
            foodCounter = 0;
            num = random.nextInt(10);
            newFood();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            movement();
            checkFood();

        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            switch (keyEvent.getKeyCode()) {
                case (KeyEvent.VK_RIGHT) -> {
                    if (!direction.equals("Left")) {
                        direction = "Right";
                    }
                }
                case (KeyEvent.VK_LEFT) -> {
                    if (!direction.equals("Right")) {
                        direction = "Left";
                    }
                }
                case (KeyEvent.VK_UP) -> {
                    if (!direction.equals("Down")) {
                        direction = "Up";
                    }
                }
                case (KeyEvent.VK_DOWN) -> {
                    if (!direction.equals("Up")) {
                        direction = "Down";
                    }
                }
            }
        }
    }
}
