package br.fiap.integrations.DroneApplication.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "TB_DRONE")
public class Drone implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Drone(UUID id, int droneId, double temperature, Double latitude, Double longitude, double humidity, boolean tracker) {
        this.id = id;
        this.droneId = droneId;
        this.temperature = temperature;
        this.latitude = latitude;
        this.longitude = longitude;
        this.humidity = humidity;
        this.tracker = tracker;
    }

    public Drone() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getDroneId() {
        return droneId;
    }

    public void setDroneId(int droneId) {
        this.droneId = droneId;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public boolean isTracker() {
        return tracker;
    }

    public void setTracker(boolean tracker) {
        this.tracker = tracker;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", droneId=" + droneId +
                ", temperature=" + temperature +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", humidity=" + humidity +
                ", tracker=" + tracker +
                '}';
    }
}
