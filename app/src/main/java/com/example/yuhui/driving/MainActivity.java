package com.example.yuhui.driving;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements View.OnTouchListener, Runnable {

    private static final String TAG = "driving";
    private LinearLayout main_panel;
    private DashBoardView dashBoardView;
    private Button accelerate;
    private Button handbrake;
    private Button brake;
    private Thread thread;
    private Handler handler;
    int speed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_panel = (LinearLayout) findViewById(R.id.main_panel);
        setButtons();
        dashBoardView = new DashBoardView(this);
        main_panel.addView(dashBoardView,
                new LinearLayout
                        .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dashBoardView.setSpeed(speed);
                dashBoardView.invalidate();
            }
        };
    }

    private void setButtons() {
        accelerate = (Button) findViewById(R.id.accelerate);
        handbrake = (Button) findViewById(R.id.handbrake);
        brake = (Button) findViewById(R.id.brake);
        accelerate.setOnTouchListener(this);
        handbrake.setOnTouchListener(this);
        brake.setOnTouchListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.accelerate:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.i(TAG, "MotionEvent.ACTION_DOWN");
//                    thread = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            while (true) {
//                                speed += 5;
//                                handler.sendEmptyMessage(1);
//                            }
//                        }
//                    });
//                    thread.start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                    thread.interrupt();
//                    thread = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            while (true) {
//                                speed -= 1;
//                                handler.sendEmptyMessage(0);
//                            }
//                        }
//                    });
//                    thread.start();
                }
                break;
            case R.id.handbrake:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    speed = 0;
                }
                break;
            case R.id.brake:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    speed -= 5;

                }
                break;
        }

        return false;
    }

    @Override
    public void run() {

    }
}
