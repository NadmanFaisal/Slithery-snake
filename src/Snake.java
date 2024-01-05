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
    private Serializer serializer;

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
        this.serializer = new Serializer();
        this.colorIndex = serializer.loadColorIndexFromJsonFile("snake_data.json");

        this.snakeRightT = new ImageIcon("src/Images/" + this.colorList[this.colorIndex] + " Snake/RightT.png");
        this.snakeLeftT = new ImageIcon("src/Images/" + this.colorList[this.colorIndex] + " Snake/LeftT.png");
        this.snakeUpT = new ImageIcon("src/Images/" + this.colorList[this.colorIndex] + " Snake/UpT.png");
        this.snakeDownT = new ImageIcon("src/Images/" + this.colorList[this.colorIndex] + " Snake/DownT.png");
        this.snakeRight = new ImageIcon("src/Images/" + this.colorList[this.colorIndex] + " Snake/Right.png");
        this.snakeLeft = new ImageIcon("src/Images/" + this.colorList[this.colorIndex] + " Snake/Left.png");
        this.snakeUp = new ImageIcon("src/Images/" + this.colorList[this.colorIndex] + " Snake/Up.png");
        this.snakeDown = new ImageIcon("src/Images/" + this.colorList[this.colorIndex] + " Snake/Down.png");
        this.snakeBody = new ImageIcon("src/Images/" + this.colorList[this.colorIndex] + " Snake/Body.png");
    }

    public int getBodyUnits() {
        return bodyUnits;
    }

    // adds a bodyUnit to snake
    public void increaseBodyUnits() {
        this.bodyUnits += 1;
    }

    // halves the bodyUnits of the snake
    public void decreaseBodyUnits() {
        this.bodyUnits = this.bodyUnits / 2;
    }
    public int getX(int index) {
        return x[index];
    }

    public int getY(int index) {
        return y[index];
    }

    //This method gets the color index.
    public int getColorIndex() {
        return this.colorIndex;
    }

    //This method sets the previous color index to the newly provided one.
    public void setColorIndex(int newColor){
       this.colorIndex = newColor;
    }

    /*
    This method adds 1 to the colorIndex(hence referring to the next color in the array colorList) if it's not the equal
    to the last index on the array. If colorIndex is equal to the last index on the array, it is assigned 0( so it refers back
    to the first color in the array).

    It then stores the color in colorList's index equal to colorIndex in a string. It assigns the attributes for the
    snake images to images of the color stored in the string.

    It calls the method to store the new colorIndex to the JSON file.
     */
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

        serializer.saveColorIndexToJsonFile(this);
    }

    /*
    This returns the image for the head of the snake that corresponds to the direction of the snake. Every 5 moves, it
    returns the snake head image which includes the tongue. This gives the effect that the snake is moving its tongue.
     */
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


    //This draws the snake. It calls the method getSnakeHead() to get the image of the head in the right direction and
    //assigns all the images to their respective positions.
    public void drawSnake (Graphics graphics, String direction){
        ImageIcon head = getSnakeHead(direction);

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

    // It sets the snake's bodyUnits and positions the snake to begin at the centre of the game screen.
    public void setSnake(int bodyUnits) {
        this.bodyUnits = bodyUnits;

        for (int i = 0; i < this.bodyUnits; i++) {
            if (i == 0) {
                x[i] = 250;
                y[i] = 350;
            } else {
                x[i] = 250 - UNIT;
                y[i] = 350;

            }
        }
    }

    /*
    This method moves the snake. It starts at the last bodyUnit and moves the bodyUnits to the position of the bodyUnit
    before it, except the head. The head moves either up, down, left or right according to the direction. It adds 1 to
    the moveCounter.
     */
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
