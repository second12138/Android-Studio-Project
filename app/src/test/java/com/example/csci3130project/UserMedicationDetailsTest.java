package com.example.csci3130project;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserMedicationDetailsTest {

    UserMedicationDetails um = new UserMedicationDetails();
    String nameTest;
    String doseTest;
    String totDoseTest;
    String sideEffectsTest;
    String timeTest;

    @Test
    public void failWithEmptyName(){

        nameTest = "";
        doseTest = "2";
        totDoseTest = "40";
        sideEffectsTest = "Pain";
        timeTest = "23:00";

        int result = um.checkFields(nameTest, doseTest, totDoseTest, sideEffectsTest, timeTest);

        //if name is empty and other fields are fine, we should receive 1 from this function
        assertEquals(result, 1);
    }

    @Test
    public void failWithEmptyDosage(){

        nameTest = "name";
        doseTest = "";
        totDoseTest = "40";
        sideEffectsTest = "Pain";
        timeTest = "23:00";

        int result = um.checkFields(nameTest, doseTest, totDoseTest, sideEffectsTest, timeTest);

        //if dosage is empty and other fields are fine, we should receive 2 from this function
        assertEquals(result, 2);
    }

    @Test
    public void failWithNegativeDosage(){

        nameTest = "name";
        doseTest = "-2";
        totDoseTest = "40";
        sideEffectsTest = "Pain";
        timeTest = "23:00";

        int result = um.checkFields(nameTest, doseTest, totDoseTest, sideEffectsTest, timeTest);

        //if dosage is negative and other fields are fine, we should receive 2 from this function
        assertEquals(result, 7);
    }

    @Test
    public void failWithEmptyTotDosage(){

        nameTest = "name";
        doseTest = "2";
        totDoseTest = "";
        sideEffectsTest = "Pain";
        timeTest = "23:00";

        int result = um.checkFields(nameTest, doseTest, totDoseTest, sideEffectsTest, timeTest);

        //if total dosage is empty and other fields are fine, we should receive 3 from this function
        assertEquals(result, 3);
    }

    @Test
    public void failWithNegativeTotDosage(){

        nameTest = "name";
        doseTest = "2";
        totDoseTest = "-40";
        sideEffectsTest = "Pain";
        timeTest = "23:00";

        int result = um.checkFields(nameTest, doseTest, totDoseTest, sideEffectsTest, timeTest);

        //if total dosage is negative and other fields are fine, we should receive 3 from this function
        assertEquals(result, 8);
    }

    @Test
    public void failWithEmptySideEffects(){

        nameTest = "name";
        doseTest = "2";
        totDoseTest = "40";
        sideEffectsTest = "";
        timeTest = "23:00";

        int result = um.checkFields(nameTest, doseTest, totDoseTest, sideEffectsTest, timeTest);

        //if side effects is empty and other fields are fine, we should receive 4 from this function
        assertEquals(result, 4);
    }

    @Test
    public void failWithEmptyTime() {

        nameTest = "name";
        doseTest = "2";
        totDoseTest = "40";
        sideEffectsTest = "Pain";
        timeTest = "";

        int result = um.checkFields(nameTest, doseTest, totDoseTest, sideEffectsTest, timeTest);

        //if time is empty and other fields are fine, we should receive 5 from this function
        assertEquals(result, 5);
    }

    @Test
    public void failWithInvalidTime() {

        nameTest = "name";
        doseTest = "2";
        totDoseTest = "40";
        sideEffectsTest = "Pain";
        timeTest = "25:00";

        int result = um.checkFields(nameTest, doseTest, totDoseTest, sideEffectsTest, timeTest);

        //if time is invalid and other fields are fine, we should receive 7 from this function
        assertEquals(result, 6);
    }

    @Test
    public void failWithNonIntDosage(){
        nameTest = "name";
        doseTest = "abc";
        totDoseTest = "40";
        sideEffectsTest = "Pain";
        timeTest = "23:00";

        int result = um.checkFields(nameTest, doseTest, totDoseTest, sideEffectsTest, timeTest);

        //if dosage is negative and other fields are fine, we should receive 2 from this function
        assertEquals(result, 2);
    }

    @Test
    public void failWithNonIntTotDosage(){
        nameTest = "name";
        doseTest = "2";
        totDoseTest = "abc";
        sideEffectsTest = "Pain";
        timeTest = "23:00";

        int result = um.checkFields(nameTest, doseTest, totDoseTest, sideEffectsTest, timeTest);

        //if dosage is negative and other fields are fine, we should receive 2 from this function
        assertEquals(result, 3);
    }

}