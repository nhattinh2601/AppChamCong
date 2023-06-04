package org.tensorflow.lite.examples.detection.model;

public class MainModel {
    String time,email;
    String surl="https://static.vecteezy.com/system/resources/previews/000/439/863/original/vector-users-icon.jpg";

    public String getSurl() {
        return surl;
    }


    public MainModel() {
    }

    public MainModel(String time, String email) {
        this.time = time;
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
