package com.e.onlinedarshan;

public class GodModel {
private String name;
private String link;
private String img;

    public GodModel() {
    }

    public GodModel(String name, String link, String img) {
        this.name = name;
        this.link = link;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getImg() {
        return img;
    }
}
