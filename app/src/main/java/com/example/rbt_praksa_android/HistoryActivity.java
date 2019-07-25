package com.example.rbt_praksa_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rbt_praksa_android.model.AirVironment;
import com.example.rbt_praksa_android.model.MyAdapter;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Chart chart = new Chart(getApplicationContext(), );
        //chart.invalidate();

        Chart.setParameters(MainActivity.temperatureList);
        setContentView(R.layout.activity_history);
        LinearLayout small_layout = findViewById(R.id.small_layout);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        ArrayList<AirVironment> history = intent.getParcelableArrayListExtra("historyList");

        mAdapter = new MyAdapter(history);
        recyclerView.setAdapter(mAdapter);
    }
}
