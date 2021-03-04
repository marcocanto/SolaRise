package com.example.solarise.activities;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.solarise.R;
import com.example.solarise.models.Day;
import com.example.solarise.models.User;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

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

}