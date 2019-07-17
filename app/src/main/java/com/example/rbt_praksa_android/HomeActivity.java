package com.example.rbt_praksa_android;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private int count = 0;

    private ImageView imageView;
    private TextView textView;
    private Switch homeSwitch;
    private Button topButton;
    private EditText editText;
    private Button leftButton;
    private Button rightButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        homeSwitch = findViewById(R.id.homeSwitch);
        topButton = findViewById(R.id.topButton);
        editText = findViewById(R.id.editText);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);

        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count++%2==1) imageView.setImageResource(R.drawable.elderly_man_working_laptop_smiling_20855483);
                else imageView.setImageResource(R.drawable.hide_the_pain_stockphoto_840x560);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                textView.setText(s);
            }
        });


        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSwitch(false);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSwitch(true);
            }
        });
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

    private void changeSwitch(Boolean change) {
        homeSwitch.setChecked(change);
    }
}
