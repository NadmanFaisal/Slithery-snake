import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameButtons extends JButton {

    public GameButtons(String buttonText) {
        this.setText(buttonText);
        this.setFont(new Font("Georgia", Font.PLAIN, 25));
        this.setFocusable(false);
        this.setOpaque(true);
        this.setForeground(Color.BLACK);
        this.setFocusPainted(false);

    }
}



