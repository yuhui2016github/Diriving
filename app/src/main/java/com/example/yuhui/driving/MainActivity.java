package com.example.yuhui.driving;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    private boolean shouldAccelerate = false;
    private boolean shouldBrake = false;

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
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        while (shouldAccelerate && speed < 180) {
                            thread.sleep(300);
                            speed += 5;
                            handler.sendEmptyMessage(1);
                        }
                        while (!shouldAccelerate && speed > 0 && !shouldBrake) {
                            thread.sleep(2000);
                            speed -= 1;
                            handler.sendEmptyMessage(1);
                        }
                        while (shouldBrake && speed > 0 && !shouldAccelerate) {
                            thread.sleep(300);
                            speed -= 5;
                            handler.sendEmptyMessage(1);
                        }
                    }
                } catch (InterruptedException ex){
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
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    shouldAccelerate = true;
                    shouldBrake = false;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    shouldAccelerate = false;
                }
                break;
            case R.id.handbrake:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    speed = 0;
                    handler.sendEmptyMessage(1);
                }
                break;
            case R.id.brake:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    shouldAccelerate = false;
                    shouldBrake = true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    shouldBrake = false;
                }
                break;
        }

        return false;
    }
}
