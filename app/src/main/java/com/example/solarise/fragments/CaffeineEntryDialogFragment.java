package com.example.solarise.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.solarise.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CaffeineEntryDialogFragment extends DialogFragment {

    public static final String TAG = "SleepEntryDialogFragment";
    private MaterialButton btnClose;
    private MaterialButton btnSubmit;
    private TextInputEditText etCaffeine;

    public CaffeineEntryDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static CaffeineEntryDialogFragment newInstance(String title) {
        CaffeineEntryDialogFragment frag = new CaffeineEntryDialogFragment();
        Bundle args = new Bundle();
        args.putString("Caffeine Entry", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_caffeine, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        btnClose = view.findViewById(R.id.btnClose);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        etCaffeine = view.findViewById(R.id.etCaffeine);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);


        btnClose.setOnClickListener(v -> {
            dismiss();
        });


        btnSubmit.setOnClickListener(v -> {
            Toast.makeText(this.getContext(), "Coffee Intake Recorded", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        etCaffeine.requestFocus();
        if(etCaffeine.requestFocus()) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

    }
}

