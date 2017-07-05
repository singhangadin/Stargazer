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
import android.widget.Button;

import com.github.angads25.stargazer.widget.StargazerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private StargazerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (StargazerView)findViewById(R.id.stargazer);
//        Thread T1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    view.animateLeaves(5);
//                }
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    view.animateLeaves(2);
//                }
//            }
//        });
//        T1.start();
        findViewById(R.id.one).setOnClickListener(this);
        findViewById(R.id.two).setOnClickListener(this);
        findViewById(R.id.three).setOnClickListener(this);
        findViewById(R.id.four).setOnClickListener(this);
        findViewById(R.id.five).setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        Thread T1 = new Thread(new Runnable() {
            @Override
            public void run() {
                view.animateLeaves(Integer.parseInt(((Button)v).getText().toString()));
            }
        });
        T1.start();
    }
}
