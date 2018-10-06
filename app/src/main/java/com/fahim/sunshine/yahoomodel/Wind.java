package com.fahim.sunshine.yahoomodel;

/**
 * Created by HSBC on 02-12-2017.
 */

public class Wind {

    public String chill;
    public String direction;
    public String speed;

    public Wind() {
    }

    public Wind(String chill, String direction, String speed) {
        this.chill = chill;
        this.direction = direction;
        this.speed = speed;
    }

    public String getChill() {
        return chill;
    }

    public void setChill(String chill) {
        this.chill = chill;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}