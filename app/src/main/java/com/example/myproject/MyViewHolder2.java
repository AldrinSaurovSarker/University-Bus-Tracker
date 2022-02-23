package com.example.myproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder2 extends RecyclerView.ViewHolder {

    TextView selectBusName;
    View v;


    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);
        selectBusName=itemView.findViewById(R.id.selectBusName);
        v=itemView;
    }
}
