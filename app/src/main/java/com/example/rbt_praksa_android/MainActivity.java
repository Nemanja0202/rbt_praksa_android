package com.example.rbt_praksa_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rbt_praksa_android.model.AirVironment;
import com.example.rbt_praksa_android.network.MeasurementApi;
import com.example.rbt_praksa_android.network.RetrofitClientInstance;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView temp;
    TextView humid;
    TextView pollution;
    TextView timestamp;
    Button history;
    ArrayList<AirVironment> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = findViewById(R.id.temperatureValue);
        humid = findViewById(R.id.humidityValue);
        pollution = findViewById(R.id.iaqIndexValue);
        timestamp = findViewById(R.id.timestamp);
        history = findViewById(R.id.show_history);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HistoryActivity.class);
                intent.putExtra("historyList", historyList);
                startActivity(intent);
            }
        });

        final Handler handler = new Handler();
        final int delay = 3000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MeasurementApi service = RetrofitClientInstance.getRetrofitInstance().create(MeasurementApi.class);

                Call<AirVironment> call = service.getMeasurementData();

                Log.wtf("URL Called", call.request().url() + "");

                call.enqueue(new Callback<AirVironment>() {
                    @Override
                    public void onResponse(Call<AirVironment> call, Response<AirVironment> response) {
                        AirVironment airdata = response.body();

                        historyList.add(airdata);

                        Double temp1 = airdata.getTemperature();
                        temp.setText(temp1.toString() + "°C");

                        Double humid1 = airdata.getHumidity();
                        humid.setText(humid1.toString() + "%");

                        Double quality1 = airdata.getPollution();
                        pollution.setText(quality1.toString());

                        String timestamp1 = airdata.getTimestamp();
                        timestamp.setText("Last update: " + timestamp1.substring(8, 10) + " " +
                                timestamp1.substring(5, 7) + "." + timestamp1.substring(0, 4) + "/" + timestamp1.substring(11, 16));
                    }

                    @Override
                    public void onFailure(Call<AirVironment> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Something went wrong" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                handler.postDelayed(this, delay);
            }
        }, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "onDestroy()");
    }
}
