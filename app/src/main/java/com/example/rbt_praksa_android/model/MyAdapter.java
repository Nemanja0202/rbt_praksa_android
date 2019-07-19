package com.example.rbt_praksa_android.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rbt_praksa_android.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<AirVironment> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_timestamp;
        public TextView textView_temp_value;
        public TextView textView_humidity_value;
        public TextView textView_quality_value;

        public MyViewHolder(View v) {
            super(v);
            textView_timestamp = v.findViewById(R.id.history_timestamp);
            textView_temp_value = v.findViewById(R.id.history_temp_value);
            textView_humidity_value = v.findViewById(R.id.history_humidity_value);
            textView_quality_value = v.findViewById((R.id.history_quality_value));
        }
    }

    public MyAdapter(ArrayList<AirVironment> myDataSet) { mDataset = myDataSet;}

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String time = mDataset.get(position).getTimestamp();
        holder.textView_timestamp.setText(time.substring(8,10) + "." + time.substring(5,7) + "." + time.substring(0,4) + "/" + time.substring(11,16));

        Double temp = mDataset.get(position).getTemperature();
        holder.textView_temp_value.setText(temp.toString());

        Double humid = mDataset.get(position).getHumidity();
        holder.textView_humidity_value.setText(humid.toString());

        Double quality = mDataset.get(position).getPollution();
        holder.textView_quality_value.setText(quality.toString());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
