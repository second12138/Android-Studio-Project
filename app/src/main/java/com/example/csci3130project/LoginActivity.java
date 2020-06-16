package com.example.csci3130project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    EditText loginEmail, loginPassword;
    Button loginButton;
    TextView registerText;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.email_login);
        loginPassword = findViewById(R.id.password_login);
        loginButton = findViewById(R.id.login_button);
        registerText = findViewById(R.id.not_registered);

        mAuthListen = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = mAuth.getCurrentUser();
                if (mUser != null) {
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent change = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(change);
                } else {
                    //Toast.makeText(LoginActivity.this, "Please login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = loginEmail.getText().toString();
                String userPassword = loginPassword.getText().toString();
                int flag = loginFunc(userEmail, userPassword);
                if (flag == 1) {
                    loginEmail.setError("Please enter a valid email");
                    loginEmail.requestFocus();
                } else if (flag == 2) {
                    loginPassword.setError("Please enter a password");
                    loginPassword.requestFocus();
                } else if (flag == 3) {
                    Toast.makeText(LoginActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(change);
            }
        });
    }

    /**
     *
     * @param userEmail
     * This is the existing email that the user uses to log in with
     * @param userPassword
     * This is the existing password that the user uses to log in with
     * @return
     * This function returns an int based on the error encountered when logging in
     * (e.g.) empty fields or login success/failure
     * @Author: Ziyu Qiu
     */
    public int loginFunc(final String userEmail, final String userPassword) {
        //check for empty fields then prompt user
        int flag;
        if (userEmail.isEmpty()&&!userPassword.isEmpty()) {
            flag = 1;
        } else if (userPassword.isEmpty()&&!userEmail.isEmpty()) {
            flag = 2;
        } else if (userEmail.isEmpty() && userPassword.isEmpty()) {
            //Toast.makeText(LoginActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
            flag = 3;

        } else if (!(userEmail.isEmpty() && userPassword.isEmpty())) {
            mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(LoginActivity.this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                //mAuth.signInWithEmailAndPassword(userEmail,userPassword).catch
                               // Toast.makeText(LoginActivity.this, "Login failed, please try again", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent change = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(change);
                            }
                        }
                    }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    String message = e.getMessage().toString();
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
            flag = 4;
          //TODO: TEST CANNOT BE DONE HERE , may try espresso test later
        } else {
            Toast.makeText(LoginActivity.this, "Error occurred, try again later", Toast.LENGTH_SHORT).show();
            flag = 5;
        }
        return flag;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListen);
    }
}



