package com.example.yuhui.driving.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.example.yuhui.driving.R;

/**
 * Created by yuhui on 2016-6-24.
 */
public class FlipCardFragmentActivity extends FragmentActivity implements View.OnKeyListener {
    FragmentManager fragmentManager;
    float downX;
    boolean change = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flipcard_fragment_activity);
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flip_card_fragment, new FrontCardFragment(), "FrontCardFragment");
        fragmentTransaction.commit();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                change = false;
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getX() < downX) {
                    change = true;
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (change) {
                    changeFragment();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void changeFragment() {
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                R.animator.card_flip_left_in, R.animator.card_flip_left_out);
        fragmentTransaction.replace(R.id.flip_card_fragment, new BackCardFragment(), "BackCardFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            fragmentManager.popBackStack();
        }
        return false;
    }

}
