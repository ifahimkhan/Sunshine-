package com.fahim.sunshine.yahoomodel;

/**
 * Created by HSBC on 02-12-2017.
 */

public class YahooObject {
    public Query query ;

    public YahooObject() {
    }

    public YahooObject(Query query) {
        this.query = query;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}