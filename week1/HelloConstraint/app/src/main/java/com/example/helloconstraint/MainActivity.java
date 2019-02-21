package com.example.helloconstraint;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int mCount=0;
    private TextView mShowCount;
    private Button zButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
        zButton = (Button) findViewById(R.id.button_zero);
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this,R.string.toast_message,Toast.LENGTH_SHORT);
        toast.show();
    }

    public void setZero(View view){



        mCount = 0;
        mShowCount.setText(Integer.toString(mCount));
        zButton.setBackgroundColor(Color.GRAY);
    }

    public void countUp(View view) {
        Button b = (Button) view;


        ++mCount;
        if (mShowCount != null){

            zButton.setBackgroundColor(Color.GREEN);
            mShowCount.setText(Integer.toString(mCount));
            if(mCount%2 == 0 ){
                b.setBackgroundColor(Color.parseColor("Red"));
            }
            else {

                b.setBackgroundColor(Color.parseColor("Blue"));
            }
        }





    }
}
