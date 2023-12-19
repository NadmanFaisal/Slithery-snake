import javax.swing.*;

public class GameLauncher {

    static GameFrame frame;

    public static void main (String[]args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new GameFrame();
            }
        });
    }

    public static void restart(){
        frame.deleteFrame();
        main(null);
    }
}