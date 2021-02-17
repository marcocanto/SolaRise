package com.example.solarise.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

import static java.time.LocalDateTime.ofInstant;

public class Daytime {

    String sunrise;
    String sunset;

    public Daytime(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Daytime(Long sunrise, Long sunset) {
        this.sunrise = getTime(sunrise).toString();
        this.sunset = getTime(sunset).toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Daytime fromJSON(JSONObject jsonObject) {
        Daytime d = new Daytime();
        try {
            JSONObject sys = jsonObject.getJSONObject("sys");
            d.sunrise = getTime(Long.parseLong(sys.getString("sunrise"))).toString();
            d.sunset = getTime(Long.parseLong(sys.getString("sunset"))).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return d;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalTime getTime(Long timeInMillis) {
        Instant instant = Instant.ofEpochMilli(timeInMillis* 1000);
        return ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

}
