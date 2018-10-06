package com.fahim.sunshine.yahoomodel;

/**
 * Created by HSBC on 02-12-2017.
 */

public class Location {

    public String city;
    public String country;
    public String region;

    public Location() {
    }

    public Location(String city, String country, String region) {
        this.city = city;
        this.country = country;
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}