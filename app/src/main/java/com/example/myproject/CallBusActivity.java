package com.example.myproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CallBusActivity extends AppCompatActivity {

    private DatabaseReference driverref;

    private RecyclerView recyclerViewBusList;

    FirebaseRecyclerOptions<BusSelect> options;
    FirebaseRecyclerAdapter<BusSelect,MyViewHolder3> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_bus);

        driverref= FirebaseDatabase.getInstance().getReference().child("drivers");

        recyclerViewBusList=findViewById(R.id.recycleViewCallBus);
        recyclerViewBusList.setLayoutManager(new LinearLayoutManager(this));

        options=new FirebaseRecyclerOptions.Builder<BusSelect>().setQuery(driverref,BusSelect.class).build();
        adapter=new FirebaseRecyclerAdapter<BusSelect, MyViewHolder3>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder3 holder, int position, @NonNull BusSelect model) {
                holder.callDriverName.setText(model.getDriver_name());
                holder.callDriverContact.setText(model.getContact_no());
            }

            @NonNull
            @Override
            public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_driver_name,parent,false);
                return new MyViewHolder3(v);
            }
        };

        recyclerViewBusList.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}