package com.fahim.sunshine.yahoomodel;

/**
 * Created by HSBC on 02-12-2017.
 */

public class Atmosphere {

    public String humidity;
    public String pressure;
    public String rising;
    public String visibility;

    public Atmosphere() {
    }

    public Atmosphere(String humidity, String pressure, String rising, String visibility) {
        this.humidity = humidity;
        this.pressure = pressure;
        this.rising = rising;
        this.visibility = visibility;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getRising() {
        return rising;
    }

    public void setRising(String rising) {
        this.rising = rising;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}