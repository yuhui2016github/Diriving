package com.example.yuhui.driving;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashBoardActivity extends Activity implements View.OnTouchListener,View.OnClickListener{

    private static final String TAG = "driving";

    private DashBoardView dashBoardView;
    private Handler handler;
    private int mType = 0;
    private boolean isBraking = false;
    private int speed = 0;
    private boolean running;

    @BindView(R.id.accelerate)Button accelerate ;
    @BindView(R.id.handbrake)Button handbrake ;
    @BindView(R.id.brake)Button brake;
    @BindView(R.id.animation)Button animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity_layout);
        ButterKnife.bind(this);
        dashBoardView = (DashBoardView) findViewById(R.id.dashBoardView);
        setButtonListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new StaticHandler(this);
        running = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (running) {
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

    /**
     * 防止内存溢出
     */
    public static class StaticHandler extends Handler {
        private final WeakReference<DashBoardActivity> mActivity;

        public StaticHandler(DashBoardActivity activity) {
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mActivity.get().setSpeed();
//            mActivity.get().dashBoardView.invalidate();
        }
    }


    private void setButtonListeners() {
        accelerate.setOnTouchListener(this);
        handbrake.setOnTouchListener(this);
        brake.setOnTouchListener(this);
        animation.setOnClickListener(this);
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
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.animation:
                Intent intent = new Intent(DashBoardActivity.this,AnimationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Bundle_key_1","Bundle_value_1");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void setSpeed() {
        dashBoardView.setSpeed(speed);
    }
}
