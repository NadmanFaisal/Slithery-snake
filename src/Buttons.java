import javax.swing.JButton;
import java.awt.*;

public class Buttons extends JButton {

    public Buttons(String buttonName){
        super(buttonName);

        this.setBackground(new Color(32,155,7));
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 20));
        this.setPreferredSize(new Dimension(80,25));
        setFocusable(false);
    }
}
