package com.fahim.sunshine.yahoomodel;

/**
 * Created by HSBC on 02-12-2017.
 */


public class Results {
    public Results(Channel channel) {
        this.channel = channel;
    }

    public Results() {
    }

    public Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

