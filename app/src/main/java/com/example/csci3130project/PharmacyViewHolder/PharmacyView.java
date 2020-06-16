package com.example.csci3130project.PharmacyViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.csci3130project.R;

/**
 * The view holder for the pharmacy list firebase adapter.
 */
public class PharmacyView extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView dosage;
    public Button detailsButton;

    public PharmacyView(View view)
    {
        super(view);
        name = view.findViewById(R.id.medicationName);
        dosage = view.findViewById(R.id.medicationDosage);
        detailsButton = view.findViewById(R.id.goDetails);

    }
}