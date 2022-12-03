package br.fiap.integrations.droneconsumerrabbit.util;
import org.json.JSONObject;

public class Utils {

    public static JSONObject messageConverter(String fileBody) {
        fileBody = "{\"drones\":{\"drone\":" + fileBody + "}}";
        System.out.println( fileBody );
        return new JSONObject( fileBody );
    }

}
