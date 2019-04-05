package com.example.usernav;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchDonutActivity(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);

    }
    public void launchIceCreamActivity(View view) {
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);

    }
    public void launchFroyoActivity(View view) {
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);

    }
}
