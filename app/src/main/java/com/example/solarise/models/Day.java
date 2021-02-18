package com.example.solarise.models;

public class Day {

    String sleep_time;
    String wakeup_time;
    int rating;
    int caffeine_intake;

    public Day() {}

    public Day(String sleep_time, String wakeup_time, int rating, int caffeine_intake) {
        this.sleep_time = sleep_time;
        this.wakeup_time = wakeup_time;
        this.rating = rating;
        this.caffeine_intake = caffeine_intake;
    }

    public int getCaffeine_intake() {
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

    public void setCaffeine_intake(int caffeine_intake) {
        this.caffeine_intake = caffeine_intake;
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
