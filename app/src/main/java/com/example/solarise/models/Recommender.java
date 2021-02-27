package com.example.solarise.models;



import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.*;
import static java.lang.Math.max;
import java.time.LocalDateTime;
import java.time.Duration;

public class Recommender {

    public Recommender() {}

    public List<Day> getBestDays(User user, int n) {

        List<Day> user_days = user.getDays();
        Collections.sort(user_days, new SortByRating());

        int sz = max(user_days.size(), n);

        return user_days.subList(sz - n, sz);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime convertStringToTime(String t) {

        return LocalDateTime.parse(t);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getMinutesSlept(Day d) {

        LocalDateTime start = convertStringToTime(d.getSleep_time());
        LocalDateTime end = convertStringToTime(d.getWakeup_time());

        long minutes = Duration.between(start, end).toMillis() / 60000;

        return (int)minutes;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int sleepRecommendation(List<Day> days) {

        int sz = days.size();
        int ct = 0;

        for(Day d : days) {
            ct += getMinutesSlept(d);
        }

        return ct / sz;

    }

    public int caffeineRecommendation(List<Day> d) {

        int sz = d.size();
        int ct = 0;

        for(int i = 0; i < sz; ++i) {
            ct += d.get(i).getCaffeine_intake().size();
        }

        return ct / sz;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalTime earliestSleepRecommendation(List<Day> days) {


        LocalTime earliestTime = OffsetDateTime.parse(days.get(0).getSleep_time()).toLocalTime();

        for(Day d: days) {

            LocalTime currentTime = OffsetDateTime.parse(d.getSleep_time()).toLocalTime();
            if(currentTime.isBefore(earliestTime)) earliestTime = currentTime;

        }

        return earliestTime;


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalTime latestSleepRecommendation(List<Day> days) {

        LocalTime latestTime = OffsetDateTime.parse(days.get(0).getSleep_time()).toLocalTime();

        for(Day d: days) {

            LocalTime currentTime = OffsetDateTime.parse(d.getSleep_time()).toLocalTime();
            if(currentTime.isAfter(latestTime)) latestTime = currentTime;

        }

        return latestTime;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Recommendation giveRecommendation(User user, int n) {

        List<Day> d = getBestDays(user, n);
        int sleepRec = sleepRecommendation(d);
        int caffeineRec = caffeineRecommendation(d);
        LocalTime earlySleepRec = earliestSleepRecommendation(d);
        LocalTime latestSleepRec = latestSleepRecommendation(d);

        Recommendation r = new Recommendation(sleepRec, caffeineRec, earlySleepRec, latestSleepRec);
        return r;


    }

    // 2021-02-25T13:14:15
    //TODO: how can we use the above function to determine recommendations?

}

class SortByRating implements Comparator<Day> {

    public int compare(Day a, Day b) {

        return a.rating - b.rating;

    }

}
