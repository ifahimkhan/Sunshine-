package com.fahim.sunshine.yahoomodel;

import java.util.List;

/**
 * Created by HSBC on 02-12-2017.
 */

public class Item {
    public String title;
    public String lat;
    public String _long;
    public String link;
    public String pubDate;
    public Condition condition;
    public List<Forecast> forecast;
    public String description;
    public Guid guid;


    public Item() {
    }

    public Item(String title, String lat, String _long, String link, String pubDate, Condition condition, List<Forecast> forecast, String description, Guid guid) {
        this.title = title;
        this.lat = lat;
        this._long = _long;
        this.link = link;
        this.pubDate = pubDate;
        this.condition = condition;
        this.forecast = forecast;
        this.description = description;
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Guid getGuid() {
        return guid;
    }

    public void setGuid(Guid guid) {
        this.guid = guid;
    }
}