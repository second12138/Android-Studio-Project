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

//This is Register New User. Main Activity is User Login
public class RegisterActivity extends AppCompatActivity {

    EditText registerEmail, registerPassword;
    Button registerButton;
    TextView signInText, PasswordAdvisor;
    FirebaseAuth mAuth;
     int[] PasswordComment = {0,0,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerEmail = findViewById(R.id.email_register);
        registerPassword = findViewById(R.id.password_register);
        registerButton = findViewById(R.id.register_button);
        signInText = findViewById(R.id.have_account);
        PasswordAdvisor = findViewById(R.id.textViewPasswordAdvisor);

        //final int PasswordComment2 = 0;

        //Password strength and other errors are determined on button click
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
                mAuth = FirebaseAuth.getInstance();
                String newUserEmail = registerEmail.getText().toString();
                String newUserPassword = registerPassword.getText().toString();

                Validator vali = new Validator(newUserPassword);
                int firstValidation = vali.verify();
                if(firstValidation == 1){

                    PasswordComment[0] = 1;
                    PasswordAdvisor.setText("password not strong");
                    //output password not strong
                }
                else {
                    int secondValidation = vali.verify2();
                    if(secondValidation == 1){
                        //basic password

                        PasswordComment[1] = 1;
                        PasswordAdvisor.setText("basic password");
                    }
                    else{
                        //complex password
                        PasswordComment[2] = 1;
                        PasswordAdvisor.setText("complex password");
                    }
                }



                int flag=registerFunction(newUserEmail, newUserPassword);
                if(flag==1){
                    registerEmail.setError("Please enter a valid email");
                    registerEmail.requestFocus();
                }else if(flag==2){
                    registerPassword.setError("Please enter a password");
                    registerPassword.requestFocus();
                }else if(flag==3)
                {
                    Toast.makeText(RegisterActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
                else if(flag==6)
                {
                    Toast.makeText(RegisterActivity.this, "password not strong", Toast.LENGTH_SHORT).show();
                }
                signInText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent change = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(change);
                    }
                });
            }
        });
    }

    /**
     *
     * @param newUserEmail
     * This is the email address specified by a new user. This is null checked
     * @param newUserPassword
     * This is the password entered by a new user. This is null, and strength checked since firebase
     * by default requires stronger passwords.
     * @return
     * This method returns an int based on which error was encountered. 1 2 3 refer to empty fields
     * 5 refers to some other error that cannot be immediately accounted for, and 6 refers to the
     * password not being strong enough.
     *
     * A 4 indicates that the process of registering the user has succeeded
     */
    public int registerFunction(String newUserEmail, String newUserPassword) {
                final int flag;

                //check for empty fields then prompt user
                if (newUserEmail.isEmpty()&&!newUserPassword.isEmpty()) {
                    flag = 1;
                } else if (newUserPassword.isEmpty()&&!newUserEmail.isEmpty()) {
                    flag = 2;
                } else if (newUserEmail.isEmpty() && newUserPassword.isEmpty()) {
                    flag = 3;
                }


                //create user if fields are not empty
                else if (!(newUserEmail.isEmpty() && newUserPassword.isEmpty())) {
                    // mAuth.fetchSignInMethodsForEmail()
                    if (PasswordComment[2] ==0 ) {
                        flag = 6;
                    }

                    else {

                        mAuth.createUserWithEmailAndPassword(newUserEmail, newUserPassword).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }).addOnCompleteListener(RegisterActivity.this,
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Registration failed, please try again", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                        flag = 4;
                    }
                }
                //otherwise some other error occurred
                else {
                    Toast.makeText(RegisterActivity.this, "Error occurred, try again later", Toast.LENGTH_SHORT).show();
                    flag=5;
                }
                return flag;
            }
        }

