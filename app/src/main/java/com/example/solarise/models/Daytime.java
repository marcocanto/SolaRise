package com.example.solarise.models;

import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.solarise.network.OpenWeatherClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

import okhttp3.Headers;

import static java.time.LocalDateTime.ofInstant;

public class Daytime {

    String sunrise;
    String sunset;

    private DaytimeListener listener;

    public interface DaytimeListener {
        public void onDataLoaded(JSONObject json);
    }

    public Daytime(){
        this.listener = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Daytime(Long sunrise, Long sunset) {
        this.sunrise = getTime(sunrise).toString();
        this.sunset = getTime(sunset).toString();
    }

    public void setListener(DaytimeListener listener) {
         this.listener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDaytimeFromJSON(JSONObject jsonObject) {
        try { JSONObject sys = jsonObject.getJSONObject("sys");
            this.sunrise = getTime(Long.parseLong(sys.getString("sunrise"))).toString();
            this.sunset = getTime(Long.parseLong(sys.getString("sunset"))).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Daytime fromJSON(JSONObject jsonObject) {
        Daytime d = new Daytime();
        try { JSONObject sys = jsonObject.getJSONObject("sys");
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

    public void loadDaytimeAsync(OpenWeatherClient client, Location location) {
        client.getCurrentWeather(location.getLatitude(), location.getLongitude(), new JsonHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                if (json != null) {
                    try {
                        Log.i("OpenWeatherClient", "retrieved weather json: " + json.jsonObject.getJSONObject("sys").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listener.onDataLoaded(json.jsonObject);
                } else {
                    Log.i("OpenWeatherClient", "received empty weather json");
                }
            }
            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e("OpenWeatherClient", "error getting weather data", throwable);
            }
        });
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

}
