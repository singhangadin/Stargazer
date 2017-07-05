/*
 * Copyright (C) 2017 Angad Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.angads25.stargazerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.angads25.stargazer.model.OnItemRatedListener;
import com.github.angads25.stargazer.widget.StargazerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StargazerView view = (StargazerView) findViewById(R.id.stargazer);
        view.setItemRatedListener(new OnItemRatedListener() {
            @Override
            public void onItemRated(View v, final int rate) {
                Toast.makeText(getBaseContext(), "Rated " + rate + " star", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
