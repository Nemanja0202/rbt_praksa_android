package com.example.rbt_praksa_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rbt_praksa_android.model.AirVironment;
import com.example.rbt_praksa_android.network.BroadcastHandler;
import com.example.rbt_praksa_android.network.MeasurementApi;
import com.example.rbt_praksa_android.network.RetrofitClientInstance;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView temp;
    TextView humid;
    TextView pollution;
    TextView timestamp;
    Button history;
    Button share;
    ArrayList<AirVironment> historyList = new ArrayList<>();
    public static ArrayList<Pair<Long, Float>> chartList = new ArrayList<Pair<Long, Float>>();

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    private static final String prefKey = "myPref";

    ConnectivityManager cm;

    BroadcastHandler broadcastHandler;
    static int MY_PERMISSIONS_SMS_RECEIVE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_SMS_RECEIVE);
        } else {
            // Permission has already been granted
        }

        broadcastHandler = new BroadcastHandler();

        IntentFilter filter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        this.registerReceiver(broadcastHandler, filter);

        sharedPref = MainActivity.this.getSharedPreferences(prefKey, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        cm = (ConnectivityManager)MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        temp = findViewById(R.id.temperatureValue);
        humid = findViewById(R.id.humidityValue);
        pollution = findViewById(R.id.iaqIndexValue);
        timestamp = findViewById(R.id.timestamp);
        history = findViewById(R.id.show_history);
        share = findViewById(R.id.share_button);


        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HistoryActivity.class);
                intent.putExtra("historyList", historyList);
                startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    AirVironment tmpMeasurement = historyList.get(historyList.size() - 1);
                    String shareString = "";
                    String tmpTimeStamp = tmpMeasurement.getTimestamp();
                    String timeHours = tmpTimeStamp.substring(11, 19);
                    String timeDate = tmpTimeStamp.substring(8, 10) + "." + tmpTimeStamp.substring(5, 7) + "." +
                            tmpTimeStamp.substring(0, 4);

                    String temp_share = tmpMeasurement.getTemperature().toString();
                    String humid_share = tmpMeasurement.getHumidity().toString();
                    String quality_share = tmpMeasurement.getPollution().toString();

                    shareString += "Here's the data on air quality recorded on ";
                    shareString += timeDate + " at " + timeHours + ": \n\n";
                    shareString += "Temperature: " + temp_share + "°C" + "\n";
                    shareString += "Humidity: " + humid_share + "%" + "\n";
                    shareString += "Pollution: " + quality_share + "\n\n";
                    shareString += "Powered by AIRvironment - RBT";

                    i.putExtra(Intent.EXTRA_TEXT, shareString);
                    startActivity(i);
                } else {
                    String saved_data = sharedPref.getString(prefKey, "");

                    if (saved_data != "") {
                        String pattern = "(.*);(.*);(.*);(.*)";

                        Pattern r = Pattern.compile(pattern);

                        Matcher m = r.matcher(saved_data);
                        if (m.find()) {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("text/plain");
                            String shareString = "";
                            String tmpTimeStamp = m.group(1);
                            String timeHours = tmpTimeStamp.substring(11, 19);
                            String timeDate = tmpTimeStamp.substring(8, 10) + "." + tmpTimeStamp.substring(5, 7) + "." + tmpTimeStamp.substring(0, 4);

                            shareString += "Here's the data on air quality recorded on ";
                            shareString += timeDate + " at " + timeHours + ": \n\n";
                            shareString += "Temperature: " + m.group(2) + "°C" + "\n";
                            shareString += "Humidity: " + m.group(3) + "%" + "\n";
                            shareString += "Pollution: " + m.group(4) + "\n\n";
                            shareString += "Powered by AIRvironment - RBT";

                            i.putExtra(Intent.EXTRA_TEXT, shareString);
                            startActivity(i);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No data do display - Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
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

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected) {
                    call.enqueue(new Callback<AirVironment>() {
                        @Override
                        public void onResponse(Call<AirVironment> call, Response<AirVironment> response) {
                            AirVironment airdata = response.body();

                            String timeGraph = airdata.getTimestamp();
                            timeGraph = timeGraph.replace('T',' ');
                            timeGraph = timeGraph.substring(0,19);
                            java.sql.Timestamp ts = java.sql.Timestamp.valueOf(timeGraph);
                            long time = ts.getTime();

                            historyList.add(airdata);
                            chartList.add(new Pair<Long, Float>(time, airdata.getTemperature().floatValue()));

                            Double temp1 = airdata.getTemperature();
                            temp.setText(temp1 + "°C");

                            Double humid1 = airdata.getHumidity();
                            humid.setText(humid1 + "%");

                            Double quality1 = airdata.getPollution();
                            pollution.setText(quality1.toString());

                            String timestamp1 = airdata.getTimestamp();
                            timestamp.setText("Last update: " + timestamp1.substring(8, 10) + " " +
                                    timestamp1.substring(5, 7) + "." + timestamp1.substring(0, 4) + "/" + timestamp1.substring(11, 16));

                            String prefString = "" + airdata.getTimestamp() + ";" + airdata.getTemperature() + ";" + airdata.getHumidity() + ";" + airdata.getPollution();
                            editor.putString(prefKey, prefString);
                            editor.commit();
                        }

                        @Override
                        public void onFailure(Call<AirVironment> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Something went wrong" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    String saved_data = sharedPref.getString(prefKey, "");

                    if (saved_data != "") {
                        String pattern = "(.*);(.*);(.*);(.*)";

                        Pattern p = Pattern.compile(pattern);

                        Matcher m = p.matcher(saved_data);

                        if (m.find()) {
                            timestamp.setText("No Internet connection - displaying last stored data");
                            String saved_temp = m.group(2);
                            String saved_humid = m.group(3);
                            String saved_pollution = m.group(4);
                            temp.setText(saved_temp + "°C");
                            humid.setText(saved_humid + "%");
                            pollution.setText(saved_pollution);
                        }
                    } else {
                        timestamp.setText("No Internet connection - No data saved");
                        temp.setText("--");
                        humid.setText("--");
                        pollution.setText("--");
                    }
                }
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
        unregisterReceiver(broadcastHandler);
    }
}
