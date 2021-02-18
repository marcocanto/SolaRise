package com.example.solarise.network;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

public class OpenWeatherClient extends  AsyncHttpClient {
    public static final String API_KEY = "202aea999dde10c1802c3e6c4590ab10";
    public static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    private static final String TAG = "OpenWeatherClient";
    private final AsyncHttpClient client;

    public OpenWeatherClient() {
        this.client = new AsyncHttpClient();
    }

    public void getCurrentWeather(Double latitude, Double longitude, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("appid", API_KEY);
        params.put("lat", Double.toString(latitude));
        params.put("lon", Double.toString(longitude));
        Log.i(TAG, "Sending current weather get request");
        client.get(API_URL, params, handler);
    }
}
