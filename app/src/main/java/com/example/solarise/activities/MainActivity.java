package com.example.solarise.activities;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.solarise.R;
import com.example.solarise.fragments.CaffeineEntryDialogFragment;
import com.example.solarise.fragments.SleepEntryDialogFragment;
import com.example.solarise.models.CoffeeReceiver;
import com.example.solarise.models.Day;
import com.example.solarise.models.Daytime;
import com.example.solarise.models.NotificationReceiver;
import com.example.solarise.models.Recommendation;
import com.example.solarise.models.Recommender;
import com.example.solarise.models.User;
import com.example.solarise.network.OpenWeatherClient;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FusedLocationProviderClient fusedLocationClient;
    private OpenWeatherClient client;
    private static Daytime currentDaytime;
    private static Location lastLocation;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private MaterialToolbar appToolbar;

    private FloatingActionButton fab;
    private FloatingActionButton fab_coffee;
    private FloatingActionButton fab_sleep;
    private Animator rotate_open;
    private Animator rotate_close;
    private boolean fab_clicked;

    private MaterialTextView rec1, rec2, rec3, rec4, rec5, rec6;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);

        fab = findViewById(R.id.add_btn);
        fab_coffee = findViewById(R.id.coffee_btn);
        fab_sleep = findViewById(R.id.sleep_btn);
        fab_clicked = false;

        appToolbar = findViewById(R.id.topAppBar);

        rotate_open = AnimatorInflater.loadAnimator(this, R.animator.rotate_open_anim);
        rotate_close = AnimatorInflater.loadAnimator(this, R.animator.rotate_close_anim);
        rotate_open.setTarget(fab);
        rotate_close.setTarget(fab);

        TextView tvSunriseTime = findViewById(R.id.tvSunriseTime);
        TextView tvSunsetTime = findViewById(R.id.tvSunsetTime);

        appToolbar.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(getApplicationContext(), EditProfile.class);
            startActivity(intent);
            return true;
        });

        fab.setOnClickListener(v -> onAddButtonClicked());
        fab_sleep.setOnClickListener(v -> showSleepDialog());
        fab_coffee.setOnClickListener(v -> showCaffeineDialog());

        // set up the network client to send API requests
        client = new OpenWeatherClient();
        // initialize the location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

//        requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20 * 1000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.i(TAG, "on location result");
                if (locationResult == null) {
                    return;
                }
                for (Location location: locationResult.getLocations()) {
                    if (location != null) {
                        getCurrentLocation(fusedLocationClient);
                        fusedLocationClient.removeLocationUpdates(locationCallback);
                    }
                }
            }
        };

        currentDaytime = new Daytime();
        currentDaytime.setListener(json -> {
            currentDaytime.setDaytimeFromJSON(json);
            tvSunriseTime.setText(currentDaytime.getSunrise());
            tvSunsetTime.setText(currentDaytime.getSunset());
            sunlightAlarm(tvSunsetTime.getText().toString());
            coffeeAlarm(tvSunsetTime.getText().toString());
        });

        getCurrentLocation(fusedLocationClient);
        tvSunriseTime.setText(currentDaytime.getSunrise());
        tvSunsetTime.setText(currentDaytime.getSunset());



        User u = new User("Rad", 21, true, 3, "test_user");

        Day d5 = new Day("2021-02-25T13:14:15", "2021-02-21T20:14:15", 5);
        Day d4 = new Day("2021-02-25T14:14:15", "2021-02-22T21:30:15", 4.5);
        Day d3 = new Day("2021-02-25T15:14:15", "2021-02-23T05:14:15", 4);
        Day d2 = new Day("2021-02-25T13:14:15.038415", "2021-02-24T13:14:15.038415", 3);
        Day d1 = new Day("2021-02-25T13:14:15.038415", "2021-02-25T13:14:15.038415", 3.5);

        u.addDay(d5);
        u.addDay(d4);
        u.addDay(d3);
        u.addDay(d2);
        u.addDay(d1);

        LineChart chart = (LineChart) findViewById(R.id.chart);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.getDescription().setEnabled(false);

//        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
//        chart.getAxisRight().setDrawGridLines(false);

        chart.animateX(1500);
//        chart.setBackgroundColor(Color.parseColor("#1f119c"));

        final String[] dates = getDates(u);

        ValueFormatter formatter = new ValueFormatter() {

            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return dates[(int) value];
            }
        };

        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMaximum(u.getDays().size());
        xAxis.setLabelCount(u.getDays().size() + 1, true);
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextSize(13);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaximum(6);
        leftAxis.setAxisMinimum(0);
        leftAxis.setLabelCount(7, true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);


        List<Entry> entries = getUserDataForGraph(u);
        LineDataSet dataSet = new LineDataSet(entries, "Rating");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setValueTextSize(12);
        dataSet.setDrawFilled(true);
//        dataSet.setFillDrawable(gradientDrawable);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
        dataSet.setFillDrawable(drawable);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();


        rec1 = (MaterialTextView) findViewById(R.id.Recommendation_1);
        rec2 = (MaterialTextView) findViewById(R.id.Recommendation_2);
        rec3 = (MaterialTextView) findViewById(R.id.Recommendation_3);
        rec4 = (MaterialTextView) findViewById(R.id.Recommendation_4);
        rec5 = (MaterialTextView) findViewById(R.id.Recommendation_5);
        rec6 = (MaterialTextView) findViewById(R.id.Recommendation_6);

        Recommender r = new Recommender();
        Recommendation recommendation = r.giveRecommendation(u, 3);
        ArrayList<String> sleep_recs = recommendation.getSleepRecommendations();

        rec1.setText(sleep_recs.get(0));
        rec2.setText(sleep_recs.get(1));
        rec3.setText(sleep_recs.get(2));
        rec4.setText(sleep_recs.get(3));
        rec5.setText(sleep_recs.get(4));
        rec6.setText(sleep_recs.get(5));

    }

    // returns pair of lat,lon
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getCurrentLocation(FusedLocationProviderClient fusedLocationClient) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
        } else {
            Log.i(TAG, "getting last location");
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        // check if location was received
                        if (location != null) {
                            Log.i(TAG, "received location data: " + location.toString());
                            setLocation(location);
                            setWeather(client, lastLocation);
                        } else {
                            Log.i(TAG, "error receiving location");
                            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                        }
                    });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setWeather(OpenWeatherClient client, Location location) {
        currentDaytime.loadDaytimeAsync(client, location);
    }

    public void setLocation(Location location) {
        lastLocation = location;
    }

    public List<Entry> getUserDataForGraph(User u) {

        List<Entry> entries = new ArrayList<>();
//        entries.add(new Entry(0,0));

        for(int i = 0; i < u.getDays().size(); ++i) {

            entries.add(new Entry(i + 1, (float) u.getDays().get(i).getRating()));

        }

        return entries;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String[] getDates(User u) {

        String[] dates = new String[u.getDays().size() + 1];
        dates[0] = "";
        int ct = 1;

        for(Day d: u.getDays()) {

            LocalDateTime l1 = LocalDateTime.parse(d.getWakeup_time());
            LocalDate date = l1.toLocalDate();
            String formattedDate = date.format(DateTimeFormatter.ofPattern("MM/dd"));
//            String formattedDate = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
            dates[ct++] = formattedDate;
        }

        return dates;

    }

    private final ActivityResultLauncher<String> requestPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            Log.i(TAG, "permission granted");
            getCurrentLocation(fusedLocationClient);
        }
        else {
            Log.i(TAG, "permission denied");
        }
    });


    private void onAddButtonClicked() {
        setVisibility(fab_clicked);
        setAnimation(fab_clicked);
        fab_clicked = !fab_clicked;
    }
    private void setVisibility(boolean clicked) {
        if (!clicked) {
            fab_sleep.show();
            fab_coffee.show();
        } else {
            fab_coffee.hide();
            fab_sleep.hide();
        }
    }

    private void setAnimation(boolean clicked) {
        if (!clicked) {
            rotate_open.start();
        } else {
            rotate_close.start();
        }
    }
    private void showSleepDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SleepEntryDialogFragment sleepEntryDialogFragment = SleepEntryDialogFragment.newInstance("Some Title");
        sleepEntryDialogFragment.show(fm, "fragment_edit_name");
    }

    private void showCaffeineDialog() {
        FragmentManager fm = getSupportFragmentManager();
        CaffeineEntryDialogFragment caffeineEntryDialogFragment = CaffeineEntryDialogFragment.newInstance("Some title");
        caffeineEntryDialogFragment.show(fm, "fragment_alert");
    }

    public void sunlightAlarm(String sunset) {

        LocalTime lt = LocalTime.parse(sunset, DateTimeFormatter.ofPattern("hh:mm a"));


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, lt.getHour() - 1);
        calendar.set(Calendar.MINUTE, lt.getMinute());
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTime().compareTo(new Date()) < 0)
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }

    }

    public void coffeeAlarm(String sunset) {

        LocalTime lt = LocalTime.parse(sunset, DateTimeFormatter.ofPattern("hh:mm a"));


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, lt.getHour());
        calendar.set(Calendar.MINUTE, lt.getMinute());
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTime().compareTo(new Date()) < 0)
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        Intent intent = new Intent(getApplicationContext(), CoffeeReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }

    }
}
