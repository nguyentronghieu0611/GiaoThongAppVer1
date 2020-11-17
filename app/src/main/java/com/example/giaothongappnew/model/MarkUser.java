package com.example.giaothongappnew.model;

public class MarkUser {
    private int user_id;
    private int error_id;
    private String name;
    private String time;
    private String error_name;

    public MarkUser(int user_id, int error_id, String time) {
        this.user_id = user_id;
        this.error_id = error_id;
        this.time = time;
    }

    public String getError_name() {
        return error_name;
    }

    public void setError_name(String error_name) {
        this.error_name = error_name;
    }

    public MarkUser(int user_id, int error_id, String name, String time, String error_name) {
        this.user_id = user_id;
        this.error_id = error_id;
        this.name = name;
        this.time = time;
        this.error_name = error_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getError_id() {
        return error_id;
    }

    public void setError_id(int error_id) {
        this.error_id = error_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
