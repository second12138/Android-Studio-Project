package com.example.csci3130project;
import java.util.*;
public class Validator {
    private String password;

    public Validator(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    /**
     *
     * @return
     * This method returns an int based on how many basic validation rules the entered password
     * has violated.
     */
    public int verify(){
        String check1 = "password";
        String check2 = ".{8,}";


        if(!password.toLowerCase().matches(check1)&&password.matches(check2)){
            System.out.println("Passed the basic validation");
            return 0;

        }
        else{
            if (password.toLowerCase().matches(check1)) {

                System.out.println("It fails the rule number 1, password not strong") ;
            }
            if (!password.matches(check2)) {

                System.out.println("It fails the rule number 2, password not strong");
            }
            return 1;

        }

    }

    /**
     *
     * @return
     * This method returns an int based on how many password strength rules the entered password
     * has violated. This is based on the complex validator method from assignment 2
     */
    public int verify2(){
        //a digit must occur at least once
        String check3 = "^(?=.*[0-9]).{8,}$";
        // a lower and upper case letter must occur at least once
        String check4 = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        //a special character must occur at least once
        String check5 = "^(?=.*[@#$%^&+=]).{8,}$";
        String check6 = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        if (!password.matches(check6)){
            if (!password.matches(check3)) {
                System.out.println("It fails the rule a digit must occur at least once");

            }
            if (!password.matches(check4)) {
                System.out.println("It fails the rule a lower and upper case letter must occur at least once");

            }
            if (!password.matches(check5)) {
                System.out.println("It fails the rule a special character must occur at least once");

            }
            return 1;
        }
        else {
            System.out.println("passed Complex validation");
            return 0;
        }

    }
}


