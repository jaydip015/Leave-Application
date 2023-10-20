package com.example.leaveapplication.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.leaveapplication.R;

public class progressbarAdapter extends Dialog {
    public progressbarAdapter(@NonNull Context context,String s) {
        super(context);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(params);
        setCancelable(false);
        setOnCancelListener(null);

        View v= LayoutInflater.from(context).inflate(R.layout.loading_screen,null);
        TextView tv=v.findViewById(R.id.loading_text);
        tv.setText(s);
        setContentView(v);
    }


}
