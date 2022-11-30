package br.fiap.integrations.droneconsumerrabbit.util;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static JSONObject messageConverter(String fileBody) {
        fileBody = "{\"drones\":{\"drone\":" + fileBody + "}}";
        System.out.println( fileBody );
        return new JSONObject( fileBody );
    }

    public static List<JSONObject> validateDrone(JSONObject my_obj) {
        JSONObject drones = my_obj.getJSONObject( "drones" );
        JSONArray arrDrone = drones.getJSONArray( "drone" );
        List<JSONObject> riskDronesList = new ArrayList<>();

        for(int i = 0; i < arrDrone.length(); i++) {
            JSONObject drone = arrDrone.getJSONObject( i );
            if ((drone.getInt( "temperature" ) >= 35) || (drone.getInt( "temperature" ) <= 0) || (drone.getDouble( "humidity" ) < 15)) {
                riskDronesList.add( drone );
            }
        }
        return riskDronesList;
    }


}
