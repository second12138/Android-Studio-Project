package com.example.csci3130project;

import android.content.Context;

import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

//import androidx.test.ext.junit.runners.AndroidJUnit4;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest

public class PasswordValidationTest {


    @Rule
    public ActivityTestRule<RegisterActivity> activityRule =
            new ActivityTestRule<>(RegisterActivity.class);
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.csci3130project", appContext.getPackageName());
    }
    @Test
    public void passwordcheck(){
        onView(withId(R.id.password_register)).perform(typeText("Paaw"),closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.textViewPasswordAdvisor)).check(matches(withText("password not strong")));
    }
    @Test
    public void passwordcheck2(){
        onView(withId(R.id.password_register)).perform(typeText("Paawword1"),closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.textViewPasswordAdvisor)).check(matches(withText("basic password")));
    }
    @Test
    public void passwordcheck3(){
        onView(withId(R.id.password_register)).perform(typeText("Paawword@"),closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.textViewPasswordAdvisor)).check(matches(withText("basic password")));
    }
    @Test
    public void passwordcheck4(){
        onView(withId(R.id.password_register)).perform(typeText("Paawword1@"),closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.textViewPasswordAdvisor)).check(matches(withText("complex password")));
    }

}
