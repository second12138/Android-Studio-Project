package com.example.csci3130project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import static android.app.AlarmManager.*;
import android.app.NotificationManager;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    Button  logoutButton;
    Button toUserListButton;
    CalendarView calendar;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //added a calendar to the
        calendar = findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new dayChange());
        logoutButton = findViewById(R.id.logout_button);
        toUserListButton = findViewById(R.id.to_userlist_button);

        //find button from xml layout file
        Button notification = findViewById(R.id.button_notification);

        // waits for button to click

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY,16);
                calendar.set(Calendar.MINUTE,23);
                calendar.set(Calendar.SECOND,30);


                Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                // Rtc.wakeup triggers the alarm even if device is in sleep mode
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), INTERVAL_DAY,pendingIntent);


            }
        });



        //logout current user
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent change = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(change);
            }
        });

        /**
         * Here we have a button on the home landing page where the user can transfer to the
         * user list activity where they can view their personal medication list
         */
        toUserListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent change = new Intent(HomeActivity.this, newUserList.class);
                startActivity(change);
            }
        });


    }
    public class dayChange implements CalendarView.OnDateChangeListener
    {
        @Override
        public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth)
        {
            month = month + 1;
            String DOM      = "";
            String newMonth = "";
            if ( dayOfMonth < 10 )
            {
                DOM = "0" + dayOfMonth;
            }
            else
                DOM = "" + dayOfMonth;
            if ( month < 10 )
            {
                newMonth = "0" + month;
            }
            else
                newMonth = "" + month;
            String date = DOM + "-" + newMonth + "-" + year;

            Intent intent = new Intent(HomeActivity.this, CalendarDetailsActivity.class);
            intent.putExtra("date", date);
            startActivity(intent);
        }
    }
}
