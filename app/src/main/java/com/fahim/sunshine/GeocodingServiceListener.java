package com.fahim.sunshine;


import com.fahim.sunshine.yahoomodel.LocationResult;

public interface GeocodingServiceListener {

    void geocodeFailure(Exception exception);

    void geocodeSuccess(LocationResult location);
}
