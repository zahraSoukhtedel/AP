package ir.sharif.math.zahraSoukhtedel.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import static ir.sharif.math.zahraSoukhtedel.gson.Constants.*;

public class Serializer<T> implements JsonSerializer<T> {
    String token;
    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        String className = src.getClass().getName();
        retValue.addProperty(CLASSNAME, className);
        retValue.addProperty(TOKEN, token);
        JsonElement elem = context.serialize(src);
        retValue.add(INSTANCE, elem);
        return retValue;
    }

    public void setToken(String token) { this.token = token; }
}
