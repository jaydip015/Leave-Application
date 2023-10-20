package com.example.leaveapplication.adapters;

import static com.example.leaveapplication.LoginActivity.EDATE;
import static com.example.leaveapplication.LoginActivity.FNAME;
import static com.example.leaveapplication.LoginActivity.LEAVEREASON;
import static com.example.leaveapplication.LoginActivity.SDATE;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leaveapplication.NotAprovecLICK;
import com.example.leaveapplication.R;
import com.example.leaveapplication.RVItemclick;

import java.util.ArrayList;
import java.util.Map;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    ArrayList<Map<String ,String>> data;
    private final RVItemclick itemclick;
    private final NotAprovecLICK notitemclick;
    public RVAdapter(ArrayList<Map<String, String>> data,RVItemclick itemclick,NotAprovecLICK notitemclick) {
        this.data = data;
        this.itemclick=itemclick;
        this.notitemclick=notitemclick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_request,parent,false);
        return new ViewHolder(v,itemclick,notitemclick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fname.setText(data.get(position).get(FNAME));
        holder.leaveReason.setText(data.get(position).get(LEAVEREASON));
        holder.edate.setText(data.get(position).get(EDATE));
        holder.sdate.setText(data.get(position).get(SDATE));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView fname,leaveReason,sdate,edate;
        Button approve,notapprove;
        public ViewHolder(@NonNull View itemView,RVItemclick itemclick,NotAprovecLICK notAprovecLICK) {
            super(itemView);
            sdate=itemView.findViewById(R.id.card_start_date);
            edate=itemView.findViewById(R.id.card_end_date);
            fname=itemView.findViewById(R.id.tv_faculty_name);
            leaveReason=itemView.findViewById(R.id.leave_applicatin);
            approve=itemView.findViewById(R.id.approved);
            notapprove=itemView.findViewById(R.id.notapprove);
            notapprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemclick!= null){
                        int position=getAdapterPosition();

                        notitemclick.onNotItemClick(position);

                    }
                }
            });
            approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemclick!= null){
                        int position=getAdapterPosition();

                            itemclick.onItemClick(position);

                    }
                }
            });
        }
    }
}
