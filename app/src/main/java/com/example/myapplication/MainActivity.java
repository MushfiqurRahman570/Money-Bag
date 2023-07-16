package com.example.myapplication;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.sign.login;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    ImageView imageMenu,notification_menu;
    RelativeLayout scan,addMoney,myQr,sendMoney;
    Button sign_off;
    TextView balance;
    FirebaseAuth mfirebaseAuth;
    FirebaseDatabase dbs = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    DocumentReference documentReference;
    String uid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<userModel>  mArrayList1= new ArrayList<>();
    ArrayList<userModel>  mArrayList2= new ArrayList<>();
    boolean isAddSum = true;
    boolean isSendSum = false;

    BalanceHelper c = new BalanceHelper();

   private Double totalAdd;
   private Double totalSend;
   private Double sendSum;
   private Double addSum;
   private Double currentBalance;
   Double AddMoney;
   Double SendMoney;
    Boolean  isAdd = true;
    Boolean isSend = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        imageMenu = findViewById(R.id.imageMenu);
        scan = findViewById(R.id.scanQr);
        myQr = findViewById(R.id.myQr);
        addMoney = findViewById(R.id.addMoney);
        sendMoney = findViewById(R.id.send);
        mfirebaseAuth = FirebaseAuth.getInstance();
        balance = findViewById(R.id.checkBalance);
        notification_menu = findViewById(R.id.notification_menu);



        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();
            }
        });

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle phone = getIntent().getExtras();
                String userId = phone.getString("phoneNo");
                uid=userId;
                mArrayList1.clear();
                totalAdd =0.0;
                Intent i = new Intent(MainActivity.this, AddMoney.class);
                i.putExtra("phoneNo", userId);
                startActivity(i);
            }
        });

        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle phone = getIntent().getExtras();
                String userId = phone.getString("phoneNo");
                uid=userId;
                mArrayList2.clear();
                totalSend =0.0;
                Intent i = new Intent(MainActivity.this, SendMoney.class);
                i.putExtra("phoneNo", userId);
                startActivity(i);
            }
        });

        myQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle phone = getIntent().getExtras();
                String userId = phone.getString("phoneNo");

                Intent i = new Intent(MainActivity.this, MyQr.class);
                i.putExtra("phoneNo", userId);
                startActivity(i);
            }
        });

        notification_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle phone = getIntent().getExtras();
                String userId = phone.getString("phoneNo");

                Intent i = new Intent(MainActivity.this, history.class);
                i.putExtra("phoneNo", userId);
                startActivity(i);
            }
        });


        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.profile:

                        Bundle phone = getIntent().getExtras();
                        String uid = phone.getString("phoneNo");
                        Intent i = new Intent(MainActivity.this, profile.class);

                        i.putExtra("phoneNo", uid);
                        startActivity(i);

//                        Toast.makeText(MainActivity.this, "My Qr", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.sign_off:
                            mfirebaseAuth.signOut();
                            Intent intent = new Intent(MainActivity.this, login.class);
                            startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.MyQr:
                        Bundle phoneNo = getIntent().getExtras();
                        String Uid = phoneNo.getString("phoneNo");
                        Intent in = new Intent(MainActivity.this, MyQr.class);
                        in.putExtra("phoneNo", Uid);
                        startActivity(in);
                        drawerLayout.closeDrawers();
                        break;
                }

                return false;
            }
        });
        imageMenu = findViewById(R.id.imageMenu);

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code Here
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.QR_CODE);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("scanning");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if (result!=null && result.getContents()!=null){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Scan Result")
                    .setMessage(result.getContents())
                    .setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            ClipData data = ClipData.newPlainText("result",result.getContents());
                            manager.setPrimaryClip(data);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public  void  getTotalAddMoney(String uid){




    }


    // send

    public  void  getTotalSendMoney(String uid){



    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        mArrayList1.clear();
        mArrayList2.clear();
        totalAdd =0.0;
        totalSend =0.0;
//        addSum = 0.0;
//        double sendSum = 0.0;
        Bundle phone = getIntent().getExtras();
        String userId = phone.getString("phoneNo");
        uid =userId;


        try {

            Log.d(TAG, "process add ");
            getTotalAddMoney(uid);
            Log.d(TAG, "addDone ");

            Log.d(TAG, "process send ");
            getTotalSendMoney(uid);
            Log.d(TAG, "sendDone ");



            //send

            db.collection("userList").document(uid).collection("sendMoney").get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.d(TAG, "onSuccess: LIST EMPTY");
                                return;
                            } else {

                                List<userModel> acc = documentSnapshots.toObjects(userModel.class);
                                mArrayList2.addAll(acc);
                                acc.clear();

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                                    mArrayList2.forEach((n) ->

                                            {
                                                Double value = Double.parseDouble(n.sendMoney);
                                                totalSend += value;

                                            }
                                    );

                                    c.setBal(totalSend);
                                    double sendSum = c.getBal();
                                    Log.d(TAG, "total Send: " + sendSum);

                                }
                            }
                        }
                    });

            //add

            db.collection("userList").document(uid).collection("addMoney").get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.d(TAG, "onSuccess: LIST EMPTY");
                                return;
                            } else {

                                List<userModel> acc = documentSnapshots.toObjects(userModel.class);
                                mArrayList1.addAll(acc);
                                acc.clear();


                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                                    mArrayList1.forEach((n) ->

                                            {

                                                Double value = Double.parseDouble(n.addMoney);
                                                totalAdd += value;


                                            }
                                    );


                                    c.setBal(totalAdd);
                                    addSum = c.getBal();
                                    Log.d(TAG, "total AddMoney: " + addSum.toString());

                                }
                            }
                        }
                    });
//            Log.d(TAG, "total Send: " +addSum.toString()+ sendSum.toString());
//            Current(addSum,sendSum);
//            Log.d(TAG, "XXXXXXXXXXX---XZXXX  : " +addSum.toString() +"-----"+sendSum.toString()+"full"+Current(addSum,sendSum).toString());

            
            balance.setText(sendSum.toString());


        }catch (Exception e){
            Log.d(TAG, "error: " + mArrayList1);
            Log.d(TAG, "error: " + mArrayList2);
        }
        finally {

//            Log.d(TAG, "balance: "+Current(addSum,sendSum).toString());
//            balance.setText(addSum.toString());
//            Log.d(TAG, "total Send: " +addSum.toString()+ sendSum.toString());
        }

        super.onStart();


    }


//    Double Current( double addSum, double sendSum)
//    {
//        currentBalance = addSum-sendSum;
////        balance.setText(currentBalance.toString());
//        return  currentBalance;
//    }


    }

