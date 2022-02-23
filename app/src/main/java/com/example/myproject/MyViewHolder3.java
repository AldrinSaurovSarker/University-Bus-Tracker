package com.example.myproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MyViewHolder3 extends RecyclerView.ViewHolder {

    TextView callDriverName,callDriverContact;

    public MyViewHolder3(@NonNull View itemView) {
        super(itemView);
        callDriverName=itemView.findViewById(R.id.callDriverName);
        callDriverContact=itemView.findViewById(R.id.callDriverNO);
    }
}
