package com.example.csci3130project.PharmacyViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.csci3130project.R;

/**
 * The viewholder for the user list firebase adapter. We would like to possibly add more
 * to this later. This displays on a medication card view.
 */
public class UserListView extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView dosage;
    public Button detailsButton;

    public UserListView(View view)
    {
        super(view);
        name = view.findViewById(R.id.medicationName);
        dosage = view.findViewById(R.id.medicationDosage);
        detailsButton = view.findViewById(R.id.goDetails);

    }
}