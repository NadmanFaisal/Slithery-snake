import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    private static final int PANEL_WIDTH = 500;
    private static final int PANEL_HEIGHT = 500;
    private static final int TOP_PANEL_HEIGHT = 100;
    private static final int UNIT = 25;
    private static final int GAME_UNITS = (PANEL_HEIGHT * PANEL_WIDTH) / (UNIT * UNIT);

    private Snake snake;
    private Food food;
    private Food invincibleFood;
    private Food toxicFood;
    private GameOverScreen gameOverScreen;
    private StartScreen startScreen;

    private Random random;
    private Timer timer;
    private Timer stopwatchTimer;  
    private Button playButton;
    private Button changeColor;

    private String direction;
    private boolean started;
    private boolean running;
    private boolean gameOver;
    private boolean invincible;

    private final int TIMER_DELAY = 100;
    private int foodCounter;
    private int randomNumber;
    private int randomNumber2;
    private int scoreCounter;
    private int playedSeconds; 
    private int tenthOfSecond;

    private ImageIcon logo;
    private ImageIcon backgroundImage;
    private final Font customFont;

    public GamePanel() {
        this.direction = "Right";
        this.started = false;
        this.running = false;
        this.gameOver = false;
        this.invincible = false;
        this.scoreCounter = 0;

        this.foodCounter = 0;
        this.random = new Random();
        this.randomNumber = random.nextInt(10);
        this.randomNumber2 = random.nextInt(10);

        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT + TOP_PANEL_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(new MyKeyAdapter());

        this.snake = new Snake(UNIT, GAME_UNITS);
        this.food = new Food(PANEL_WIDTH, PANEL_HEIGHT, TOP_PANEL_HEIGHT, UNIT, "src/Images/Strawberry.png");
        this.toxicFood = new Food(PANEL_WIDTH, PANEL_HEIGHT, TOP_PANEL_HEIGHT, UNIT, "src/Images/EvilBerry.png");
        this.invincibleFood = new Food(PANEL_WIDTH, PANEL_HEIGHT, TOP_PANEL_HEIGHT, UNIT, "src/Images/InvincibleBerryMain.png");

        this.gameOverScreen = new GameOverScreen(PANEL_WIDTH, PANEL_HEIGHT + TOP_PANEL_HEIGHT);
        this.addMouseListener(gameOverScreen);
        this.addMouseMotionListener(gameOverScreen);


        this.startScreen = new StartScreen(PANEL_WIDTH, PANEL_HEIGHT + TOP_PANEL_HEIGHT);
        this.addMouseListener(startScreen);
        this.addMouseMotionListener(startScreen);

        this.backgroundImage = new ImageIcon("src/Images/Background.jpeg");
        this.logo = new ImageIcon("src/Images/SnakeLogo.png");
        this.customFont = getFont("KarmaFuture.ttf");

        this.playButton = new Button("Play");
        this.playButton.addActionListener(this);
        this.playButton.setBounds(120, 30, 100, 40);
        this.playButton.setVisible(false);

        this.changeColor = new Button("Change Color"); //SHOULD IT BE "SWITCH COLOR"??
        this.changeColor.addActionListener(this);
        this.changeColor.setBounds(230, 30, 160, 40);
        this.changeColor.setVisible(false);

        this.stopwatchTimer = new Timer(1000, this); 
        this.playedSeconds = 0;
        this.tenthOfSecond = 0;

        timer = new Timer(TIMER_DELAY, this);

        this.add(playButton);
        this.add(changeColor);
        snake.setSnake(6);

        timer.start();
    }

    //This method takes the name of the font file as an argument and uses it to construct a path,
    //retrieve the font file's URL and create a font object. If the file is not found, a runtime exception is thrown.
    public Font getFont(String fontName) {
        try {
            String path = "/Fonts/" + fontName;
            URL url = getClass().getResource(path);
            return Font.createFont(Font.TRUETYPE_FONT, url.openStream());
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    //Starts the stopwatch when the user starts playing. Counts seconds and tenths of seconds. 
    public void startStopwatch() { 
        tenthOfSecond = tenthOfSecond + 1;
        if (tenthOfSecond == 10) {
            tenthOfSecond = 0;
            playedSeconds = playedSeconds + 1;
        }
        stopwatchTimer.start();
    }

    //Stops the stopwatch.
    public void stopStopwatch() {
        stopwatchTimer.stop();
    }

    /*
        This method calls to draw the Game screen according to some conditions:
            - It would draw the start game screen, if the game has not been started, by calling
              startScreen.drawStartMenu() method.

            - If the game had been started and gameOver was true due to the snake colliding with either a wall or itself,
              it set the buttons of the game screen to not be visible and would call the
              gameOverScreen.showGameOverScreen() method.

            - Otherwise if the game was started but gameOver was false, it would call methods to draw the top panel, the
              background image, Additionally, if running was true due to the user clicking the play button, it would call
              the draw food methods to draw the food, the toxic food, if foodCounter was equal to randomNumber, and the
              invincible food, if foodCounter was equal to randomNumber2. Then it would also call the methods to draw
              the score and the stopwatch label.

    */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (!started) {
            startScreen.drawStartMenu(graphics, 250, logo);

        } else if (gameOver) {
            playButton.setVisible(false);
            changeColor.setVisible(false);
            gameOverScreen.showGameOverScreen(graphics, scoreCounter, playedSeconds, tenthOfSecond);

        } else {
            drawTopPanel(graphics);
            drawBackgroundImage(graphics);

            if (running) {
                if (foodCounter == randomNumber) {
                    toxicFood.drawFood(graphics);
                }
                if (foodCounter == randomNumber2) {
                    invincibleFood.drawFood(graphics);
                }
                food.drawFood(graphics);
            }
            this.snake.drawSnake(graphics, direction);

            drawScore(graphics);
            drawStopwatchLabel(graphics);
        }

    }

    //Makes the stopwatch visible for the player on the game screen when the game is running.
    public void drawStopwatchLabel(Graphics graphics) {
        if (!gameOver) {
            graphics.setColor(new Color(14, 102, 0));
            graphics.setFont(customFont.deriveFont(Font.BOLD, 25));
            graphics.drawString("Time: " + playedSeconds + "." + tenthOfSecond + " seconds", PANEL_WIDTH - 250, graphics.getFont().getSize() + TOP_PANEL_HEIGHT);
        }
    }

    //This method draws the score on the screen using the provided graphics object
    //and sets the color, font style and size of the text along with the placement of it.
    public void drawScore(Graphics graphics) {
        graphics.setColor(new Color(14, 102, 0));
        graphics.setFont(customFont.deriveFont(Font.BOLD, 25));
        graphics.drawString("Score: " + scoreCounter, 10, graphics.getFont().getSize() + TOP_PANEL_HEIGHT);
    }
    /*
      This Java code defines a method to draw a graphical user interface top panel.
      It uses Java's graphics functionalities to set background and border colors,
      draw lines for the panel border, and display a logo image. Additionally,
      it adjusts the stroke thickness for the border and sets the visibility
      of play and color-changing buttons within the panel.
     */
    private void drawTopPanel(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics.setColor(new Color(221, 244, 155));
        graphics.fillRect(0, 0, PANEL_WIDTH, TOP_PANEL_HEIGHT);

        graphics.setColor(new Color(14, 102, 0));
        graphics2D.setStroke(new BasicStroke(6));
        graphics2D.drawLine(0, 0, PANEL_WIDTH, 0);
        graphics2D.drawLine(0, TOP_PANEL_HEIGHT, PANEL_WIDTH, TOP_PANEL_HEIGHT);
        graphics2D.drawLine(0, 0, 0, TOP_PANEL_HEIGHT);
        graphics2D.drawLine(PANEL_WIDTH, 0, PANEL_WIDTH, TOP_PANEL_HEIGHT);

        graphics2D.setStroke(new BasicStroke(0));

        graphics.drawImage(logo.getImage(), 5, 0, TOP_PANEL_HEIGHT, TOP_PANEL_HEIGHT, null);
        playButton.setVisible(true);
        changeColor.setVisible(true);
    }

    //This method draws the background image on the screen using the provided graphics object.
    public void drawBackgroundImage(Graphics graphics) {
        graphics.drawImage(backgroundImage.getImage(), 0, TOP_PANEL_HEIGHT, PANEL_WIDTH, PANEL_HEIGHT, this);
    }

    /*
        This method is called when play button is clicked to start playing. The snake is set to beginning position and
        bodyUnits by calling snake.setSnake(). FoodCounter, randomNumber, randomNumber2 and direction are reassigned
        their original values. gameOver is set false and running is true. If a timer is already running, it is
        stopped. A new timer starts.
     */
    public void startGame() {
        gameOverScreen.setActive(false);

        this.snake.setSnake(6);

        foodCounter = 0;
        randomNumber = random.nextInt(10);
        randomNumber2 = random.nextInt(10);

        direction = "Right";
        gameOver = false;
        running = true;

        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    /*
        This method checks if the head of the snake is at the same coordinates as the food. If so:
            - It checks whether the foodCounter is equal to the randomNumber, which means the toxic food was also an
              option. If it is true, the foodCounter and randomNumber is reset.
            - It calls to increase the snake's bodyUnit by adding 1 more
            - It adds 1 to the foodCounter as the food is eaten.
            - It updates the score and calls the method to create a new food on the screen and plays the audio selected.
            - If the foodCounter now equals to randomNumber, it calls the method to create new toxic food, if foodCounter
              equals randomNumber2, it calls the method to create new invincible food.
     */
    public void checkFood() {
        if ((snake.getX(0) == food.getFoodX() && snake.getY(0) == food.getFoodY())) {
            if (this.foodCounter == this.randomNumber) {
                foodCounter = 0;
                randomNumber = random.nextInt(10);
            }
            snake.increaseBodyUnits();
            this.foodCounter = this.foodCounter + 1;
            updateScore();
            food.newFood();
            Audio clicked = new Audio("src/Audio/SnakeEat3.wav");
            clicked.audio.start(); // starting the playback
            if (this.foodCounter == this.randomNumber) {
                toxicFood.newFood();
            }
            if (this.foodCounter == this.randomNumber2) {
                invincibleFood.newFood();
            }

        }
    }

    /*
    This method checks if the toxic food is visible, if it is, it checks whether the snake's head is at the same
    coordinates as the toxic food. If so, it halves the bodyUnits of the snake by calling the respective method, resets
    the values of the foodCounter and the randomNumber, updates the score to half, plays the selected audio and calls
    for normal new food to be created.
     */
    public void checkToxicFood() {
        if (this.foodCounter == this.randomNumber) {
            if (snake.getX(0) == toxicFood.getFoodX() && snake.getY(0) == toxicFood.getFoodY()) {
                snake.decreaseBodyUnits();
                this.foodCounter = 0;
                this.randomNumber = random.nextInt(10);
                updateScore();
                Audio clicked = new Audio("src/Audio/SnakePoisonFruit4.wav");
                clicked.audio.start();
                food.newFood();
            }
        }
    }

    // Checks whether snake head collides with invincible food
    public void checkInvincibleFood() {
        if (this.foodCounter == this.randomNumber2) {
            if (snake.getX(0) == invincibleFood.getFoodX() && snake.getY(0) == invincibleFood.getFoodY()) {
                this.foodCounter = 0;
                this.randomNumber2 = random.nextInt(10);
                activateInvincibility();
                Audio clicked = new Audio("src/Audio/SnakeGameInvincible.wav");
                clicked.audio.start();
                food.newFood();
            }
        }
    }

    // Changes invincibility boolean to true for 10 seconds
    public void activateInvincibility() {
        invincible = true;
        Timer invincibilityTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invincible = false;
            }
        });

        // Makes the timer run only once
        invincibilityTimer.setRepeats(false);
        invincibilityTimer.start();
    }


    // Causes Game Over if the snake collides with itself. 
    private void snakeBodyCollision() {
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

    // Snake Collides With Wall/ Panel
        /* if the snake head (x) is smaller than the left panel(0) or bigger than the right panel(500)
           or the snake head (y) is smaller than the upper panel(70) or bigger than the lower panel(570)
           game ends.
           here 0 may look like a magic number, but it's not as we all know width and height size is 570
           it means the starting point is 0. So it the panel size goes from 70 --> 570; both side.
           */

    // Causes the snake to collide with the wall
    public void snakeWallCollision() {
        if ((snake.getX(0) < 0 || snake.getX(0) >= PANEL_WIDTH) || (snake.getY(0) < TOP_PANEL_HEIGHT || snake.getY(0) >= PANEL_HEIGHT + TOP_PANEL_HEIGHT)) {
            gameOver = true;
            running = false;
            Audio clicked = new Audio("src/Audio/SnakeGameOver.wav");
            clicked.audio.start();
        }

    }

    //This method updates the score depending on the type of food that has been eaten. If the snake eats a normal berry,
    //the player earns 10 points and the score is updated. If an evil berry is eaten, the player loses half of their earned points
    //and the score is updated.
    public void updateScore() {
        if (snake.getX(0) == food.getFoodX() && snake.getY(0) == food.getFoodY()) {
            scoreCounter += 10;
        } else {
            scoreCounter = scoreCounter / 2;
        }
    }

    //Resets the game to start mode. Snake length starts at 6 bodyunits. Stopwatch and score is reset to 0. 
    public void resetGame() {
        gameOver = false;
        snake.setSnake(6);
        scoreCounter = 0;
        playedSeconds = 0;
        tenthOfSecond = 0;
    }

    /*
    This method does different functions for different conditions:

    If the game has not been started, it checks whether the repaint attribute of the startScreen is true, which means
    mouse is hovering over the start button. If it's true, it calls repaint and sets the repaint attribute to false via
    the setter. It then checks if the active attribute of startScreen is true, which means the button has been clicked.
    If it's true, it sets started to true and active to false, via a setter.

    Else if the game has been started but game is over (gameOver is true), it checks whether the repaint attribute of the
    gameOverScreen is true, which means the mouse is hovering over the restart button. If it's true, it calls repaint()
    and sets the repaint attribute to false via the setter. It then checks if the active attribute of gameOverScreen is
    true, which means the button has been clicked. If it's true, it calls reset() and sets active to false, via the
    setter.

    Else if the game has been started and both running and gameOver are false, it checks if the play button or
    changeColor buttons have been pressed. If play is presses, it calls to start playing the game. If changeColor is
    pressed it switches to the next color.

    Else if game has started, gameOver is false and running is true, it calls the snake movement,checks whether the
    snake has eaten any foods or had a collision and calls to start the stopwatch.

    This method always calls the repaint method and the stopStopWatch method.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!started) {
            if (startScreen.isRepaint()) {
                repaint();
                startScreen.setRepaint(false);
            }
            if (startScreen.isActive()) {
                started = true;
                startScreen.setActive(false);
            }

        } else if (gameOver) {
            if (gameOverScreen.isRepaint()) {
                repaint();
                gameOverScreen.setRepaint(false);
            }
            if (gameOverScreen.isActive()) {
                resetGame();
                gameOverScreen.setActive(false);
            }

        } else if (!running) {
            if (e.getSource() == playButton) {
                startGame();
            }

            if (e.getSource() == changeColor) {
                snake.changeSnakeColor();
            }

        } else {
            snake.movement(direction);
            checkFood();
            checkToxicFood();
            checkInvincibleFood();
            snakeWallCollision();

            // If invincible is false, snake can collide with itself only then
            if (!invincible) {
                snakeBodyCollision();
            }
            startStopwatch();

        }

        stopStopwatch();
        repaint();
    }

    //This method changes the direction of the snake in the game according to the arrow key pressed.
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

