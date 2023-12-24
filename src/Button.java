import javax.swing.JButton;
import java.awt.*;

public class Button extends JButton {

    public Button(String buttonName){
        super(buttonName);
        this.setBackground(new Color(32,155,7));
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 17));
        setFocusable(false);
    }
}
