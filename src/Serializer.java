import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serializer {
    private Gson gson;

    public Serializer() {
        // Initializes Gson and registers the adapter
        gson = new GsonBuilder()
                .registerTypeAdapter(Snake.class, new SnakeAdapter())
                .create();
    }

    public String serializeSnake(Snake snake) {
        return gson.toJson(snake);
    }

    public Snake deserializeSnake(String json) {
        return gson.fromJson(json, Snake.class);
    }
}

