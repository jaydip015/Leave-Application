package com.example.leaveapplication;

import static com.example.leaveapplication.From.aprroved;
import static com.example.leaveapplication.LoginActivity.CUSER;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leaveapplication.adapters.RVAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RVItemclick,NotAprovecLICK {
    FirebaseAuth auth;
    FirebaseDatabase database;
    Button logout,getdata;
    RecyclerView rv;
    ArrayList<Map<String ,String>> data=new ArrayList<>();
    ArrayList<Map<String ,String>> request=new ArrayList<>();
    ArrayList<Map<String,String>> arrayList=new ArrayList<>();
    RVAdapter adapter;
    Toolbar toolbar;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logoutF) {
            auth.signOut();
            Intent i=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        rv=findViewById(R.id.rv);
        fetchdata();



    }

    private void fetchdata() {
        database.getReference().child(auth.getUid()).child("Leave Applications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(MainActivity.this, "Fetching from database", Toast.LENGTH_SHORT).show();
                data= (ArrayList<Map<String, String>>) snapshot.getValue();
                Toast.makeText(MainActivity.this, "Fetching Done", Toast.LENGTH_SHORT).show();
                adapter=new RVAdapter(data,MainActivity.this,MainActivity.this);
                Log.d("Mtag",snapshot.getValue().toString());
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        database.getReference().child(data.get(position).get(CUSER)).child(aprroved).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList= (ArrayList<Map<String, String>>) snapshot.getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        SystemClock.sleep(400);
        arrayList.add(data.get(position));
        database.getReference().child(data.get(position).get(CUSER)).child(aprroved).setValue(arrayList).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onNotItemClick(int position) {

    }
}