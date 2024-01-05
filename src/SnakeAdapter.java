import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class SnakeAdapter extends TypeAdapter<Snake> {

    //This method uses a Json reader to extract the color index from the JSON data
    //and creates a Snake instance with the specified color index.
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
        createSnake().setColorIndex(colorIndex);
        return createSnake();

    }

    //This method uses a Json writer to retrieve the color index from the Snake object and writes it as a JSON attribute named "colorIndex".
    @Override
    public void write(JsonWriter writer, Snake snake) throws IOException {
        writer.beginObject();
        writer.name("colorIndex").value(snake.getColorIndex());
        writer.endObject();
    }

    //This method creates a Snake object with predefined properties like panel dimensions and unit sizes.
    public Snake createSnake(){
        int PANEL_WIDTH = 500;
        int PANEL_HEIGHT = 500;
        int UNIT = 25;
        int GAME_UNITS = (PANEL_HEIGHT * PANEL_WIDTH) / (UNIT * UNIT);

        return new Snake(UNIT, GAME_UNITS);
    }
}


