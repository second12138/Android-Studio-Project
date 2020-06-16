package com.example.csci3130project.Models;

import java.io.Serializable;

/**
 * Firestore medication object used in the pharmacy list
 */
public class Medication implements Serializable{

    public String name;
    public String dosage;
    public String sideEffects;
    public String id;

    public Medication(){
        this.name = "";
        this.dosage = "";
        this.sideEffects = "";

    }
    public Medication(String name, String dosage, String sideEffects){
        this.name = name;
        this.dosage = dosage;
        this.sideEffects = sideEffects;

    }

    @Override
        public String toString() {return "Name: " + name + "Dosage: " + dosage +
            "Side Effects: " + sideEffects;}

}
