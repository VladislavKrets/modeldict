package online.omnia.dict;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 25.08.2017.
 */
public class JsonModelListDeserializer implements JsonDeserializer<List<Model>>{
    @Override
    public List<Model> deserialize(JsonElement jsonElement, Type type,
                                   JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String status = object.get("status").getAsString();
        String message = object.get("message").getAsString();

        JsonArray data = object.get("data").getAsJsonArray();
        List<Model> models = new ArrayList<>();

        for (JsonElement element : data) {
            models.add(new Model(
                    element.getAsJsonObject().get("id").getAsInt(),
                    element.getAsJsonObject().get("value").getAsString()
            ));
        }

        return models;
    }
}
