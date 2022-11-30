package br.fiap.integrations.DroneApplication.dtos;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DroneDTO {

    @NotNull
    private int droneId;
    @NotNull
    private double temperature;
    @NotNull
    private BigDecimal latitude;
    @NotNull
    private BigDecimal longitude;
    @NotNull
    private double humidity;
    private boolean tracker;

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

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
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
}
