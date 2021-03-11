package com.example.solarise.activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.solarise.R;
import com.example.solarise.fragments.TimePickerFragment;
import com.example.solarise.models.SleepCalc;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;

public class CalculatorActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private boolean am_pm, sleeping_now;
    private MaterialButton btnSleep, btnWake, btnCalc;
    private EditText etSleepTime, etAge;
    private ArrayList<String> sleep_cycles;
    private TextView display_cycles;
    private int hour, min;
    private boolean am;
    MaterialButtonToggleGroup toggleDay;
    private MaterialToolbar appToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        MaterialButtonToggleGroup toggleDay = findViewById(R.id.toggleType);
        btnSleep = findViewById(R.id.sleeping);
        btnWake = findViewById(R.id.waking);
        btnCalc = findViewById(R.id.calculate_times);
        appToolbar = findViewById(R.id.topAppBar);
        display_cycles = (TextView)findViewById(R.id.displayCycles);
        Button clock = (Button)findViewById(R.id.clock);

        appToolbar.setOnMenuItemClickListener(item -> {
            finish();
            return true;
        });

        clock.setOnClickListener(v -> {
            DialogFragment time = new TimePickerFragment();
            time.show(getSupportFragmentManager(), "time");
        });

        btnCalc.setOnClickListener(v -> {
            String str_hour = String.valueOf(hour);
            String str_min = String.valueOf(min);
            sleeping_now = toggleDay.getCheckedButtonId() == R.id.sleeping;
            Toast.makeText(this, Boolean.toString(sleeping_now), Toast.LENGTH_SHORT).show();
            SleepCalc calc = new SleepCalc();
            sleep_cycles = calc.calc_times(str_hour + ":" + str_min, sleeping_now, 18);
            if (sleeping_now){
                display_cycles.setText("Recommended Wake Up Times in Order: " + "\n" + sleep_cycles.get(0) + " (Best) " + "\n" + sleep_cycles.get(1)  + "\n"
                        + sleep_cycles.get(2)  + "\n" + sleep_cycles.get(3));
            }
            else{
                display_cycles.setText("Recommended Sleep Times in Order: " + "\n" + sleep_cycles.get(0) + " (Best) " + "\n" + sleep_cycles.get(1)  + "\n"
                        + sleep_cycles.get(2) + "\n" + sleep_cycles.get(3));
            }

        });
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (hourOfDay > 12 ){
            hour = hourOfDay - 12;
           // hour = hourOfDay;
            am = false;
        }
        else{
            hour = hourOfDay;
            am = true;

        }
        min = minute;
    }
}