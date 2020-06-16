package com.example.csci3130project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.csci3130project.Models.UserMedication;
import com.example.csci3130project.PharmacyViewHolder.CalendarView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CalendarAdapter extends FirestoreRecyclerAdapter<UserMedication, CalendarView>
{


    public CalendarAdapter(@NonNull
                                   FirestoreRecyclerOptions<UserMedication> options)
    {
        super(options);
    }


    @Override
    protected void onBindViewHolder(
            @NonNull CalendarView CalendarHolder, int i,
            @NonNull UserMedication prescription)
    {
        CalendarHolder.setPrescription(prescription);

        CalendarHolder.textViewMedicationName.setText(prescription.name);
        //CalendarHolder.textViewUserInfo.setText(prescription.getNotes());

        /*if ( prescription.getTimeOfDay() != 0 )
        {
            CalendarHolder.textViewTOD.setText(TimeHelper.getTimeString(prescription));
        }*/
        CalendarHolder.textViewTOD.setText(String.valueOf(prescription.time));
        CalendarHolder.textViewDosageInfo.setText(String.valueOf(prescription.dosage));
    }


    @NonNull
    @Override
    public CalendarView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prescription_item, parent, false);
        return new CalendarView(v);
    }


}
