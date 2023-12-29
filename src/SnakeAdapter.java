import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class SnakeAdapter extends TypeAdapter<Snake> {

    private static final int PANEL_WIDTH = 500;
    private static final int PANEL_HEIGHT = 500;
    private static final int UNIT = 25;
    private static final int GAME_UNITS = (PANEL_HEIGHT * PANEL_WIDTH) / (UNIT * UNIT);
    @Override
    public Snake read(JsonReader reader) throws IOException {
        reader.beginObject();
        int colorIndex = 0; // Default to 0 if color index is missing
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("colorIndex")) {
                colorIndex = reader.nextInt();
            }
        }
        reader.endObject();
        Snake snake = new Snake(UNIT, GAME_UNITS); // Initialize snake object
        snake.setColorIndex(colorIndex);
        return snake;

    }

    @Override
    public void write(JsonWriter writer, Snake snake) throws IOException {
        writer.beginObject();
        writer.name("colorIndex").value(snake.getColorIndex());
        writer.endObject();
    }
}


