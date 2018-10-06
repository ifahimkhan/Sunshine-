package com.fahim.sunshine.yahoomodel;

/**
 * Created by HSBC on 02-12-2017.
 */

public class Units {

    public String distance;
    public String pressure;
    public String speed;
    public String temperature;

    public Units() {
    }

    public Units(String distance, String pressure, String speed, String temperature) {
        this.distance = distance;
        this.pressure = pressure;
        this.speed = speed;
        this.temperature = temperature;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
