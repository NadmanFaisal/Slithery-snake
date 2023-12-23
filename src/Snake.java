import javax.swing.*;
import java.awt.*;

public class Snake {
    private final int UNIT;
    private final int[] x;
    private final int[] y;
    private int bodyUnits;
    private int moveCounter;

    private ImageIcon snakeRightT;
    private ImageIcon snakeLeftT;
    private ImageIcon snakeUpT;
    private ImageIcon snakeDownT;
    private ImageIcon snakeRight;
    private ImageIcon snakeLeft;
    private ImageIcon snakeUp;
    private ImageIcon snakeDown;
    private ImageIcon snakeBody;

    public Snake(int UNIT, int GAME_UNITS) {
        this.UNIT= UNIT;
        this.x = new int[GAME_UNITS];
        this.y = new int[GAME_UNITS];

        this.snakeRightT = new ImageIcon("src/Images/GreenSnake/Snake Right.png");
        this.snakeLeftT = new ImageIcon("src/Images/GreenSnake/Snake Left.png");
        this.snakeUpT = new ImageIcon("src/Images/GreenSnake/Snake Up.png");
        this.snakeDownT = new ImageIcon("src/Images/GreenSnake/Snake Down.png");
        this.snakeRight = new ImageIcon("src/Images/GreenSnake/Snake Right (no).png");
        this.snakeLeft = new ImageIcon("src/Images/GreenSnake/Snake Left (no).png");
        this.snakeUp = new ImageIcon("src/Images/GreenSnake/Snake Up (no).png");
        this.snakeDown = new ImageIcon("src/Images/GreenSnake/Snake Down (no).png");
        this.snakeBody = new ImageIcon("src/Images/GreenSnake/SnakeBody (circle).png");
    }
    public int getBodyUnits() {
        return bodyUnits;
    }
    public void increaseBodyUnits() {
        this.bodyUnits += 1;
    }
    public void decreaseBodyUnits() {
        this.bodyUnits = this.bodyUnits / 2;
    }
    public int getX(int index) {
        return x[index];
    }

    public int getY(int index) {
        return y[index];
    }
    private ImageIcon getSnakeHead(String direction) {
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
    public void drawSnake (Graphics graphics, String direction){
        ImageIcon head = getSnakeHead(direction);
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
    public void setSnake(int bodyUnits) {
        for (int i = 0; i < bodyUnits; i++) {
            x[i] = 0;
            y[i] = 100;
        }
        this.bodyUnits = bodyUnits;
    }

    public void movement (String direction) {

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

    }
}
