package com.github.angads25.stargazerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.angads25.stargazer.StargazerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StargazerView view = (StargazerView)findViewById(R.id.stargazer);
    }
}
