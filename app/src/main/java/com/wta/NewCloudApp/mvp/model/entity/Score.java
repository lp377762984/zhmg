package com.wta.NewCloudApp.mvp.model.entity;

public class Score {
    public String icon;
    public String name;
    public String time;
    public String score;

    public Score(String icon, String name, String time, String score) {
        this.icon = icon;
        this.name = name;
        this.time = time;
        this.score = score;
    }

    public Score(String name, String time, String score) {
        this.name = name;
        this.time = time;
        this.score = score;
    }
}
