package com.example.myproject;

import android.content.Intent;
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

public class InfoBusSelectActivity extends AppCompatActivity {

    private DatabaseReference driverref;

    private RecyclerView recyclerViewBusList;

    FirebaseRecyclerOptions<BusSelect> options;
    FirebaseRecyclerAdapter<BusSelect,MyViewHolder2> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bus_select);

        driverref= FirebaseDatabase.getInstance().getReference().child("drivers");

        recyclerViewBusList=findViewById(R.id.recycleViewBusSelectInfo);
        recyclerViewBusList.setLayoutManager(new LinearLayoutManager(this));

        options=new FirebaseRecyclerOptions.Builder<BusSelect>().setQuery(driverref,BusSelect.class).build();
        adapter=new FirebaseRecyclerAdapter<BusSelect, MyViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder2 holder, int position, @NonNull BusSelect model) {
                holder.selectBusName.setText(model.getBus_name());


                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(InfoBusSelectActivity.this,BusInfoActivity.class);
                        intent.putExtra("driverKey",getRef(holder.getBindingAdapterPosition()).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_bus_name,parent,false);
                return new MyViewHolder2(v);
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