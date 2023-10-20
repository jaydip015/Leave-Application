package com.example.leaveapplication.Fragments;

import static com.example.leaveapplication.From.yettoaprroved;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leaveapplication.R;
import com.example.leaveapplication.adapters.pendingAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class PendingFragment extends Fragment {
    ArrayList<Map<String ,String>> data=new ArrayList<>();
    RecyclerView prv;
    pendingAdapter adapter;
    FirebaseAuth auth;
    FirebaseDatabase database;

    public PendingFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_pending, container, false);
        prv=v.findViewById(R.id.pending_rv);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        database.getReference().child(auth.getUid()).child(yettoaprroved).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data= (ArrayList<Map<String, String>>) snapshot.getValue();
                SystemClock.sleep(1000);
                adapter=new pendingAdapter(data);
                LinearLayoutManager manger=new LinearLayoutManager(v.getContext());
                prv.setAdapter(adapter);
                prv.setLayoutManager(manger);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(v.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return v;
    }
}