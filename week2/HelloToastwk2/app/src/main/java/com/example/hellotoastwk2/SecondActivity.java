package com.example.hellotoastwk2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String count = intent.getStringExtra(MainActivity.EXTRA_COUNT);
        TextView textView = findViewById(R.id.show_count_second);
        textView.setText(count);
    }
}
