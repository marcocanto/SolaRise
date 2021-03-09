package com.example.solarise.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.solarise.R;
import com.example.solarise.fragments.SleepEntryDialogFragment;
import com.example.solarise.fragments.TimePickerFragment;
import com.example.solarise.models.SleepCalc;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.timepicker.MaterialTimePicker;

import java.util.ArrayList;




public class CalculatorActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private boolean am_pm, sleeping_now;
    private Button sleep, wake, calculate_times;
    private EditText sleep_time, age;
    private ArrayList<String> sleep_cycles;
    private TextView display_cycles;
    private int hour, min;
    private boolean am;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        MaterialButtonToggleGroup toggleDay = findViewById(R.id.toggleType);
        sleep = (Button)findViewById(R.id.sleeping);
        wake = (Button)findViewById(R.id.waking);
        calculate_times = (Button)findViewById(R.id.calculate_times);
        age = (EditText)findViewById(R.id.enter_age);
        display_cycles = (TextView)findViewById(R.id.displayCycles);
        Button clock = (Button)findViewById(R.id.clock);



        clock.setOnClickListener(v -> {
            DialogFragment time = new TimePickerFragment();
            time.show(getSupportFragmentManager(), "time");

        });


        calculate_times.setOnClickListener(v -> {
            String str_hour = String.valueOf(hour);
            String str_min = String.valueOf(min);
            sleeping_now = toggleDay.getCheckedButtonId() == R.id.sleeping;
            SleepCalc calc = new SleepCalc();
            sleep_cycles = calc.calc_times(str_hour + ":" + str_min, sleeping_now, Integer.parseInt(age.getText().toString()));
            display_cycles.setText("Recommended Sleep Times in Preferred Order: " + sleep_cycles.get(0) + " (Best), " + sleep_cycles.get(1) + ", "
            + sleep_cycles.get(2) + ", " + sleep_cycles.get(3));





        });


    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (hourOfDay > 12 ){
            hour = hourOfDay - 12;
            am = false;
        }
        else{
            hour = hourOfDay;
            am = true;

        }
        min = minute;
    }
}