package com.example.leaveapplication;


import static com.example.leaveapplication.FacultyActivity.CHILD;
import static com.example.leaveapplication.FacultyActivity.HOD_UID;
import static com.example.leaveapplication.LoginActivity.CUSER;
import static com.example.leaveapplication.LoginActivity.EDATE;
import static com.example.leaveapplication.LoginActivity.FNAME;
import static com.example.leaveapplication.LoginActivity.LEAVEREASON;
import static com.example.leaveapplication.LoginActivity.SDATE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class From extends AppCompatActivity {
    TextView startdate,enddate;
    EditText reason;
    Button submit;
    boolean fetchedonce=false;
    FirebaseDatabase database;
    ArrayList<Map<String,String>> recevied=new ArrayList<>();
    ArrayList<Map<String,String>> yettoaprove=new ArrayList<>();
    FirebaseAuth auth;
    public static final String aprroved="Approved";
    public static final String yettoaprroved="Yet to Approve";
    public static final String notaprroved="Not Approved";
    public static final String UNIQUEID="time stamp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        startdate=findViewById(R.id.start_date);
        enddate=findViewById(R.id.end_date);
        reason=findViewById(R.id.reason);
        submit=findViewById(R.id.submitf);
        feching();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SystemClock.sleep(4000);
                Map<String ,String > map=new HashMap<>();
                map.put(CUSER,auth.getUid());
                map.put(UNIQUEID,String.valueOf(System.currentTimeMillis()));
                map.put(SDATE,startdate.getText().toString());
                map.put(EDATE,enddate.getText().toString());
                map.put(LEAVEREASON,reason.getText().toString());
                map.put(FNAME,auth.getCurrentUser().getDisplayName());
                recevied.add(map);
                database.getReference().child(HOD_UID).child(CHILD).setValue(recevied).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Log.d("tag","3");
                        yettoaprove.add(map);
                        database.getReference().child(auth.getUid()).child(yettoaprroved).setValue(yettoaprove).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("tag","4");

                                Toast.makeText(From.this, "done", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(From.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(From.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(startdate);
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(enddate);
            }
        });
    }
    public void feching(){
        database.getReference().child(HOD_UID).child(CHILD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                recevied= (ArrayList<Map<String, String>>) snapshot.getValue();
                Log.d("tag","1");
                Log.d("tag",recevied.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(From.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        database.getReference().child(auth.getUid()).child(yettoaprroved).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                yettoaprove= (ArrayList<Map<String, String>>) snapshot.getValue();
                Log.d("tag","2");
                Log.d("tag",yettoaprove.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(From.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void showDatePicker(TextView date) {
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPositiveButtonClick(Object selection) {
                Calendar cal=Calendar.getInstance();
                cal.setTimeInMillis((Long) selection);
                int selectedYear = cal.get(Calendar.YEAR);
                int selectedMonth = cal.get(Calendar.MONTH);
                int selectedDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
//                calendar.set(selectedYear,selectedMonth,selectedDayOfMonth);
                date.setText(materialDatePicker.getHeaderText());
            }
        });

    }


}