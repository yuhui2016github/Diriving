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

public class MainActivity extends Activity implements View.OnTouchListener {

    private static final String TAG = "driving";
    private LinearLayout main_panel;
    private DashBoardView dashBoardView;
    private Button accelerate;
    private Button handbrake;
    private Button brake;
    private Thread thread;
    private Handler handler;
    private int mType = 0;
    private boolean isBraking = false;
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
                        .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dashBoardView.setSpeed(speed);
                dashBoardView.invalidate();
            }
        };
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        switch (mType) {
                            case 1: //加速
                                Thread.sleep(300);
                                Log.i(TAG, "case 1 speed: " + speed);
                                if (speed < 180) {
                                    speed += 5;
                                }
                                break;
                            case 2: //自然减速
                                Thread.sleep(1000);
                                if (speed > 0) {
                                    speed -= 1;
                                }
                                break;
                            case 3: //手刹
                                speed = 0;
                                Log.i(TAG, "case 3 speed: " + speed);
                                break;
                            case 4: //刹车
                                Thread.sleep(300);
                                if (speed > 0) {
                                    speed -= 5;
                                }
                                break;
                            default:
                                break;
                        }
                        if (speed < 0) {
                            speed = 0;
                        }
                        if (speed > 180) {
                            speed = 180;
                        }
                        handler.sendEmptyMessage(1);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
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
                if (!isBraking) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        mType = 1;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        mType = 2;
                    }
                }
                break;
            case R.id.handbrake:
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
                    mType = 3;
                    isBraking = true;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isBraking = false;
                        }
                    }, 1000);
                }
                break;
            case R.id.brake:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mType = 4;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mType = 2;
                }
                break;
        }

        return false;
    }
}
