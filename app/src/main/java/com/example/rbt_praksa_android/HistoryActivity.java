package com.example.rbt_praksa_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rbt_praksa_android.model.AirVironment;
import com.example.rbt_praksa_android.model.MyAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Pair<Long, Float>> chartList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        //chartList = (ArrayList< Pair<Long, Float>>) intent.getSerializableExtra("chartList");

        setParameters(MainActivity.chartList);

        setContentView(R.layout.activity_history);
        LinearLayout small_layout = findViewById(R.id.small_layout);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<AirVironment> history = intent.getParcelableArrayListExtra("historyList");

        mAdapter = new MyAdapter(history);
        recyclerView.setAdapter(mAdapter);
    }

    private void setParameters(ArrayList<Pair<Long, Float>> chartList){
//        Date startDate = new Date(listParams.get(0).getTimestamp());
//        Date endDate = new Date(listParams.get(listParams.size()-1).getTimestamp());
//        Date line1X = new Date(listParams.get(0).getTimestamp()+timeCoef);
//        Date line2X = new Date(timeCoef/2);
//        Date line3X = new Date(listParams.get(0).getTimestamp()+3*timeCoef);
//
//        //SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
//        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Chart.setParameters(chartList);
    }
}
