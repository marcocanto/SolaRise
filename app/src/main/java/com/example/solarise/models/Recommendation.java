package com.example.solarise.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<ArrayList<String>> getSleepRecommendations() {

        ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
        String pattern = "hh:mm a";

        for(int i = 0; i <= 30; i += 15) {

            ArrayList<String> baseline1 = new ArrayList<String>();
            LocalTime e1 = earliest_sleep.minusMinutes(i);
            baseline1.add(e1.format(DateTimeFormatter.ofPattern(pattern)));
            baseline1.add(e1.plusMinutes(sleep_length).format(DateTimeFormatter.ofPattern(pattern)));
            output.add(baseline1);

            ArrayList<String> baseline2 = new ArrayList<String>();
            LocalTime l1 = latest_sleep.plusMinutes(i);
            baseline2.add(l1.format(DateTimeFormatter.ofPattern(pattern)));
            baseline2.add(l1.plusMinutes(sleep_length).format(DateTimeFormatter.ofPattern(pattern)));
            output.add(baseline2);

        }


        return output;

    }

    public ArrayList<Integer> getCaffeineRecommendation() {

        ArrayList<Integer> output = new ArrayList<Integer>();

        if(this.caffeine <= 1) {
            for(int i = 0; i <= 2; ++i) {
                output.add(i);
            }
        }

        else {

            for(int i = this.caffeine - 1; i <= this.caffeine + 1; ++i) {
                output.add(i);
            }

        }

        return output;

    }

}

