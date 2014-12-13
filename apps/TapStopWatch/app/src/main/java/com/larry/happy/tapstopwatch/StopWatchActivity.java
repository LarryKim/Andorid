package com.larry.happy.tapstopwatch;

import com.larry.happy.tapstopwatch.util.SystemUiHider;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class StopWatchActivity extends Activity {
    private TextView textTimer;
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;
    private boolean isStarted = false;
    private String logTag = "_STOP_WATCH_";
    GestureDoubleTap gestureDoubleTap;
    GestureDetector gestureDetector;


    public class GestureDoubleTap extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //Log.d(logTag, "---- double tap --------");
            myHandler.removeCallbacks(updateTimerMethod);
            textTimer.setText(R.string.reset_time);
            isStarted = false;
            startTime = 0L;
            timeSwap = 0L;
            return true;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stop_watch);
        final View contentView = findViewById(R.id.fullscreen_content);
        textTimer = (TextView) findViewById(R.id.fullscreen_content);

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(logTag, "clicked!!!!------------------------------");
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


        gestureDoubleTap = new GestureDoubleTap();
        gestureDetector = new GestureDetector(this, gestureDoubleTap);

        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }

        });
    }

    private Runnable updateTimerMethod = new Runnable() {
        public void run() {
            timeInMillies = SystemClock.uptimeMillis()-startTime;
            finalTime = timeSwap + timeInMillies;

            int seconds = (int) (finalTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (finalTime % 1000);
            textTimer.setText("" + String.format("%02d", minutes)+":"
            +String.format("%02d", seconds)+":"
            +String.format("%03d", milliseconds));
            myHandler.postDelayed(this, 0);
        }
    };
}
