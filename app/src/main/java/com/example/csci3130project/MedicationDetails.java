package com.example.csci3130project;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;


import com.google.firebase.firestore.FirebaseFirestore;
import com.example.csci3130project.Models.Medication;

/**
 * This class contains the medication details for the pharmacy list.
 * We will later add functionality for the pharmacist account to modify or delete items.
 */
public class MedicationDetails extends AppCompatActivity {

    private FirebaseFirestore database;
    private Intent intent;
    private Medication medication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medication_details);

        database = FirebaseFirestore.getInstance();
        intent = getIntent();
        medication = (Medication) intent.getSerializableExtra("Medication");

        TextView name = findViewById(R.id.nameEdit);
        name.setText(medication.name);

        TextView dosage = findViewById(R.id.dosageEdit);
        dosage.setText(medication.dosage);

        TextView sideEffects = findViewById(R.id.sideEffectsEdit);
        sideEffects.setText(medication.sideEffects);
    }

    /**
     *
     * This method allows us to add an entry in the pharmacy list to the user's personal
     * medication list
     */
    public void addToListButton_onClick(View view){
        TextView name = findViewById(R.id.nameEdit);
        TextView dosage = findViewById(R.id.dosageEdit);
        TextView sideEffects = findViewById(R.id.sideEffectsEdit);

        //TODO: Change this to UserMedication since right now we are just adding plain Medication objects to userlist
        Medication c = new Medication(name.getText().toString(), dosage.getText().toString(), sideEffects.getText().toString());
        DocumentReference ref = database.collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).document();
        c.id = ref.getId();
        ref.set(c);
        finish();
    }


}
