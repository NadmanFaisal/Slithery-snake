
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

    public static final int PANEL_WIDTH = 500;
    public static final int PANEL_HEIGHT = 500;
    private static final int TOP_PANEL_HEIGHT = 70;
    private static final int UNIT = 25;
    private static final int GAME_UNITS = (PANEL_HEIGHT * PANEL_WIDTH) / (UNIT * UNIT);
    private static final int TIMER_DELAY = 100;
    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];
    private int bodyUnits;
    private int foodX;
    private int foodY;
    private int ToxicfoodX;
    private int ToxicfoodY;
    private int InvincibleFoodX;
    private int InvincibleFoodY;
    private String direction;
    private boolean running;
    private Random random;
    private Timer timer;
    private int foodCounter;
    private int randomNumber; //better name
    private int randomNumber2;
    private int scoreCounter;
    private boolean gameOver = false;
    private GameOverPanel gameOverPanel;

    private ImageIcon snakeRightT;
    private ImageIcon snakeLeftT;
    private ImageIcon snakeUpT;
    private ImageIcon snakeDownT;
    private ImageIcon snakeRight;
    private ImageIcon snakeLeft;
    private ImageIcon snakeUp;
    private ImageIcon snakeDown;
    private ImageIcon snakeBody;
    private ImageIcon berry;
    private ImageIcon evilBerry;
    private ImageIcon logo;
    private ImageIcon invincibleBerry;
    private int moveCounter;
    private Timer stopwatchTimer;  //timer attribute for the stopwatch of type timer
    private int playedSeconds; //attribute for the seconds that will go up as we play
    private int tenthOfSecond;
    private ImageIcon backgroundImage;
    private final Font customFont;
    private Buttons buttons;

    private StartScreen startButton;
    private boolean invincible = false;

    public GamePanel() {

        this.bodyUnits = 6;
        this.foodCounter = 0;
        this.moveCounter = 0;
        this.direction = "Right";
        this.running = false;
        this.random = new Random();
        this.randomNumber = random.nextInt(10);
        this.randomNumber2 = random.nextInt(10);
        this.scoreCounter = 0;
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT + TOP_PANEL_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(new MyKeyAdapter());
        this.gameOverPanel = new GameOverPanel();
        this.add(gameOverPanel);
        this.startButton = new StartScreen(PANEL_WIDTH,PANEL_HEIGHT + TOP_PANEL_HEIGHT);


        this.backgroundImage = new ImageIcon("src/Images/gamepanel-bg.png");
        this.snakeRightT = new ImageIcon("src/Images/Snake Right.png");
        this.snakeLeftT = new ImageIcon("src/Images/Snake Left.png");
        this.snakeUpT = new ImageIcon("src/Images/Snake Up.png");
        this.snakeDownT = new ImageIcon("src/Images/Snake Down.png");
        this.snakeRight = new ImageIcon("src/Images/Snake Right (no).png");
        this.snakeLeft = new ImageIcon("src/Images/Snake Left (no).png");
        this.snakeUp = new ImageIcon("src/Images/Snake Up (no).png");
        this.snakeDown = new ImageIcon("src/Images/Snake Down (no).png");
        this.snakeBody = new ImageIcon("src/Images/SnakeBody (circle).png");
        this.berry = new ImageIcon("src/Images/Strawberry.png");
        this.evilBerry = new ImageIcon("src/Images/EvilBerry.png");
        this.logo = new ImageIcon("src/Images/SnakeLogo.png");
        this.invincibleBerry = new ImageIcon("src/Images/InvincibleBerryMain.png");

        this.customFont = getFont("KarmaFuture.ttf");

        this.buttons = new Buttons("PLAY");
        this.buttons.addActionListener(e -> startGame());
        this.add(buttons);


        this.stopwatchTimer = new Timer(1000, this); //making the stopwatch a Timer (built-in java) object.
        this.playedSeconds = 0;
        this.tenthOfSecond = 0;
        startStopwatch(); //calling method to start the stopwatch when player starts playing
        this.addMouseListener(startButton);
        this.addMouseMotionListener(startButton);

        timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    public int getScoreCounter() {
        return this.scoreCounter;
    }

    public int getPlayedSeconds() {

        return this.playedSeconds;
    }

    public int getTenthOfSecond() {
        return this.tenthOfSecond;
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
            startButton.drawStartMenu(graphics, 250, logo);

        }else{
            drawTopPanel(graphics);
            drawBackgroundImage(graphics);
            drawFood(graphics);
            drawSnake(graphics);
            drawScore(graphics);
            drawStopwatchLabel(graphics);

        }
        if (gameOver) {
            gameOverPanel.setVisible(true);
            gameOverPanel.showGameOverScreen(graphics);

        }
    }
    public void drawStopwatchLabel (Graphics graphics) {
        if ( !gameOver ) {
            graphics.setColor(new Color(14, 102, 0));
            graphics.setFont(customFont.deriveFont(Font.BOLD, 25));
            graphics.drawString("Time: "+ playedSeconds + "." + tenthOfSecond + " seconds", PANEL_WIDTH -250, 25 );
        }
        if (gameOver) {
            gameOverPanel.setVisible(true);
            gameOverPanel.showGameOverScreen(graphics);
        }
    }
    private ImageIcon getHead() {
        ImageIcon head = null;

        switch (direction) {
            case ("Right") -> {
                if (moveCounter % 5 == 0) {
                    head = snakeRightT;
                } else {
                    head = snakeRight;
                }
            }
            case ("Left") -> {
                if (moveCounter % 5 == 0) {
                    head = snakeLeftT;
                } else {
                    head = snakeLeft;
                }
            }
            case ("Up") -> {
                if (moveCounter % 5 == 0) {
                    head = snakeUpT;
                } else {
                    head = snakeUp;
                }
            }
            case ("Down") -> {
                if (moveCounter % 5 == 0) {
                    head = snakeDownT;
                } else {
                    head = snakeDown;
                }
            }
        }
        return head;
    }



    public void drawSnake (Graphics graphics){
        ImageIcon head = getHead();
        //snake
        switch (direction) {
            case "Right", "Down" -> {
                for (int i = 0; i < bodyUnits; i++) {
                    if (i == 0) {
                        graphics.drawImage(head.getImage(), x[i], y[i], null);
                    } else {
                        graphics.drawImage(snakeBody.getImage(), x[i], y[i], null);
                    }
                }
            }
            case "Left" -> {
                for (int i = 0; i < bodyUnits; i++) {
                    if (i == 0) {
                        if (head.equals(snakeLeftT)) {
                            graphics.drawImage(head.getImage(), x[i] - 10, y[i], null);
                        } else {
                            graphics.drawImage(head.getImage(), x[i], y[i], null);
                        }
                    } else {
                        graphics.drawImage(snakeBody.getImage(), x[i], y[i], null);
                    }
                }
            }
            case "Up" -> {
                for (int i = 0; i < bodyUnits; i++) {
                    if (i == 0) {
                        if (head.equals(snakeUpT)) {
                            graphics.drawImage(head.getImage(), x[i], y[i] - 10, null);
                        } else {
                            graphics.drawImage(head.getImage(), x[i], y[i], null);
                        }


                    } else {
                        graphics.drawImage(snakeBody.getImage(), x[i], y[i], null);
                    }
                }
            }
        }
    }

    public void drawFood (Graphics graphics){
        //food item
        graphics.drawImage(berry.getImage(), foodX, foodY, null);

        //ToxicFood item
        if (foodCounter == randomNumber) {
            graphics.drawImage(evilBerry.getImage(), ToxicfoodX, ToxicfoodY, null);
        }

        if (foodCounter == randomNumber2) {
            graphics.drawImage(invincibleBerry.getImage(), InvincibleFoodX, InvincibleFoodY, null);
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
        graphics.drawString("Score: " + scoreCounter,10,graphics.getFont().getSize() + 70);
    }

    public void drawBackgroundImage(Graphics graphics){
        graphics.drawImage(backgroundImage.getImage(), 0, TOP_PANEL_HEIGHT, PANEL_WIDTH, PANEL_HEIGHT, this);
    }

    public void startGame() {

        buttons.setVisible(true);

        for (int i = 0; i< bodyUnits; i++){
            x[i] = 0;
            y[i] = TOP_PANEL_HEIGHT;
        }

        newFood();
        running = true;
        if(timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = new Timer(TIMER_DELAY, this);
        timer.start();
        startStopwatch();

    }

    public void newFood () {
        foodX = random.nextInt(PANEL_WIDTH / UNIT) * UNIT; //will it ever appear at corner??
        foodY = random.nextInt(PANEL_HEIGHT / UNIT) * UNIT + TOP_PANEL_HEIGHT;
    }

    public void newInvincibleFood() {
        InvincibleFoodX = random.nextInt(PANEL_WIDTH / UNIT) * UNIT;
        InvincibleFoodY = random.nextInt(PANEL_HEIGHT / UNIT) * UNIT + TOP_PANEL_HEIGHT;
    }

    public void newToxicFood() {
        ToxicfoodX = random.nextInt(PANEL_WIDTH / UNIT) * UNIT;
        ToxicfoodY = random.nextInt(PANEL_HEIGHT / UNIT) * UNIT + TOP_PANEL_HEIGHT;
    }

    public void movement () {

        for (int i = bodyUnits; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        moveCounter += 1;

        switch (direction) {
            case "Right" -> x[0] = x[0] + UNIT;
            case "Left" -> x[0] = x[0] - UNIT;
            case "Up" -> y[0] = y[0] - UNIT;
            case "Down" -> y[0] = y[0] + UNIT;
        }

        snakeWallCollision();

        if (!invincible) {
            snakeBodyCollision();
        }

    }

    public void checkFood () { //changed logic in if block
        if ((x[0] == foodX && y[0] == foodY)) {
            this.bodyUnits = this.bodyUnits + 1;
            this.foodCounter = this.foodCounter + 1;
            updateScore();
            newFood();
            Audio clicked = new Audio("src/SnakeEat2.wav");
            clicked.audio.start();
            if (this.foodCounter == this.randomNumber) {
                newToxicFood();
            }
            if (this.foodCounter == this.randomNumber2) {
                newInvincibleFood();
            }
        }
    }

    public void checkToxicFood () { //also separated methods to check the different foods
        if (this.foodCounter == this.randomNumber) { //Added a condition to fix logic
            if (x[0] == ToxicfoodX && y[0] == ToxicfoodY) {
                bodyUnits = bodyUnits / 2;
                this.foodCounter = 0;
                this.randomNumber = random.nextInt(10);
                updateScore();
                Audio clicked = new Audio("src/SnakePoisonFruit.wav");
                clicked.audio.start();
                newFood();
            }
        }
    }


    public void checkInvincibleFood() {
        if (this.foodCounter == this.randomNumber2) {
            if (x[0] == InvincibleFoodX && y[0] == InvincibleFoodY) {
                this.foodCounter = 0;
                this.randomNumber2 = random.nextInt(10);
                activateInvincibility();
                newFood();
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
        for (int i = bodyUnits; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                gameOver = true;
                running = false;
                Audio clicked = new Audio("src/SnakeGameOver.wav");
                clicked.audio.start();
                timer.stop();
                break;
            }

        }

        if(gameOver) {
            this.gameOverPanel.setScoreCounter(scoreCounter);
            this.gameOverPanel.setTime(playedSeconds,tenthOfSecond);
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

        if ((x[0] < 0 || x[0] >= PANEL_WIDTH) || (y[0] < TOP_PANEL_HEIGHT || y[0] >= PANEL_HEIGHT + TOP_PANEL_HEIGHT)){
            gameOver = true;
            running = false;
            Audio clicked = new Audio("src/SnakeGameOver.wav");
            clicked.audio.start();
            timer.stop();
        }

        if(gameOver) {
            this.gameOverPanel.setScoreCounter(scoreCounter);
            this.gameOverPanel.setTime(playedSeconds,tenthOfSecond);

        }

    }

    public void updateScore () {
        if (x[0] == foodX && y[0] == foodY) {
            scoreCounter += 10;
        } else {
            scoreCounter = scoreCounter / 2;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (startButton.isRepaint()) {
            repaint();
            startButton.setRepaint(false);
        }
        if (!running && startButton.isActive()) {
            startGame();
            startButton.setActive(false);
        }
        if (running) {
            movement();
            checkFood();
            checkToxicFood();
            checkInvincibleFood();
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
                case (KeyEvent.VK_R) -> {
                    if (gameOver) {
                        restartGame();
                    }
                }
            }
        }
    }


    // Game restarts when you press "R" in game over screen
    public void restartGame () {
        //Restart from a new position
        // Always restarts from the top left side.
        for (int i = 0; i < bodyUnits; i++) {
            x[i] = 0;
            y[i] = 0;
        }

        bodyUnits = 6;
        scoreCounter = 0;
        randomNumber = random.nextInt(10);

        playedSeconds = 0;
        tenthOfSecond = 0;

        direction = "Right";
        running = false;
        gameOver = false;

        startGame();

    }

    private void drawTopPanel(Graphics graphics){
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,PANEL_WIDTH, TOP_PANEL_HEIGHT);
        graphics.drawImage(logo.getImage(), 5,0,TOP_PANEL_HEIGHT, TOP_PANEL_HEIGHT, null);
    }
}

