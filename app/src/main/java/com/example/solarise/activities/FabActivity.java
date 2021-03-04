package com.example.solarise.activities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.solarise.R;
import com.example.solarise.fragments.CaffeineEntryDialogFragment;
import com.example.solarise.fragments.SleepEntryDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FabActivity extends AppCompatActivity {

    FloatingActionButton fab;
    FloatingActionButton fab_coffee;
    FloatingActionButton fab_sleep;
    Animator rotate_open;
    Animator rotate_close;
    boolean fab_clicked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);

        fab = findViewById(R.id.add_btn);
        fab_coffee = findViewById(R.id.coffee_btn);
        fab_sleep = findViewById(R.id.sleep_btn);
        fab_clicked = false;

        rotate_open = AnimatorInflater.loadAnimator(this, R.animator.rotate_open_anim);
        rotate_close = AnimatorInflater.loadAnimator(this, R.animator.rotate_close_anim);
        rotate_open.setTarget(fab);
        rotate_close.setTarget(fab);

        fab.setOnClickListener(v -> {
            onAddButtonClicked();
        });

        fab_sleep.setOnClickListener(v -> showSleepDialog());

        fab_coffee.setOnClickListener(v -> showCaffeineDialog());

    }
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
}
