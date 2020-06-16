package com.example.csci3130project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.csci3130project.Models.UserMedication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.csci3130project.Models.Medication;
import com.example.csci3130project.PharmacyViewHolder.UserListView;

public class newUserList extends AppCompatActivity {

    private FirebaseFirestore database;
    private FirestoreRecyclerAdapter adapter;
    Button toHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_list);
        toHome = findViewById(R.id.back_home_button);

        database = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = findViewById(R.id.userList);
        adapter = setUpAdapter(database);

        setUpRecyclerView(recyclerView,adapter);


        //go back to the home activity
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent change = new Intent(newUserList.this, HomeActivity.class);
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
        Intent intent = new Intent(newUserList.this, PharmacyList.class);
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


    //Creates a Firestore adapter to be used with a Recycler view.
    //More info on this: https://github.com/firebase/FirebaseUI-Android/blob/master/firestore/README.md
    private FirestoreRecyclerAdapter setUpAdapter(FirebaseFirestore db){

        Query query = db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).orderBy("name").limit(50);
        FirestoreRecyclerOptions<UserMedication> options = new FirestoreRecyclerOptions.Builder<UserMedication>()
                .setQuery(query,UserMedication.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<UserMedication, UserListView>(options){
            //For each item in the database connect it to the view
            @Override
            public void onBindViewHolder(UserListView holder, int position, final UserMedication model)
            {
                holder.name.setText(model.name);
                holder.dosage.setText(model.dosage);

                //Set the on click for the button
                // e.g. setOnClickListener ((View v) -> ....
                holder.detailsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(newUserList.this,UserMedicationDetails.class);
                        intent.putExtra("UserMedication",model);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public UserListView onCreateViewHolder(ViewGroup group, int i)
            {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.medication_entry,group,false);
                return new UserListView(view);
            }
        };
        return adapter;

    }
}
