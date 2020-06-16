package com.example.csci3130project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.csci3130project.Models.Medication;
import com.example.csci3130project.PharmacyViewHolder.PharmacyView;

public class PharmacyList extends AppCompatActivity {

    private FirebaseFirestore database;
    private FirestoreRecyclerAdapter adapter;
    Button toHome;

    /**
     *
     * Here we display the pharmacy list recycler view (set up with the adapter below)
     * and set an onClickListener to the toHome button so the user can return to the homepage from
     * the pharmacy list
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pharmacy_list);
        toHome = findViewById(R.id.back_home_button);

        database = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = findViewById(R.id.pharmacyList);
        adapter = setUpAdapter(database);

        setUpRecyclerView(recyclerView,adapter);


        //go back to the home activity
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent change = new Intent(PharmacyList.this, HomeActivity.class);
                startActivity(change);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void addMedicationButton_onClick(View view){
        Intent intent = new Intent(PharmacyList.this, AddMedication.class);
        startActivity(intent);
    }


    //Connects our recycler view with the viewholder (how we want to show our model[data])
    // and the firestore adapter
    private void setUpRecyclerView(RecyclerView rv, FirestoreRecyclerAdapter adapter){
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }



    private FirestoreRecyclerAdapter setUpAdapter(FirebaseFirestore db){
        Query query = db.collection("Medication").orderBy("name").limit(50);
        FirestoreRecyclerOptions<Medication> options = new FirestoreRecyclerOptions.Builder<Medication>()
                .setQuery(query,Medication.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Medication, PharmacyView>(options){
            //For each item in the database connect it to the view

            /**
             *
             * @param holder The PharmacyView viewholder
             * @param position
             * @param model The model is our Medication object
             *
             * For each item in the database we connect it with a view holder in our recyclerview
             */
            @Override
            public void onBindViewHolder(PharmacyView holder, int position, final Medication model)
            {
                holder.name.setText(model.name);
                holder.dosage.setText(model.dosage);

                //Set the on click for the button
                // e.g. setOnClickListener ((View v) -> ....
                holder.detailsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PharmacyList.this,MedicationDetails.class);
                        intent.putExtra("Medication",model);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public PharmacyView onCreateViewHolder(ViewGroup group, int i)
            {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.medication_entry,group,false);
                return new PharmacyView(view);
            }
        };
        return adapter;

    }
}
