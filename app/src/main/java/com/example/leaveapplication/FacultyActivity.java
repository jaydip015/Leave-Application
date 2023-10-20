package com.example.leaveapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.leaveapplication.Fragments.ApprovedFragment;
import com.example.leaveapplication.Fragments.NotAprovedFragment;
import com.example.leaveapplication.Fragments.PendingFragment;
import com.example.leaveapplication.adapters.progressbarAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class FacultyActivity extends AppCompatActivity {
    FirebaseAuth auth;

    FirebaseDatabase database;
    public static final String HOD_UID="55Tm5i2TYHUZDj77NgvdoLHAqjB2";
    public static final String CHILD="Leave Applications";
    String faculty_name =" ";
    ArrayList<Map<String,String> > recevied=new ArrayList<>();
    progressbarAdapter dialog;

    BottomNavigationView bnv;
    Toolbar toolbar;
    FloatingActionButton add;
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
            Intent i=new Intent(FacultyActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        add=findViewById(R.id.add);
        auth=FirebaseAuth.getInstance();
        faculty_name =auth.getCurrentUser().getDisplayName();
        bnv=findViewById(R.id.bottomnavigation);


        database=FirebaseDatabase.getInstance();
//        fetchCurrentData();
        setFragment(new ApprovedFragment());
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                if(id==R.id.aproved){
                    setFragment(new ApprovedFragment());
                } else if (id==R.id.notaproved) {
                    setFragment(new NotAprovedFragment());
                } else if (id==R.id.yaprove) {
                    setFragment(new PendingFragment());
                }
                return true;
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyActivity.this, From.class));
            }
        });



    }
    public void fetchCurrentData(){
        database.getReference().child(HOD_UID).child("Leave Applications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dialog =new progressbarAdapter(FacultyActivity.this,"Please wait..\nChecking DataBase");
                dialog.show();
                recevied= (ArrayList<Map<String, String>>) snapshot.getValue();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                },4000);
                Toast.makeText(FacultyActivity.this, "fetching done", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FacultyActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void setFragment(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction ft=manager.beginTransaction();
        ft.replace(R.id.container,fragment);
        ft.commit();
    }
}