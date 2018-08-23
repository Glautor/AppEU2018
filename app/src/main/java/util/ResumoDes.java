package util;

import com.example.usuario.telaswendel.Resumo;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ResumoDes implements JsonDeserializer<Resumo> {
    @Override
    public Resumo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement resumo = json.getAsJsonObject();

        if(json.getAsJsonObject().get("resumo") != null){
            resumo = json.getAsJsonObject();
        }

        return (new Gson().fromJson(resumo, Resumo.class));
    }
}
