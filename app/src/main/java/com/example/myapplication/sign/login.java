package com.example.myapplication.sign;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.profile;
import com.example.myapplication.registration;
import com.example.myapplication.splash_main;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class login extends AppCompatActivity {

    Button signup;
    Button signIn;
    TextInputLayout number, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup = findViewById(R.id.signup);
        signIn = findViewById(R.id.signIn);
        number = findViewById(R.id.number);
        password = findViewById(R.id.signPassword);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, registration.class));
            }
        });
    }

    private boolean validatePhone() {
        String val = number.getEditText().getText().toString();

        if (val.isEmpty()) {
            number.setError("Field cannot be empty");
            return false;
        } else {
            number.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        String passwordVal = "0-9";

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if(val.matches(passwordVal)){
            password.setError("Password should Number");
            return false;
        }else {
            password.setError(null);
            return true;
        }
    }

    public void loginUser(View view) {
        //Validate Login Info
        if (!validatePhone() | !validatePassword()) {
            return;
        }else {
            isUser();
        }
    }

    public void isUser() {
        final String userEnteredUsername = number.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("phoneNo").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    number.setError(null);
                    number.setErrorEnabled(false);
                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);



                    if (passwordFromDB.equals(userEnteredPassword)) {
                        number.setError(null);
                        number.setErrorEnabled(false);
                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNo", phoneNoFromDB);

                        startActivity(intent);

                    } else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                } else {
                    number.setError("No such User exist");
                    number.requestFocus();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
