package com.example.solarise.models;

import java.time.LocalTime;

public class Recommendation {

    LocalTime earliest_sleep;
    LocalTime latest_sleep;
    int sleep_length;
    int caffeine;

    public Recommendation(int sleep_length, int caffeine, LocalTime earliest_sleep, LocalTime latest_sleep) {
        this.sleep_length = sleep_length;
        this.caffeine = caffeine;
        this.earliest_sleep = earliest_sleep;
        this.latest_sleep = latest_sleep;
    }

}
