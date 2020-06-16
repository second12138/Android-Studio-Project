package com.example.csci3130project;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginActivityUnitTest {
    LoginActivity la=new LoginActivity();
    @Test
    public void FailWithNoPasswordInput() {
        String password="";
        String email="amous@famous.com";
        int result=la.loginFunc(email,password);
        assertEquals(result,2);
    }

    @Test
    public void FailWithNoEmailInput() {
        String password="19980420qzy@";
        String email="";
        int result=la.loginFunc(email,password);
        assertEquals(result,1);
    }

    @Test
    public void FailWithNoInputAtAll() {
        String password="";
        String email="";
        int result=la.loginFunc(email,password);
        assertEquals(result,3);
    }

// TODO: Success case be tested in ESPRESSO TEST.


}
