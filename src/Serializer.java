import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Serializer {
    private Gson gson;

    public Serializer() {
        // Initializes Gson and registers the adapter
        gson = new GsonBuilder()
                .registerTypeAdapter(Snake.class, new SnakeAdapter())
                .create();
    }

    public void saveColorIndexToJsonFile(Snake snake) {
        String serializedSnake = gson.toJson(snake);
        // Save serialized snake data to a file
        try (FileWriter fileWriter = new FileWriter("snake_data.json")) {
            fileWriter.write(serializedSnake);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int loadColorIndexFromJsonFile(String filePath) {
        try (FileReader fileReader = new FileReader(filePath)) {
            Gson gson = new Gson();
            // Read the JSON data from the file to a Map<String, Object>
            Map<String, Object> jsonData = gson.fromJson(fileReader, Map.class);
            // Get the colorIndex value from the JSON data
            if (jsonData != null && jsonData.containsKey("colorIndex")) {
                Object colorIndexObj = jsonData.get("colorIndex");
                if (colorIndexObj instanceof Number) {
                    return ((Number) colorIndexObj).intValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0; // Default value if colorIndex couldn't be loaded
    }
}

