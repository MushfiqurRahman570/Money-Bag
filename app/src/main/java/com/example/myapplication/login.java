package com.example.myapplication;

import static android.Manifest.permission_group.PHONE;

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
        setContentView(R.layout.activity_login);
        inputNumber = findViewById(R.id.Number);
        inputPass = findViewById(R.id.password);
        login= findViewById(R.id.loginButton);
        btn = findViewById(R.id.loginButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone= inputNumber.getText().toString();
                checkCrededentials();
                getIntent().putExtra(PHONE,phone);
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

    public String number;

    public String getName() {
        return number;
    }

    public void setName(String number) {
        this.number = number;
    }


    private void showError(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }
}