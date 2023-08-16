package ir.sharif.math.zahraSoukhtedel.gson;

import com.google.gson.*;

import java.lang.reflect.Type;

import static ir.sharif.math.zahraSoukhtedel.gson.Constants.*;

public class Deserializer<T> implements JsonDeserializer<T> {
    String token;
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String className = jsonObject.get(CLASSNAME).getAsString();
        if(jsonObject.get(TOKEN) != null)
            token = jsonObject.get(TOKEN).getAsString();
        Class<?> myClass;
        try {
            myClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
        return context.deserialize(jsonObject.get(INSTANCE), myClass);
    }

    public String getToken() { return token; }
}
