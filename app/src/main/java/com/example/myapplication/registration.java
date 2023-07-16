package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.sign.login;
import com.example.myapplication.sign.otp;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {

    Button upBtn, logBtn;
    TextInputLayout regName, reUsername, regEmail, regPhone, regPassword;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        upBtn = findViewById(R.id.upBtn);
        logBtn = findViewById(R.id.logBtn);
        regName = findViewById(R.id.name);
        reUsername = findViewById(R.id.Username);
        regEmail = findViewById(R.id.Email);
        regPhone = findViewById(R.id.Phone);
        regPassword = findViewById(R.id.Password);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registration.this, login.class));
            }
        });
    }

    private boolean validateName() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateUserName() {
        String val = reUsername.getEditText().getText().toString();
        String noWhiteSpace="\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            reUsername.setError("Field cannot be empty");
            return false;
        } else if(val.length()>=15){
            reUsername.setError("Username too long");
            return true;
        }else if(!val.matches(noWhiteSpace)){
            reUsername.setError("White Space are not Allowed");
            return false;}
        else {
            reUsername.setError(null);
            return false;
        }
    }
    private boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9_-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if(!val.matches(emailPattern)){
            regEmail.setError("Invalid Email");
            return false;
        }else {
            regEmail.setError(null);
            return false;
        }
    }
    private boolean validatePhone() {
        String val = regPhone.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhone.setError("Field cannot be empty");
            return false;
        } else {
            regPhone.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "0-9";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if(val.matches(passwordVal)){
            regPassword.setError("Password should Number");
            return false;
        }else {
            regPassword.setError(null);
            return true;
        }
    }

    public void registerUser(View view){


        String name = regName.getEditText().getText().toString();
        String userName = reUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String regPhoneNo = regPhone.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();


//        try {
//            if (!validateEmail()|!validateName()|!validatePhone()|!validateUserName()|!validatePassword()){
//            return;
//            }

        try {
            UserhelperClass userhelperClass = new UserhelperClass(name, userName, email, regPhoneNo, password);
            reference.child(regPhoneNo).setValue(userhelperClass);
            Toast.makeText(this, "Your Account has been Created Successfully", Toast.LENGTH_SHORT).show();

        } catch(Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }finally {
            Intent intent = new Intent(getApplicationContext(), otp.class);
            intent.putExtra("phoneNo", regPhoneNo);
            startActivity(intent);
        }

    }



}


