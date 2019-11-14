package com.app.haircutuser.mybooking;

import java.io.Serializable;

public class Booking implements Serializable {
    private String vendor_id;
    private String booking_id;
    private String name;
    private String location;
    private String booking_type;
    private String booking_date;
    private String booking_time;
    private String service_id;
    private String price;
    private String service_started;
    private String servicename;
    private String saloonname;
    private String status;
    private String is_reminder;


    public Booking(String vendor_id, String booking_id, String name, String location, String booking_type, String booking_date, String booking_time, String service_id, String price, String service_started, String servicename, String saloonname, String status, String is_reminder) {
        this.vendor_id = vendor_id;
        this.booking_id = booking_id;
        this.name = name;
        this.location = location;
        this.booking_type = booking_type;
        this.booking_date = booking_date;
        this.booking_time = booking_time;
        this.service_id = service_id;
        this.price = price;
        this.service_started = service_started;
        this.servicename = servicename;
        this.saloonname = saloonname;
        this.status = status;
        this.is_reminder = is_reminder;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getBooking_type() {
        return booking_type;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public String getBooking_time() {
        return booking_time;
    }

    public String getService_id() {
        return service_id;
    }

    public String getPrice() {
        return price;
    }

    public String getService_started() {
        return service_started;
    }

    public String getServicename() {
        return servicename;
    }

    public String getSaloonname() {
        return saloonname;
    }

    public String getStatus() {
        return status;
    }

    public String getIs_reminder() {
        return is_reminder;
    }
}
