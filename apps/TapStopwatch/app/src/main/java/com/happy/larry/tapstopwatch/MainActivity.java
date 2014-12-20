package com.happy.larry.tapstopwatch;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private TextView tvStopwatch;
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    private long timeInMillies = 0L;
    private long timeSwap = 0L;
    private long finalTime = 0L;
    private boolean isStarted = false;
    private final String TAG = "TS";
    private GestureTap GestureTap;
    private GestureDetector gestureDetector;

    private Runnable updateTimerMethod = new Runnable() {
        public void run() {
            timeInMillies = SystemClock.uptimeMillis()-startTime;
            finalTime = timeSwap + timeInMillies;

            int seconds = (int) (finalTime/1000);
            int minutes = seconds/60;
            seconds = seconds%60;
            int milliseconds = (int) ((finalTime%1000)/10);
            tvStopwatch.setText(String.format("%02d", minutes)
                    + ":" + String.format("%02d", seconds)
                    + ":" + String.format("%02d", milliseconds));
            myHandler.postDelayed(this, 0);
        }
    };

    private void reset() {
        myHandler.removeCallbacks(updateTimerMethod);
        tvStopwatch.setText(R.string.reset_time);
        isStarted = false;
        startTime = 0L;
        timeSwap = 0L;
    }

    public class GestureTap extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //Log.d(logTag, "---- double tap --------");
            reset();
            return true;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View contentView = findViewById(R.id.stopwatch);
        tvStopwatch = (TextView) findViewById(R.id.stopwatch);

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isStarted == false) {
                    startTime = SystemClock.uptimeMillis();
                    myHandler.postDelayed(updateTimerMethod, 0);
                    isStarted = true;
                }
                else {
                    timeSwap += timeInMillies;
                    myHandler.removeCallbacks(updateTimerMethod);
                    isStarted = false;
                }
            }
        });

        GestureTap = new GestureTap();
        gestureDetector = new GestureDetector(this, GestureTap);
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }
}
