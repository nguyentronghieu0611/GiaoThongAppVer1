package com.example.giaothongappnew.model;

public class AmercementLevel {
    private int error_id;
    private int vehical_id;
    private String amercement;

    public AmercementLevel(int error_id, int vehical_id, String amercement) {
        this.error_id = error_id;
        this.vehical_id = vehical_id;
        this.amercement = amercement;
    }

    public int getError_id() {
        return error_id;
    }

    public void setError_id(int error_id) {
        this.error_id = error_id;
    }

    public int getVehical() {
        return vehical_id;
    }

    public void setVehical(int vehical_id) {
        this.vehical_id = vehical_id;
    }

    public String getAmercement() {
        return amercement;
    }

    public void setAmercement(String amercement) {
        this.amercement = amercement;
    }
}
