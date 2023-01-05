package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity {

    EditText inputNumber, inputPass;
    Button login,btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogin);
        inputNumber = findViewById(R.id.number);
        inputPass = findViewById(R.id.password);
        login= findViewById(R.id.loginButton);
        btn = findViewById(R.id.loginButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCrededentials();
            }
        });




    }

    private void checkCrededentials(){
        String number = inputNumber.getText().toString();
        String password= inputPass.getText().toString();

        if (number.isEmpty() || !number.contains("01"))
        {
            showError(inputNumber,"number is not valid");
        } else if (password.isEmpty() || password.length()<5)
        {
            showError(inputPass, "password must be 7 number");
        } else if(number.contains("01")){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(login.this,MainActivity.class));
                    finish();
                }
            });
        }
    }
    private void showError(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }
}