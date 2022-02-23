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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapBusSelectActivity extends AppCompatActivity {

    private DatabaseReference driverref;
    private FirebaseAuth auth;

    private RecyclerView recyclerViewBusList;

    FirebaseRecyclerOptions<BusSelect> options;
    FirebaseRecyclerAdapter<BusSelect,MyViewHolder> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_bus_select);

        driverref= FirebaseDatabase.getInstance().getReference().child("drivers");

        recyclerViewBusList=findViewById(R.id.recycleViewBusSelect);
        recyclerViewBusList.setLayoutManager(new LinearLayoutManager(this));



      //LoadData();

        options=new FirebaseRecyclerOptions.Builder<BusSelect>().setQuery(driverref,BusSelect.class).build();
        adapter=new FirebaseRecyclerAdapter<BusSelect, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull BusSelect model) {
                holder.allBusName.setText(model.getBus_name());
                holder.allBusNo.setText(model.getBus_no());
                holder.allBusDetails.setText(model.getBus_details());

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MapBusSelectActivity.this,BusOnMapActivity.class);
                        intent.putExtra("driverKey",getRef(holder.getBindingAdapterPosition()).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_bus_layout,parent,false);
                return new MyViewHolder(v);
            }
        };

        recyclerViewBusList.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();






    }

   // private void LoadData() {

    //}

    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }




}