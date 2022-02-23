package com.example.myproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView allBusName,allBusNo,allBusDetails;
    View v;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        allBusName=itemView.findViewById(R.id.allBusName);
        allBusNo=itemView.findViewById(R.id.allBusNo);
        allBusDetails=itemView.findViewById(R.id.allBusDetails);
        v=itemView;
    }
}
