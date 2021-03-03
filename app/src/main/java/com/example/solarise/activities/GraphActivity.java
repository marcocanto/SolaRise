package com.example.solarise.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

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

import java.util.List;

public class GraphActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        User u = new User("Rad", 21, true, 3);

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
        chart.animateX(1500);
//        chart.setBackgroundColor(Color.parseColor("#1f119c"));

        final String[] dates = u.getDates();

        ValueFormatter formatter = new ValueFormatter() {

            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return dates[(int) value];
            }
        };

        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMaximum(u.getDays().size());
        xAxis.setLabelCount(u.getDays().size(), true);
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextSize(13);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaximum(5);
        leftAxis.setAxisMinimum(0);
        leftAxis.setLabelCount(6, true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);


        List<Entry> entries = u.getUserDataForGraph();
        LineDataSet dataSet = new LineDataSet(entries, "Rating");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setValueTextSize(12);
        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);
        chart.invalidate();
    }
}