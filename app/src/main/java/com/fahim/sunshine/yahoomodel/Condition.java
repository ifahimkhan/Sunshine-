package com.fahim.sunshine.yahoomodel;

/**
 * Created by HSBC on 02-12-2017.
 */

public class Condition {

    public String code;
    public String date;
    public String temp;
    public String text;

    public Condition() {
    }

    public Condition(String code, String date, String temp, String text) {
        this.code = code;
        this.date = date;
        this.temp = temp;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}