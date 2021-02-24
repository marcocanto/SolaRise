package com.example.solarise.models;

import java.util.ArrayList;
import java.util.List;

public class Day {

    String sleep_time;
    String wakeup_time;
    int rating;
    List<Coffee> caffeine_intake = new ArrayList<Coffee>();

    public Day() {}

    public Day(String sleep_time, String wakeup_time, int rating) {
        this.sleep_time = sleep_time;
        this.wakeup_time = wakeup_time;
        this.rating = rating;
    }

    public List<Coffee> getCaffeine_intake() {
        return caffeine_intake;
    }

    public int getRating() {
        return rating;
    }

    public String getSleep_time() {
        return sleep_time;
    }

    public String getWakeup_time() {
        return wakeup_time;
    }

    public void setCaffeine_intake(List<Coffee> caffeine_intake) {
        this.caffeine_intake = caffeine_intake;
    }

    public void addCoffee(String time, int numMg) {
        Coffee c = new Coffee(time, numMg);
        this.caffeine_intake.add(c);
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setSleep_time(String sleep_time) {
        this.sleep_time = sleep_time;
    }

    public void setWakeup_time(String wakeup_time) {
        this.wakeup_time = wakeup_time;
    }
}
