import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Serializer {
    private Gson gson;

    //This method is a constructor that initializes the Gson attribute with a custom adapter
    //called SnakeAdapter for the Snake class.
    public Serializer() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Snake.class, new SnakeAdapter())
                .create();
    }

    //This method serializes the Snake object to JSON format and saves it to a file named "snake_data.json".
    public void saveColorIndexToJsonFile(Snake snake) {
        String serializedSnake = gson.toJson(snake);
        // Save serialized snake data to a file
        try (FileWriter fileWriter = new FileWriter("snake_data.json")) {
            fileWriter.write(serializedSnake);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method reads the JSON data, extracts the "colorIndex" attribute, and returns its integer value.
    //If the file or attribute is missing it defaults to returning 0 (color index for the color green).
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
        return 0; // Default color will be green if colorIndex couldn't be loaded
    }
}

