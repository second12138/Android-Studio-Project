package com.example.csci3130project;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.widget.TextView;
import android.widget.Toast;

import com.example.csci3130project.Models.UserMedication;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CalendarDetailsActivity extends AppCompatActivity
{
    private CalendarAdapter adapter;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    private Intent intent;
    private TextView dow;
    private String newDow;
    private Date nDate;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_details);
        dow = findViewById(R.id.dow);
        newDow = "";
        intent = getIntent();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        newDow = (String) intent.getSerializableExtra("date");
        try
        {
            nDate = format.parse(newDow);

        } catch ( ParseException e )
        {
            Toast.makeText(this, "FAIL", Toast.LENGTH_SHORT).show();
        }


        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
        calendar.setTime(nDate);
        int numDOW = calendar.get(Calendar.DAY_OF_WEEK);


        switch ( numDOW )
        {
            case 1:
                newDow = "Sunday";
                break;
            case 2:
                newDow = "Monday";
                break;
            case 3:
                newDow = "Tuesday";
                break;
            case 4:
                newDow = "Wednesday";
                break;
            case 5:
                newDow = "Thursday";
                break;
            case 6:
                newDow = "Friday";
                break;
            case 7:
                newDow = "Saturday";
                break;
            default:
                break;
        }
        int DayDOW  = calendar.get(Calendar.DAY_OF_MONTH);
        int MonthDOW = calendar.get(Calendar.MONTH)+1;
        int YearDOW = calendar.get(Calendar.YEAR);
        dow.setText(newDow + " " + DayDOW + ", "+ MonthDOW +", "+ YearDOW);
        setUpRecyclerView(newDow);
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        adapter.startListening();
    }


    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }


    /**
     * Set up a recycler view to show prescriptions matching the provided day.
     *
     * @param newDow string representing a weekday that is used to construct the query
     */
    private void setUpRecyclerView(String newDow)
    {
        CollectionReference prescriptRef = database.collection(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Query query = prescriptRef.whereEqualTo("weekdays." + newDow, true);

        FirestoreRecyclerOptions<UserMedication> options =
                new FirestoreRecyclerOptions.Builder<UserMedication>()
                        .setQuery(query, UserMedication.class).build();

        adapter = new CalendarAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.CalendarRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
