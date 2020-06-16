package com.example.csci3130project;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterActivityUnitTest {

    RegisterActivity ra=new RegisterActivity();
    @Test
    public void FailWithNoPasswordInput() {
        String password="";
        String email="amous@famous.com";
        int result=ra.registerFunction(email,password);
        assertEquals(result,2);
    }

    @Test
    public void FailWithNoEmailInput() {
        String password="19980420qzy@";
        String email="";
        int result=ra.registerFunction(email,password);
        assertEquals(result,1);
    }

    @Test
    public void FailWithNoInputAtAll() {
        String password="";
        String email="";
        int result=ra.registerFunction(email,password);
        assertEquals(result,3);
    }




}

