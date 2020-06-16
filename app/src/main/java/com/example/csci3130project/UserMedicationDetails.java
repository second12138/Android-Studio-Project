package com.example.csci3130project;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csci3130project.Models.UserMedication;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

public class UserMedicationDetails extends AppCompatActivity {

    private FirebaseFirestore database;
    private Intent intent;
    private UserMedication userMedication;
    private String pattern = "HH:mm";
    private Button update;
    private Map map, weekdayMap;

    private Map mapTakeMedication;
    private DateFormat df = new SimpleDateFormat(pattern);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_medication_details);

        database = FirebaseFirestore.getInstance();
        intent = getIntent();
        userMedication = (UserMedication) intent.getSerializableExtra("UserMedication");


        TextView name = findViewById(R.id.nameEdit);
        name.setText(userMedication.name);

        TextView dosage = findViewById(R.id.dosageEdit);
        dosage.setText(userMedication.dosage);

        TextView totalDosage = findViewById(R.id.totDosageEdit);
        totalDosage.setText(userMedication.totalDosage);

        TextView sideEffect = findViewById(R.id.SideEffectEdit2);
        sideEffect.setText(userMedication.sideEffects);

        TextView time = findViewById(R.id.timeEdit);
        time.setText(userMedication.time);


        CheckBox mon = findViewById(R.id.chkMonday);
        CheckBox tues = findViewById(R.id.chkTuesday);
        CheckBox wed = findViewById(R.id.chkWednesday);
        CheckBox thur = findViewById(R.id.chkThursday);
        CheckBox fri  = findViewById(R.id.chkFriday);
        CheckBox sat = findViewById(R.id.chkSaturday);
        CheckBox sun = findViewById(R.id.chkSunday);
        mon.setChecked(userMedication.weekdays.get("Monday"));
        tues.setChecked(userMedication.weekdays.get("Tuesday"));
        wed.setChecked(userMedication.weekdays.get("Wednesday"));
        thur.setChecked(userMedication.weekdays.get("Thursday"));
        fri.setChecked(userMedication.weekdays.get("Friday"));
        sat.setChecked(userMedication.weekdays.get("Saturday"));
        sun.setChecked(userMedication.weekdays.get("Sunday"));

    }

    /**
     *
     * This function allows us to update a medication in the user's list.
     * All fields are updatable
     *
     */
    public void takeButton_onClick(View view){
        TextView dosage = findViewById(R.id.dosageEdit);
        TextView totDosage = findViewById(R.id.totDosageEdit);
        int remainingDosage = Integer.parseInt(totDosage.getText().toString()) - Integer.parseInt(dosage.getText().toString());
        if (remainingDosage>=0){
            mapTakeMedication = new HashMap();
            mapTakeMedication.put("totalDosage", Integer.toString(remainingDosage));
            DocumentReference ref = database.collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).document(userMedication.id);
            ref.update(mapTakeMedication);



        }
        finish();

        if (remainingDosage>=50) {
            Toast.makeText(UserMedicationDetails.this, "Remaining Dosage: " + Integer.toString(remainingDosage), Toast.LENGTH_SHORT).show();

        }
        else if (remainingDosage<50 && remainingDosage>=0){
            Toast.makeText(UserMedicationDetails.this, "You only have "+Integer.toString(remainingDosage)+" amount of doses left, please refill your medication", Toast.LENGTH_SHORT).show();
        }
        else if (remainingDosage<0){
            Toast.makeText(UserMedicationDetails.this, "Remaining Dosage is not enough, please refill your medication", Toast.LENGTH_SHORT).show();
        }
    }
    public void updateButton_onClick(View view){

        TextView name = findViewById(R.id.nameEdit);
        TextView dosage = findViewById(R.id.dosageEdit);
        TextView totDosage = findViewById(R.id.totDosageEdit);
        TextView sideEffects = findViewById(R.id.SideEffectEdit2);
        TextView time = findViewById(R.id.timeEdit);


        String nameStr = name.getText().toString();
        String dosageStr = dosage.getText().toString();
        String totDosageStr = totDosage.getText().toString();
        String sideEffectsStr = sideEffects.getText().toString();
        String timeStr = time.getText().toString();


        int validation = checkFields(nameStr, dosageStr, totDosageStr, sideEffectsStr, timeStr);
        int valCheckbox = checkCheckboxes();

        if(validation == 1){
            Toast.makeText(UserMedicationDetails.this, "Please enter a name", Toast.LENGTH_SHORT).show();
            name.requestFocus();
        }
        else if (validation == 2){
            Toast.makeText(UserMedicationDetails.this, "Dosage is empty, or not an integer", Toast.LENGTH_SHORT).show();
            dosage.requestFocus();
        }
        else if (validation == 3){
            Toast.makeText(UserMedicationDetails.this, "Total Dosage is empty, not a number, or negative", Toast.LENGTH_SHORT).show();
            totDosage.requestFocus();
        }
        else if (validation == 4){
            Toast.makeText(UserMedicationDetails.this, "Side effects is empty", Toast.LENGTH_SHORT).show();
            sideEffects.requestFocus();
        }
        else if (validation == 5){
            Toast.makeText(UserMedicationDetails.this, "Time is empty", Toast.LENGTH_SHORT).show();
            time.requestFocus();
        }
        else if (validation == 6){
            Toast.makeText(UserMedicationDetails.this, "Time is not a valid format. Use 24 hour time", Toast.LENGTH_SHORT).show();
            time.requestFocus();
        }
        else if (validation == 7){
            Toast.makeText(UserMedicationDetails.this, "Dosage is negative. Please enter a positive integer", Toast.LENGTH_SHORT).show();
            dosage.requestFocus();
        }
        else if (validation == 8){
            Toast.makeText(UserMedicationDetails.this, "Total Dosage is negative. Please enter a positive integer", Toast.LENGTH_SHORT).show();
            totDosage.requestFocus();
        }
        else if (valCheckbox == 1){
            Toast.makeText(UserMedicationDetails.this, "No day has been selected", Toast.LENGTH_SHORT).show();
        }


        //otherwise no issue, perform the update
        else if (validation == 0 && valCheckbox == 0){
            updateEntry();
        }

    }

    public void deleteButton_onClick(View view){

        DocumentReference ref = database.collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).document(userMedication.id);

        ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Log.d(TAG, "DocumentSnapshot successfully deleted!");
            }
        }).addOnFailureListener(UserMedicationDetails.this,new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.getMessage().toString();
                Toast.makeText(UserMedicationDetails.this, message, Toast.LENGTH_SHORT).show();
                // Log.w(TAG, "Error deleting document", e);
            }
        });

        finish();
    }

    /**
     * This function is used to validate the fields
     * @return This value tells the handler which error has occurred based on an integer
     * If there is no error, this value will be 0
     */

    public int checkFields(String nameStr, String dosageStr, String totDosageStr, String sideEffectsStr, String timeStr){

        int result = 0;


        //regex for time patterns
        Pattern timePat1 = Pattern.compile("[0-1][0-9]:[0-5][0-9]");
        Pattern timePat2 = Pattern.compile("[2][0-3]:[0-5][0-9]");


        Matcher timeMatch1 = timePat1.matcher(timeStr);
        Matcher timeMatch2 = timePat2.matcher(timeStr);

        boolean timeMatches1 = timeMatch1.matches();
        boolean timeMatches2 = timeMatch2.matches();


        //check if name is empty
        if(nameStr.isEmpty()){
            result = 1;
            return result;
        }

        //check if dosage empty or non-numeric/negative
        else if(dosageStr.isEmpty() || !isNumber(dosageStr)){
            result = 2;
            return result;
        }

        //check if totDosage is empty or non-numeric/negative
        else if(totDosageStr.isEmpty() || !isNumber(totDosageStr)){
            result = 3;
            return result;
        }

        //check if side effects are empty
        else if(sideEffectsStr.isEmpty()){
            result = 4;
            return result;
        }

        //check if time is empty
        else if(timeStr.isEmpty()){
            result = 5;
            return result;
        }

        //time does not match either valid time pattern
        else if(!timeMatches1 && !timeMatches2){
            result = 6;
            return result;
        }

        //if dosage is a negative integer
        else if(Integer.parseInt(dosageStr) < 0){
            result = 7;
            return result;
        }

        //if total dosage is a negative integer
        else if(Integer.parseInt(totDosageStr) < 0){
            result = 8;
            return result;
        }

        //nothing is wrong, return the default value
        else{
            return result;
        }
    }


    public int checkCheckboxes(){
        int result;

        CheckBox mon = findViewById(R.id.chkMonday);
        CheckBox tues = findViewById(R.id.chkTuesday);
        CheckBox wed = findViewById(R.id.chkWednesday);
        CheckBox thur = findViewById(R.id.chkThursday);
        CheckBox fri  = findViewById(R.id.chkFriday);
        CheckBox sat = findViewById(R.id.chkSaturday);
        CheckBox sun = findViewById(R.id.chkSunday);

        //check if no days are checked
        if(!mon.isChecked() && !tues.isChecked() && !wed.isChecked()
                && !thur.isChecked() && !fri.isChecked() && !sat.isChecked() && !sun.isChecked()){
            result = 1;
            return result;
        }
        else{
            result = 0;
            return result;
        }
    }



    public void updateEntry(){
        TextView name = findViewById(R.id.nameEdit);
        TextView dosage = findViewById(R.id.dosageEdit);
        TextView totDosage = findViewById(R.id.totDosageEdit);
        TextView sideEffects = findViewById(R.id.SideEffectEdit2);
        TextView time = findViewById(R.id.timeEdit);

        CheckBox mon = findViewById(R.id.chkMonday);
        CheckBox tues = findViewById(R.id.chkTuesday);
        CheckBox wed = findViewById(R.id.chkWednesday);
        CheckBox thur = findViewById(R.id.chkThursday);
        CheckBox fri  = findViewById(R.id.chkFriday);
        CheckBox sat = findViewById(R.id.chkSaturday);
        CheckBox sun = findViewById(R.id.chkSunday);

        map = new HashMap();
        weekdayMap = new HashMap();

        weekdayMap.put("Monday", mon.isChecked());
        weekdayMap.put("Tuesday",tues.isChecked());
        weekdayMap.put("Wednesday",wed.isChecked());
        weekdayMap.put("Thursday", thur.isChecked());
        weekdayMap.put("Friday", fri.isChecked());
        weekdayMap.put("Saturday", sat.isChecked());
        weekdayMap.put("Sunday", sun.isChecked());
        map.put("weekdays", weekdayMap);
        map.put("name", name.getText().toString());
        map.put("dosage", dosage.getText().toString());
        map.put("totalDosage", totDosage.getText().toString());
        map.put("sideEffects", sideEffects.getText().toString());
        map.put("time", time.getText().toString());
        DocumentReference ref = database.collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).document(userMedication.id);
        ref.update(map);

        finish();
    }

    /**
     * @param num The string to be tested to see if it is numeric
     * @return return a boolean that pertains to whether the string is numeric or not.
     */
    public static boolean isNumber(String num){
        try{
            int n = Integer.parseInt(num);
        }catch(NumberFormatException|NullPointerException error){
            return false;
        }
        return true;
    }

}
