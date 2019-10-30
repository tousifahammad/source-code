package com.app.JainFurnishing.location;

public class Location {

    private String location_id;
    private String location_name;
    private String location_status;
    private String location_image;

    public Location(String location_id, String location_name, String location_status, String location_image) {
        this.location_id = location_id;
        this.location_name = location_name;
        this.location_status = location_status;
        this.location_image = location_image;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLocation_status() {
        return location_status;
    }

    public void setLocation_status(String location_status) {
        this.location_status = location_status;
    }

    public String getLocation_image() {
        return location_image;
    }

    public void setLocation_image(String location_image) {
        this.location_image = location_image;
    }
}
