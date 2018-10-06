package com.fahim.sunshine.yahoomodel;

/**
 * Created by HSBC on 02-12-2017.
 */

public class Image {

    public String title;
    public String width;
    public String height;
    public String link;
    public String url;

    public Image() {
    }

    public Image(String title, String width, String height, String link, String url) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.link = link;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}