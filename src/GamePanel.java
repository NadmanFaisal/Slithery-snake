
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

    private static final int PANEL_WIDTH = 500;
    private static final int PANEL_HEIGHT = 500;
    private static final int TOP_PANEL_HEIGHT = 100;
    private static final int UNIT = 25;
    private static final int GAME_UNITS = (PANEL_HEIGHT * PANEL_WIDTH) / (UNIT * UNIT);
    private final int TIMER_DELAY = 100;

    private Snake snake;
    private Food food;
    private Food invincibleFood;
    private Food toxicFood;

    private Random random;
    private Timer timer;
    private Timer stopwatchTimer;  //timer attribute for the stopwatch of type timer
    private String direction;
    private boolean running;
    private boolean gameOver;
    private boolean invincible;

    private int foodCounter;
    private int randomNumber; //better name
    private int randomNumber2;
    private int scoreCounter;



    private int playedSeconds; //attribute for the seconds that will go up as we play
    private int tenthOfSecond;

    private GameOverScreen gameOverScreen;
    private StartScreen startScreen;
    private ImageIcon logo;
    private ImageIcon backgroundImage;
    private final Font customFont;
    private Button button;




    public GamePanel() {
        this.snake = new Snake(UNIT, GAME_UNITS);
        this.foodCounter = 0;
        this.direction = "Right";
        this.running = false;
        this.gameOver = false;
        this.invincible = false;
        this.random = new Random();
        this.randomNumber = random.nextInt(10);
        this.randomNumber2 = random.nextInt(10);
        this.scoreCounter = 0;
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT + TOP_PANEL_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(new MyKeyAdapter());

        this.gameOverScreen = new GameOverScreen(PANEL_WIDTH, PANEL_HEIGHT + TOP_PANEL_HEIGHT);
        this.addMouseListener(gameOverScreen);
        this.addMouseMotionListener(gameOverScreen);

        this.startScreen = new StartScreen(PANEL_WIDTH,PANEL_HEIGHT + TOP_PANEL_HEIGHT);
        this.addMouseListener(startScreen);
        this.addMouseMotionListener(startScreen);

        this.backgroundImage = new ImageIcon("src/Images/gamepanel-bg.png");

        this.logo = new ImageIcon("src/Images/SnakeLogo.png");
        this.customFont = getFont("KarmaFuture.ttf");

        this.button = new Button("PLAY");
        this.button.addActionListener(e -> startGame());
        this.add(button);


        this.stopwatchTimer = new Timer(1000, this); //making the stopwatch a Timer (built-in java) object.
        this.playedSeconds = 0;
        this.tenthOfSecond = 0;
        startStopwatch(); //calling method to start the stopwatch when player starts playing

        timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    public void startStopwatch(){ //start stopwatch
        tenthOfSecond = tenthOfSecond + 1;
        if (tenthOfSecond == 10) {
            tenthOfSecond = 0;
            playedSeconds = playedSeconds + 1;
        }
        stopwatchTimer.start();
    }

    public void stopStopwatch() { //stop stopwatch
        stopwatchTimer.stop();
    }


    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (!running){
            startScreen.drawStartMenu(graphics, 250, logo);

        }else{
            drawTopPanel(graphics);
            drawBackgroundImage(graphics);
            if (foodCounter == randomNumber) {
                toxicFood.drawFood(graphics);
            }
            if (foodCounter == randomNumber2) {
                invincibleFood.drawFood(graphics);
            }
            food.drawFood(graphics);
            this.snake.drawSnake(graphics, direction);
            drawScore(graphics);
            drawStopwatchLabel(graphics);

        }
        if (gameOver) {
            gameOverScreen.showGameOverScreen(graphics, scoreCounter, playedSeconds, tenthOfSecond);
        }
    }
    public void drawStopwatchLabel (Graphics graphics) {
        if ( !gameOver ) {
            graphics.setColor(new Color(14, 102, 0));
            graphics.setFont(customFont.deriveFont(Font.BOLD, 25));
            graphics.drawString("Time: "+ playedSeconds + "." + tenthOfSecond + " seconds", PANEL_WIDTH -250, graphics.getFont().getSize() + TOP_PANEL_HEIGHT);
        }
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
    
    public void drawScore (Graphics graphics){
        graphics.setColor(new Color(14, 102, 0));
        graphics.setFont(customFont.deriveFont(Font.BOLD, 25));
        graphics.drawString("Score: " + scoreCounter,10,graphics.getFont().getSize() + TOP_PANEL_HEIGHT);
    }

    public void drawBackgroundImage(Graphics graphics){
        graphics.drawImage(backgroundImage.getImage(), 0, TOP_PANEL_HEIGHT, PANEL_WIDTH, PANEL_HEIGHT, this);
    }

    public void startGame() {
        gameOverScreen.setActive(false);//WHY DO WE I HAVE TO?? MAYBE BUG
        this.snake.setSnake(6);
        button.setVisible(true);
        this.food = new Food(PANEL_WIDTH, PANEL_HEIGHT, TOP_PANEL_HEIGHT, UNIT, "src/Images/Strawberry.png");
        this.toxicFood = new Food(PANEL_WIDTH, PANEL_HEIGHT, TOP_PANEL_HEIGHT, UNIT, "src/Images/EvilBerry.png");
        this.invincibleFood = new Food(PANEL_WIDTH, PANEL_HEIGHT, TOP_PANEL_HEIGHT, UNIT, "src/Images/InvincibleBerryMain.png");
        running = true;
        if(timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = new Timer(TIMER_DELAY, this);
        timer.start();
        startStopwatch();

    }

    public void checkFood () { //changed logic in if block
        if((snake.getX(0) == food.getFoodX() && snake.getY(0) == food.getFoodY())) {
            if (this.foodCounter == this.randomNumber) {
                foodCounter = 0;
                randomNumber =  random.nextInt(10);
            }
            snake.increaseBodyUnits();
            this.foodCounter = this.foodCounter + 1;
            updateScore();
            food.newFood();
            Audio clicked = new Audio("src/Audio/SnakeEat2.wav");
            clicked.audio.start();
            if (this.foodCounter == this.randomNumber) {
                toxicFood.newFood();
            }
            if (this.foodCounter == this.randomNumber2) {
                invincibleFood.newFood();
            }

        }
    }

    public void checkToxicFood () { //also separated methods to check the different foods
        if (this.foodCounter == this.randomNumber) { //Added a condition to fix logic
            if (snake.getX(0) == toxicFood.getFoodX() && snake.getY(0) == toxicFood.getFoodY()) {
                snake.decreaseBodyUnits();
                this.foodCounter = 0;
                this.randomNumber = random.nextInt(10);
                updateScore();
                Audio clicked = new Audio("src/Audio/SnakePoisonFruit.wav");
                clicked.audio.start();
                food.newFood();
            }
        }
    }


    public void checkInvincibleFood() {
        if (this.foodCounter == this.randomNumber2) {
            if (snake.getX(0) == invincibleFood.getFoodX() && snake.getY(0) == invincibleFood.getFoodY()) {
                this.foodCounter = 0;
                this.randomNumber2 = random.nextInt(10);
                activateInvincibility();
                food.newFood();
            }
        }
    }

    public void activateInvincibility() {
        invincible = true;
        Timer invincibilityTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invincible = false;
            }
        });
        invincibilityTimer.setRepeats(false); // Make the timer run only once
        invincibilityTimer.start();
    }


    private void snakeBodyCollision() {
        // self collision
        for (int i = snake.getBodyUnits(); i > 0; i--) {
            if ((snake.getX(0) == snake.getX(i)) && (snake.getY(0) == snake.getY(i))) {
                gameOver = true;
                running = false;
                Audio clicked = new Audio("src/Audio/SnakeGameOver.wav");
                clicked.audio.start();
                break;
            }

        }

    }


    public void snakeWallCollision() {
        // Snake Collides With Wall/ Panel
        /* if the snake head (x) is smaller than the left panel(0) or bigger than the right panel(500)
           or the snake head (y) is smaller than the upper panel(70) or bigger than the lower panel(570)
           game ends.
           here 0 may look like a magic number but it's not as we all know width and height size is 570
           it means the starting point is 0. So it the panel size goes from 70 --> 570; both side.
           */

        if ((snake.getX(0) < 0 || snake.getX(0) >= PANEL_WIDTH) || (snake.getY(0) < TOP_PANEL_HEIGHT || snake.getY(0) >= PANEL_HEIGHT + TOP_PANEL_HEIGHT)){
            gameOver = true;
            running = false;
            Audio clicked = new Audio("src/Audio/SnakeGameOver.wav");
            clicked.audio.start();
        }

    }

    public void updateScore () {
        if (snake.getX(0) == food.getFoodX() && snake.getY(0) == food.getFoodY()) {
            scoreCounter += 10;
        } else {
            scoreCounter = scoreCounter / 2;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(!running && !gameOver) {
            if (startScreen.isRepaint()) {
                repaint();
                startScreen.setRepaint(false);
            }
            if (startScreen.isActive()) {
                startGame();
                startScreen.setActive(false);
            }
        } else if (gameOver) {
            if (gameOverScreen.isRepaint()) {
                repaint();
                gameOverScreen.setRepaint(false);
            }
            if (gameOverScreen.isActive()) {
                restartGame();
                gameOverScreen.setActive(false);
            }
        }
        if (running) {
            snake.movement(direction);
            checkFood();
            checkToxicFood();
            checkInvincibleFood();
            snakeWallCollision();
            if (!invincible) {
                snakeBodyCollision();
            }
            startStopwatch();
        }
        stopStopwatch();
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


    // Game restarts when you press "R" in game over screen
    public void restartGame () {
        //Restart from a new position
        // Always restarts from the top left side.
        snake.setSnake(6);

        scoreCounter = 0;
        foodCounter = 0;
        randomNumber = random.nextInt(10);
        randomNumber2 = random.nextInt(10);

        playedSeconds = 0;
        tenthOfSecond = 0;

        direction = "Right";
        gameOver = false;

        startGame();

    }

    private void drawTopPanel(Graphics graphics){
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,PANEL_WIDTH, TOP_PANEL_HEIGHT);
        graphics.drawImage(logo.getImage(), 5,0,TOP_PANEL_HEIGHT, TOP_PANEL_HEIGHT, null);
    }
}

