package com.example.rbt_praksa_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

//    private Button secondActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        secondActivityButton = findViewById(R.id.second_activity_button);

//        secondActivityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent nextActivity = new Intent();
//                startActivity(nextActivity);
//            }
//        });
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
        Log.d("lifecycle", "onSop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "onDestroy()");
    }

    protected void welcome(View view) {
        Intent welcome = new Intent(this, SecondActivity.class);
        startActivity(welcome);
    }
}
