package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {


    TextView fullNameField, usernameField,fullName, email, phoneNo, userName;
    ImageView pro_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fullName = findViewById(R.id.pro_fullName);
        email = findViewById(R.id.pr_email);
        phoneNo = findViewById(R.id.pro_number);
        userName = findViewById(R.id.pro_username);
        pro_back = findViewById(R.id.pro_back);
        fullNameField = findViewById(R.id.fullNameField);
        usernameField = findViewById(R.id.userNameField);


        pro_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this, MainActivity.class));
            }
        });
        getData();

//        User();
//        showAllUserData();


    }





//    private void showAllUserData() {
//        Intent intent = getIntent();
//        String user_username = intent.getStringExtra("username");
//        String user_name = intent.getStringExtra("name");
//        String user_email = intent.getStringExtra("email");
//        String user_phoneNo = intent.getStringExtra("phoneNo");
//        String user_password = intent.getStringExtra("username");
//
//        fullNameField.setText(user_name);
//        usernameField.setText(user_username);
//        fullName.getEditText().setText(user_name);
//        email.getEditText().setText(user_email);
//        phoneNo.getEditText().setText(user_phoneNo);
//        userName.getEditText().setText(user_password);
//    }
    private void getData(){
        FirebaseDatabase myDataBase= FirebaseDatabase.getInstance();
        DatabaseReference myDB = myDataBase.getReference("users");
        Bundle phone = getIntent().getExtras();
        if (phone != null) {
            String userId = phone.getString("phoneNo");

            myDB.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserhelperClass userhelperClass = snapshot.getValue(UserhelperClass.class);

                    phoneNo.setText(userhelperClass.phoneNo.toString());
                    userName.setText(userhelperClass.username.toString());
                    email.setText(userhelperClass.email.toString());
                    fullName.setText(userhelperClass.name.toString());
                    fullNameField.setText(userhelperClass.name.toString());
                    usernameField.setText(userhelperClass.username.toString());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {

        }
    }
//    public void User() {
//        final String userEnteredUsername = phoneNo.getEditText().getText().toString().trim();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//        Query checkUser = reference.orderByChild("phoneNo").equalTo(userEnteredUsername);
//
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//
//                    phoneNo.setError(null);
//                    phoneNo.setErrorEnabled(false);
//                    String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
//                    String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
//                    String phoneNoFromDB = dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
//                    String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
//                    Intent intent = new Intent(getApplicationContext(), profile.class);
//
//                    intent.putExtra("name", nameFromDB);
//                    intent.putExtra("username", usernameFromDB);
//                    intent.putExtra("email", emailFromDB);
//                    intent.putExtra("phoneNo", phoneNoFromDB);
//
//                    startActivity(intent);
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
