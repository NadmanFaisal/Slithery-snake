import javax.swing.JButton;
import java.awt.*;

public class Button extends JButton {

    public Button(String buttonName){
        super(buttonName);
        this.setBackground(new Color(32,155,7));
        this.setForeground(new Color(246, 248, 246));
        this.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
        setFocusable(false);
    }

}
