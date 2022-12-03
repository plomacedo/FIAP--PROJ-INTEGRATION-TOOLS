package br.fiap.integrations.droneconsumerrabbit.services;

import br.fiap.integrations.droneconsumerrabbit.models.DroneRisk;
import br.fiap.integrations.droneconsumerrabbit.repositories.DroneRiskRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DroneRiskService {

    final DroneRiskRepository droneRiskRepository;

    public DroneRiskService(DroneRiskRepository droneRiskRepository) {
        this.droneRiskRepository = droneRiskRepository;
    }

    @Transactional
    public DroneRisk save(DroneRisk droneRisk) {
        return droneRiskRepository.save( droneRisk );
    }

    public List<DroneRisk> findAll() {
        return droneRiskRepository.findAll();
    }

    public Optional<DroneRisk> findById(UUID id) {
        return droneRiskRepository.findById( id );
    }

    public static List<DroneRisk> checkDrones(JSONObject my_obj) {
        JSONObject drones = my_obj.getJSONObject( "drones" );
        JSONArray arrDrone = drones.getJSONArray( "drone" );

        List<DroneRisk> riskDronesList = new ArrayList<>();

        for (int i = 0; i < arrDrone.length(); i++) {
            JSONObject drone = arrDrone.getJSONObject( i );

            if ((drone.getInt( "temperature" ) >= 35) || (drone.getInt( "temperature" ) <= 0) || (drone.getDouble( "humidity" ) < 15)) {
                System.out.println(drone);
                
                DroneRisk droneRisk = new DroneRisk();
                droneRisk.setId( UUID.fromString( drone.getString( "id" ) ) );
                droneRisk.setDroneId( drone.getInt( "droneId" ) );
                droneRisk.setTemperature( drone.getInt( "temperature" ) );
                droneRisk.setLatitude( drone.getDouble( "latitude" ) );
                droneRisk.setLongitude( drone.getDouble( "longitude" ) );
                droneRisk.setHumidity( drone.getDouble( "humidity" ) );
                droneRisk.setTracker( drone.getBoolean( "tracker" ) );

                riskDronesList.add(droneRisk);
            }
        }
        return riskDronesList;
    }
}

