package com.example.statehomework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private int count=0;
    private TextView tView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tView = findViewById(R.id.show_count);

        if(savedInstanceState!=null){
            count = savedInstanceState.getInt("count");
            tView.setText(Integer.toString(count));
        }
    }

    public void updateCount(View view) {
        count++;
        tView.setText(Integer.toString(count));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count",  count);

    }
}
