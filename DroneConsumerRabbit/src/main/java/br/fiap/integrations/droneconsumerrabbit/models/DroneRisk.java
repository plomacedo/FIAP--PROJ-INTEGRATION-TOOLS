package br.fiap.integrations.droneconsumerrabbit.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_DRONE_RISK")
public class DroneRisk implements Serializable {

    @Id
    private UUID id;
    @Column(nullable = false)
    private int droneId;
    @Column(nullable = false)
    private double temperature;
    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;
    @Column(nullable = false)
    private double humidity;
    @Column(nullable = false)
    private boolean tracker;

    @Override
    public String toString() {
        return  "\nDrone ID: " + droneId +
                "\nTemperature: " + temperature +
                "\nLatitude: " + latitude +
                "\nLongitude: " + longitude +
                "\nHumidity: " + humidity +
                "\nTracker: " + tracker +
                "\n--------------------------------" ;
    }
}
