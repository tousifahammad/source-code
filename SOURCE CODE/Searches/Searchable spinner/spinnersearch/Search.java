package com.app.baseproject.shared;

public class Search {
    private String name;
    private String location;
    private String image;

    public Search(String name, String location, String image) {
        this.name = name;
        this.location = location;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }
}
