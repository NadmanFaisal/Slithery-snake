import javax.swing.*;
import java.awt.*;

public class Snake {
    private final int UNIT;
    private final int[] x;
    private final int[] y;
    private int bodyUnits;
    private int moveCounter;
    private String[] colorList;
    private  int colorIndex;

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
        this.colorList = new String[4];
        this.colorList[0] = "Green";
        this.colorList[1] = "Blue";
        this.colorList[2] = "Red";
        this.colorList[3] = "Yellow";
        this.colorIndex = 0;

        this.snakeRightT = new ImageIcon("src/Images/Green Snake/RightT.png");
        this.snakeLeftT = new ImageIcon("src/Images/Green Snake/LeftT.png");
        this.snakeUpT = new ImageIcon("src/Images/Green Snake/UpT.png");
        this.snakeDownT = new ImageIcon("src/Images/Green Snake/DownT.png");
        this.snakeRight = new ImageIcon("src/Images/Green Snake/Right.png");
        this.snakeLeft = new ImageIcon("src/Images/Green Snake/Left.png");
        this.snakeUp = new ImageIcon("src/Images/Green Snake/Up.png");
        this.snakeDown = new ImageIcon("src/Images/Green Snake/Down.png");
        this.snakeBody = new ImageIcon("src/Images/Green Snake/Body.png");
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
    public void changeSnakeColor() {
        if(this.colorIndex < colorList.length - 1) {
            this.colorIndex += 1;
        } else {
            this.colorIndex = 0;
        }

        String color = this.colorList[this.colorIndex];
        this.snakeRightT = new ImageIcon("src/Images/" + color + " Snake/RightT.png");
        this.snakeLeftT = new ImageIcon("src/Images/" + color + " Snake/LeftT.png");
        this.snakeUpT = new ImageIcon("src/Images/" + color + " Snake/UpT.png");
        this.snakeDownT = new ImageIcon("src/Images/" + color + " Snake/DownT.png");
        this.snakeRight = new ImageIcon("src/Images/" + color + " Snake/Right.png");
        this.snakeLeft = new ImageIcon("src/Images/" + color + " Snake/Left.png");
        this.snakeUp = new ImageIcon("src/Images/" + color + " Snake/Up.png");
        this.snakeDown = new ImageIcon("src/Images/" + color + " Snake/Down.png");
        this.snakeBody = new ImageIcon("src/Images/" + color + " Snake/Body.png");
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
