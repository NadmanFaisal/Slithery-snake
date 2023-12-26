import javax.swing.JButton;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Button extends JButton {

    private final Font customFont;

    public Button(String buttonName){
        super(buttonName);
        this.setBackground(new Color(32,155,7));
        this.setForeground(new Color(14, 102, 0));
        this.customFont = getFont("KarmaFuture.ttf");
        this.setFont(customFont.deriveFont(Font.BOLD, 17));
        setFocusable(false);
    }
    public Font getFont (String fontName){
        try {
            String path = "/Fonts/" + fontName;
            URL url = getClass().getResource(path);
            assert url != null;
            return Font.createFont(Font.TRUETYPE_FONT, url.openStream());
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

}
