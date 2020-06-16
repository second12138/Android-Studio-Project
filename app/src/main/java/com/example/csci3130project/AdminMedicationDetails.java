package com.example.csci3130project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.csci3130project.Models.Medication;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminMedicationDetails extends AppCompatActivity {
    private FirebaseFirestore database;
    private Intent intent;
    private Medication medication;
    private Map map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_medication_details);

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

    public void updateButton_onClick(View view){
        TextView name = findViewById(R.id.nameEdit);
        TextView dosage = findViewById(R.id.dosageEdit);
        TextView sideEffects = findViewById(R.id.sideEffectsEdit);
        map = new HashMap();
        map.put("name", name.getText().toString());
        map.put("dosage", dosage.getText().toString());
        map.put("sideEffects", sideEffects.getText().toString());
        DocumentReference ref = database.collection("Medication").document(medication.id);
        ref.update(map);
        finish();
    }


    public void deleteButton_onClick(View view){
        TextView name = findViewById(R.id.nameEdit);
        TextView email = findViewById(R.id.dosageEdit);
        DocumentReference ref = database.collection("Medication").document(medication.id);


        ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Log.d(TAG, "DocumentSnapshot successfully deleted!");
            }
        }).addOnFailureListener(AdminMedicationDetails.this,new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.getMessage().toString();
                Toast.makeText(AdminMedicationDetails.this, message, Toast.LENGTH_SHORT).show();
                // Log.w(TAG, "Error deleting document", e);
            }
        });

        //TODO: add the logic for deleting an entry

        finish();
    }
}