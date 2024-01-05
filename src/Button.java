import javax.swing.JButton;
import java.awt.*;

public class Button extends JButton {
    /*
    The Button class extends JButton, creating a customized button with a specified label.
    It sets the background color to greenish (RGB: 32, 155, 7),
    the text color to light (RGB: 246, 248, 246), and uses a monospaced,
    bold font with a size of 17 points. Additionally,
    the button is configured to be non-focusable,improving the overall user interface in certain contexts.
    */
    public Button(String buttonName){
        super(buttonName);
        this.setBackground(new Color(32,155,7));
        this.setForeground(new Color(246, 248, 246));
        this.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
        setFocusable(false);
    }

}
