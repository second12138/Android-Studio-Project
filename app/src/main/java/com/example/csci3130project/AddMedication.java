package com.example.csci3130project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.example.csci3130project.Models.Medication;
import org.w3c.dom.Text;


public class AddMedication extends AppCompatActivity {

    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medication);
        database = FirebaseFirestore.getInstance();
    }

    /**
     * When this button is clicked we add a new Medication object to the pharmacy list in firebase
     *
     */
    public void addNewButton_onClick(View view) {
        TextView name = findViewById(R.id.nameEdit);
        TextView dosage = findViewById(R.id.dosageEdit);
        TextView sideEffects = findViewById(R.id.sideEffectsEdit);

        String nameString = name.getText().toString();
        String dosageString = dosage.getText().toString();
        String sideEffectsString = sideEffects.getText().toString();
        addToMedicationList(nameString,dosageString,sideEffectsString);


        //Finishes the activity and return to the parent one.
        finish();
    }
    public int addToMedicationList(String nameString,String dosageString,String sideEffectsString) {

        int flag = 0;
        //Medication c = new Medication(name.getText().toString(),dosage.getText().toString(), sideEffects.getText().toString());

        if (nameString.isEmpty()) {
            flag += 1;
        }
        if (dosageString.isEmpty()) {
            flag += 10;
        }
        if (sideEffectsString.isEmpty()) {
            flag += 100;
        }

        if (flag == 0) {
            Medication c = new Medication(nameString, dosageString, sideEffectsString);
            //Here instead of adding directly we are first getting a reference so we save the ID;
            // this is not necessary but it will make life easier later when editing/deleting.
            DocumentReference ref = database.collection("Medication").document();
            c.id = ref.getId();
            ref.set(c);
        }
        return flag;
    }
}

