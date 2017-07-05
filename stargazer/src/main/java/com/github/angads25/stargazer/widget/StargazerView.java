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

package com.github.angads25.stargazer.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.github.angads25.stargazer.R;
import com.github.angads25.stargazer.model.OnItemRatedListener;
import com.github.angads25.stargazer.model.StarLeaf;

/**
 * <p>
 * Created by Angad on 28-06-2017.
 * </p>
 */

public class StargazerView extends View {
    private Paint paint;
    private int bounds;
    private int radius;
    private int centerX;
    private int centerY;

    private int minRadii;
    private int leafRadii;
    private int baseRadii;

    private long downTime;
    private float downX, downY;

    private Point H, B, D, F, E;
    private StarLeaf[] starLeaves;
    private StarLeaf[] progressLeaves;

    private int textColor, textColorSelected, bgColor, fgColor;

    private OnItemRatedListener itemRatedListener;

    public StargazerView(Context context) {
        super(context);
        initView();
    }

    public StargazerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public StargazerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StargazerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    @SuppressWarnings("deprecation")
    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        H = new Point();
        B = new Point();
        D = new Point();
        F = new Point();
        E = new Point();

        starLeaves = new StarLeaf[5];
        progressLeaves = new StarLeaf[5];
        for (int i = 0; i < 5; i++) {
            starLeaves[i] = new StarLeaf();
            progressLeaves[i] = new StarLeaf();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textColor = getResources().getColor(R.color.text_clr, getContext().getTheme());
            textColorSelected = getResources().getColor(R.color.text_clr_sel, getContext().getTheme());
            bgColor = getResources().getColor(R.color.star_bkg, getContext().getTheme());
            fgColor = getResources().getColor(R.color.star_frg, getContext().getTheme());
        } else {
            textColor = getResources().getColor(R.color.text_clr);
            textColorSelected = getResources().getColor(R.color.text_clr_sel);
            bgColor = getResources().getColor(R.color.star_bkg);
            fgColor = getResources().getColor(R.color.star_frg);
        }
        setClickable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int angle = 72;
        int offset = -18;

        for(int i = 0; i < 5;i++) {
            double ang = Math.toRadians(offset);
            double ang1 = Math.toRadians(offset + 32);
            double ang2 = Math.toRadians(offset - 32);

            // Bottom Most Point
            int pt1x = (int) (centerX + ((radius >>> 4) * Math.cos(ang)));
            int pt1y = (int) (centerY + ((radius >>> 4) * Math.sin(ang)));
            H.set(pt1x, pt1y);

            // Left Center Point
            int pt4x = (int) (centerX + ((radius >>> 1) * Math.cos(ang2)));
            int pt4y = (int) (centerY + ((radius >>> 1) * Math.sin(ang2)));
            B.set(pt4x, pt4y);

            // Top Most Point
            int pt2x = (int) (centerX + (radius * Math.cos(ang)));
            int pt2y = (int) (centerY + (radius * Math.sin(ang)));
            D.set(pt2x, pt2y);

            // Right Center Point
            int pt3x = (int) (centerX + ((radius >>> 1) * Math.cos(ang1)));
            int pt3y = (int) (centerY + ((radius >>> 1) * Math.sin(ang1)));
            F.set(pt3x, pt3y);

            starLeaves[i].setPoints(H, B, D, F);

            paint.setColor(bgColor);
            canvas.drawPath(starLeaves[i].getPath(), paint);

            // Top Most Progress Point
            float factor = progressLeaves[i].getFactor();
            float power = (float) Math.pow(2, i);
            if(factor > power) {
                factor = power;
            }

            float radii = ((radius * factor)/power);
            pt2x = (int) (centerX + ((radii < minRadii ? minRadii : radii) * Math.cos(ang)));
            pt2y = (int) (centerY + ((radii < minRadii ? minRadii : radii) * Math.sin(ang)));
            E.set(pt2x, pt2y);

            progressLeaves[i].setProgressPoints(H, B, D, F, E);

            paint.setColor(fgColor);
            canvas.drawPath(progressLeaves[i].getPath(), paint);

            int pttX = (int) (centerX + ((radius / 3) * Math.cos(ang)));
            int pttY = (int) (centerY + ((radius / 3) * Math.sin(ang)));

            if(radii > radius / 3) {
                paint.setColor(textColorSelected);
            } else {
                paint.setColor(textColor);
            }
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setTextSize(baseRadii / 7);
            paint.setStrokeWidth(baseRadii >>> 7);
            float textSize = paint.measureText(""+i);
            canvas.drawText((i + 1)+"", pttX - (textSize / 2), pttY + (textSize / 2), paint);

            offset = (offset % 360) + angle;
            paint.reset();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        bounds = Math.min(width, height);
        setMeasuredDimension(bounds, bounds);
        bounds = bounds >>> 1;
        radius = bounds - ((bounds >>> 1) / 5);
        centerX = (getRight() - getLeft()) >>> 1;
        centerY = (getBottom() - getTop()) >>> 1;
        minRadii = radius >>> 4;
        leafRadii = minRadii;
        baseRadii = radius;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:   downTime = System.currentTimeMillis();
                                            downX = x; downY = y;
                                            break;

            case MotionEvent.ACTION_UP: long diff = System.currentTimeMillis() - downTime;
                                        if(diff < 500) {
                                            for (int i = 0; i < 5; i++) {
                                                if(starLeaves[i].contains(x, y)) {
                                                    if(itemRatedListener!=null) {
                                                        itemRatedListener.onItemRated(StargazerView.this, i + 1);
                                                        animateLeaves(i + 1);
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                        break;
        }
        return super.onTouchEvent(event);
    }

    public void animateLeaves(final int point) {
        Thread T1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int j = point - 1; j < 5; j++) {
                    progressLeaves[j].setFactor(0);
                }
                for(float i = 0 ; i < Math.pow(2, point); i += 0.2) {
                    for(int j = 0; j < point; j++) {
                        progressLeaves[j].setFactor(i);
                    }
                    try {
                        if(i < Math.pow(2, 1)) {
                            Thread.sleep(20);
                        } else if(i < Math.pow(2, 1)) {
                            Thread.sleep(10);
                        } else if(i < Math.pow(2, 2)) {
                            Thread.sleep(5);
                        } else if(i < Math.pow(2, 3)) {
                            Thread.sleep(2, 500000);
                        } else if(i < Math.pow(2, 4)) {
                            Thread.sleep(1, 250000);
                        } else {
                            Thread.sleep(0, 750000);
                        }
                        ((Activity)getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                invalidate();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        T1.start();
//        for(int i = minRadii ; i < baseRadii; i++) {
//            leafRadii = i;
//            try {
//                Thread.sleep(1);
//                ((Activity)getContext()).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        invalidate();
//                    }
//                });
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        invalidate();
    }

    public void setItemRatedListener(OnItemRatedListener itemRatedListener) {
        this.itemRatedListener = itemRatedListener;
    }
}
