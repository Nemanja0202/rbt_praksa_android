package com.example.rbt_praksa_android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rbt_praksa_android.model.AirVironment;
import com.example.rbt_praksa_android.model.MyAdapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private ArrayList<Pair<Long, Float>> chartList = new ArrayList<>();
    private float minX, maxX, minY, maxY;
    private ArrayList<String> labelX = new ArrayList<>();
    private ArrayList<String> labelY = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();

        setContentView(R.layout.activity_history);
        LinearLayout small_layout = findViewById(R.id.small_layout);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<AirVironment> history = intent.getParcelableArrayListExtra("historyList");
        copyList(chartList, history);
        setParameters(chartList);

        mAdapter = new MyAdapter(history);
        recyclerView.setAdapter(mAdapter);
    }

    private void copyList(ArrayList<Pair<Long, Float>> chartList, ArrayList<AirVironment> history){
        for(int i = 0; i<history.size();i++){
            String timeStamp = history.get(i).getTimestamp();
            timeStamp = timeStamp.replace('T',' ');
            timeStamp = timeStamp.substring(0,19);
            System.out.println(timeStamp);
            java.sql.Timestamp ts = java.sql.Timestamp.valueOf(timeStamp);
            long time = ts.getTime();

            chartList.add(new Pair<Long, Float>(time, history.get(i).getTemperature().floatValue()));
        }
    }

    private void setParameters(ArrayList<Pair<Long, Float>> chartList){

        minY = chartList.get(0).second;
        maxY = chartList.get(0).second;
        maxX = chartList.get(0).first;
        minX = chartList.get(0).first;

        for (int i=0; i<chartList.size(); i++) {
            if (chartList.get(i).first > maxX)
                maxX = chartList.get(i).first;
            if (chartList.get(i).first < minX)
                minX = chartList.get(i).first;
            if (chartList.get(i).second > maxY)
                maxY = chartList.get(i).second;
            if (chartList.get(i).second < minY)
                minY = chartList.get(i).second;
        }

        DecimalFormat numberFormat = new DecimalFormat("#.00");
        maxY = maxY*1.1f;
        minY = minY*0.9f;
        maxY = Float.parseFloat(numberFormat.format(maxY));
        minY = Float.parseFloat(numberFormat.format(minY));

        float textCoefY = (maxY-minY)/4;
        float line1Y = Float.parseFloat(numberFormat.format(minY+textCoefY));
        float line2Y = Float.parseFloat(numberFormat.format(minY+(textCoefY*2)));
        float line3Y = Float.parseFloat(numberFormat.format(maxY-textCoefY));

        labelY.add(Float.toString(minY));
        labelY.add(Float.toString(line1Y));
        labelY.add(Float.toString(line2Y));
        labelY.add(Float.toString(line3Y));
        labelY.add(Float.toString(maxY));

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date startDate = new Date(chartList.get(0).first);
        labelX.add(df.format(startDate));

        Date endDate = new Date(chartList.get(chartList.size()-1).first);
        long timeCoef = chartList.get(chartList.size()-1).first - chartList.get(0).first;

        Date line1X = new Date(chartList.get(0).first+timeCoef);
        Date line2X = new Date(chartList.get(0).first + timeCoef*2);
        Date line3X = new Date(chartList.get(0).first + 3*timeCoef);

        labelX.add(df.format(line1X));
        labelX.add(df.format(line2X));
        labelX.add(df.format(line3X));
        labelX.add(df.format(endDate));

        Chart.setParameters(chartList, labelX, labelY, Color.RED, Color.BLUE);
    }
}
