package com.example.solarise.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.solarise.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.timepicker.MaterialTimePicker;

public class SleepEntryDialogFragment extends DialogFragment {
    public static final String TAG = "SleepEntryDialogFragment";
    private Button btnSleepStart;
    private Button btnWakeUp;
    private TextView tvSleepStart;
    private TextView tvWakeUp;
    private MaterialButton btnClose, btnSubmit;

    private MaterialTimePicker picker1;
    private MaterialTimePicker picker2;

    public SleepEntryDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static SleepEntryDialogFragment newInstance(String title) {
        SleepEntryDialogFragment frag = new SleepEntryDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_sleep, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        btnSleepStart = view.findViewById(R.id.btnSleepStart);
        btnWakeUp = view.findViewById(R.id.btnWakeUp);
        tvSleepStart = view.findViewById(R.id.tvSleepStart);
        tvWakeUp = view.findViewById(R.id.tvWakeUp);
        btnClose = view.findViewById(R.id.btnClose);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);

        btnSleepStart.setOnClickListener(v -> {
            picker1 = createSleepPicker("Enter Fall Asleep Time");
            picker1.addOnPositiveButtonClickListener(x -> {
                tvSleepStart.setText(getContext().getResources().getString(R.string.sleep_start_time, picker1.getHour(), picker1.getMinute()));
            });
            picker1.show(requireFragmentManager(), "SleepEntryDialogFragment");
        });

        btnWakeUp.setOnClickListener(v -> {
            picker2 = createSleepPicker("Enter Wake Up Time");

            picker2.addOnPositiveButtonClickListener(x -> {
                tvWakeUp.setText(getContext().getResources().getString(R.string.sleep_start_time, picker2.getHour(), picker2.getMinute()));
            });
            picker2.show(requireFragmentManager(), "SleepEntryDialogFragment");
        });

        btnClose.setOnClickListener(v -> {
            dismiss();
        });

        btnSubmit.setOnClickListener(v -> {
            Toast.makeText(this.getContext(), "Sleep Session Recorded", Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }

    MaterialTimePicker createSleepPicker(String titleText) {
        return new MaterialTimePicker.Builder()
                .setHour(12)
                .setMinute(10)
                .setTitleText(titleText)
                .build();
    }
}
