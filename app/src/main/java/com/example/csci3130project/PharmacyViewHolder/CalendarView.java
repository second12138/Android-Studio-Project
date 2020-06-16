package com.example.csci3130project.PharmacyViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.csci3130project.Models.UserMedication;
import com.example.csci3130project.R;

public class CalendarView extends RecyclerView.ViewHolder
{
    private UserMedication prescription;

    public TextView textViewMedicationName;
    public TextView textViewDosageInfo;
    public TextView textViewUserInfo;
    public TextView textViewTOD;


    public CalendarView(View view)
    {
        super(view);

        textViewMedicationName = itemView.findViewById(R.id.MedicationView);
        textViewDosageInfo = itemView.findViewById(R.id.DosageView);
        textViewUserInfo = itemView.findViewById(R.id.UserInfoView);
        textViewTOD = itemView.findViewById(R.id.TODInfo);
    }


    public void setPrescription(UserMedication prescription)
    {
        this.prescription = prescription;
    }
}
