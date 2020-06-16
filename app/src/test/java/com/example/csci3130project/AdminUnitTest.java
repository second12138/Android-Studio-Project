package com.example.csci3130project;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdminUnitTest {
    AddMedication a = new AddMedication();

    @Test
    public void nameStringIsEmpty() {
        String name = "";
        String dosage = "2";
        String sideEffects = "bala";
        int result = a.addToMedicationList(name, dosage, sideEffects);
        assertEquals(result, 1);
    }

    @Test
    public void dosageStringIsEmpty() {
        String name = "some";
        String dosage = "";
        String sideEffects = "bala";
        int result = a.addToMedicationList(name, dosage, sideEffects);
        assertEquals(result, 10);
    }

    @Test
    public void sideEffectsStringIsEmpty() {
        String name = "dsa";
        String dosage = "2";
        String sideEffects = "";
        int result = a.addToMedicationList(name, dosage, sideEffects);
        assertEquals(result, 100);
    }

    @Test
    public void nameAndDosageStringIsEmpty() {
        String name = "";
        String dosage = "";
        String sideEffects = "bala";
        int result = a.addToMedicationList(name, dosage, sideEffects);
        assertEquals(result, 11);
    }

    @Test
    public void nameAndSideEffectsStringIsEmpty() {
        String name = "";
        String dosage = "2";
        String sideEffects = "";
        int result = a.addToMedicationList(name, dosage, sideEffects);
        assertEquals(result, 101);
    }

    @Test
    public void dosageAndSideEffectStringIsEmpty() {
        String name = "a3";
        String dosage = "";
        String sideEffects = "";
        int result = a.addToMedicationList(name, dosage, sideEffects);
        assertEquals(result, 110);
    }

    @Test
    public void EverythingIsEmpty() {
        String name="";
        String dosage="";
        String sideEffects="";
        int result=a.addToMedicationList(name,dosage,sideEffects);
        assertEquals(result,111);
    }

}
