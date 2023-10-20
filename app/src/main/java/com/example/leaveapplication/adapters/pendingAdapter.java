package com.example.leaveapplication.adapters;

import static com.example.leaveapplication.LoginActivity.EDATE;
import static com.example.leaveapplication.LoginActivity.LEAVEREASON;
import static com.example.leaveapplication.LoginActivity.SDATE;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leaveapplication.R;

import java.util.ArrayList;
import java.util.Map;

public class pendingAdapter extends RecyclerView.Adapter<pendingAdapter.ViewHolder> {
    ArrayList<Map<String ,String>> data=new ArrayList<>();

    public pendingAdapter(ArrayList<Map<String, String>> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.preason.setText(data.get(position).get(LEAVEREASON));
        holder.pedate.setText(data.get(position).get(EDATE));
        holder.psdate.setText(data.get(position).get(SDATE));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView psdate,pedate,preason;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pedate=itemView.findViewById(R.id.tv_card_f_enddate);
            psdate=itemView.findViewById(R.id.tv_card_f_startdate);
            preason=itemView.findViewById(R.id.tv_card_f_reason);

        }
    }
}
