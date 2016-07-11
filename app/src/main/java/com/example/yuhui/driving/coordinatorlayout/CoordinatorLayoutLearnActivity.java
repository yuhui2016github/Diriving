package com.example.yuhui.driving.coordinatorlayout;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.yuhui.driving.R;

/**
 * Created by yuhui on 2016-7-11.
 */
public class CoordinatorLayoutLearnActivity extends AppCompatActivity implements View.OnTouchListener {
    View firstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_coordinatelearn_activity);
        firstView = findViewById(R.id.first);
        firstView.setOnTouchListener(this);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "FAB", Snackbar.LENGTH_LONG)
                        .setAction("cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //这里的单击事件代表点击消除Action后的响应事件
                                Log.i("a", "");
                            }
                        })
                        .show();
            }
        });
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.first:
                //如果XML里面不设置clickable="true"，则只能收到MotionEvent.ACTION_DOWN事件
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.i("yuhui", "onTouch " + " event.getY() " + event.getY());
                    firstView.setY(event.getY());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        return false;
    }
}
