package com.example.myproject;

public class BusSelect {
    public String  driver_name,contact_no, bus_no, bus_name, bus_details;

    public String getDriver_name() {
        return driver_name;
    }
    public BusSelect() {

    }

    public BusSelect(String driver_name, String contact_no, String bus_no, String bus_name, String bus_details) {
        this.driver_name = driver_name;
        this.contact_no = contact_no;
        this.bus_no = bus_no;
        this.bus_name = bus_name;
        this.bus_details = bus_details;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getBus_no() {
        return bus_no;
    }

    public void setBus_no(String bus_no) {
        this.bus_no = bus_no;
    }

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }

    public String getBus_details() {
        return bus_details;
    }

    public void setBus_details(String bus_details) {
        this.bus_details = bus_details;
    }
}
