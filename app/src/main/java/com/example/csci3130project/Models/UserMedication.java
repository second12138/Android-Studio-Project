package com.example.csci3130project.Models;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.*;
import java.util.*;

/**
 * This is a firebase object that represents the medication in the user's list
 * This has more user-specific information than the pharmacy list medication object.
 * This displays in the UserMedicationDetails activity
 */
public class UserMedication implements Serializable{

    public String name;
    public String dosage;
    public String totalDosage;
    public String id;
    public Map<String, Boolean> weekdays = new HashMap<>();
    public String time;
    public String sideEffects;



    public UserMedication(){
        this.name = "";
        this.dosage = "";
        this.totalDosage = "";
        this.time = "";
        this.sideEffects = "";
        this.weekdays.put("Monday", false);
        this.weekdays.put("Tuesday", false);
        this.weekdays.put("Wednesday", false);
        this.weekdays.put("Thursday", false);
        this.weekdays.put("Friday", false);
        this.weekdays.put("Saturday", false);
        this.weekdays.put("Sunday", false);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public UserMedication(String name, String dosage, String totalDosage, String time, String sideEffect) {
        this.name = name;

        this.dosage = dosage;

        this.totalDosage = totalDosage;

        this.time = time;

        this.sideEffects = sideEffect;

    }

    public void setMon(Boolean b)
    {
        this.weekdays.put("Monday", b);
    }
    public void setTues(Boolean b)
    {
        this.weekdays.put("Tuesday", b);
    }
    public void setWed(Boolean b)
    {
        this.weekdays.put("Wednesday", b);
    }
    public void setThurs(Boolean b)
    {
        this.weekdays.put("Thursday", b);
    }
    public void setFri(Boolean b)
    {
        this.weekdays.put("Friday", b);
    }
    public void setSat(Boolean b)
    {
        this.weekdays.put("Saturday", b);
    }
    public void setSun(Boolean b)
    {
        this.weekdays.put("Sunday", b);
    }
    public boolean getMon()
    {
        return this.weekdays.get("Monday");
    }
    public boolean getTues()
    {
        return this.weekdays.get("Tuesday");
    }
    public boolean getWed()
    {
        return this.weekdays.get("Wednesday");
    }
    public boolean getThurs()
    {
        return this.weekdays.get("Thursday");
    }
    public boolean getFri()
    {
        return this.weekdays.get("Friday");
    }
    public boolean getSat()
    {
        return this.weekdays.get("Saturday");
    }
    public boolean getSun()
    {
        return this.weekdays.get("Sunday");
    }



    //@Override
   //public String toString() {return "Name: " + name + "Dosage: " + dosage + "Schedule of take medication: " +Arrays.toString(l1.toArray())+"Time: "+df.format(time) ;}

}
