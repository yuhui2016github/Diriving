package com.example.yuhui.driving.customview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.yuhui.driving.adapter.TitleAdapter;

/**
 * Created by yuhui on 2016-6-28.
 */
public class TitleListView extends RecyclerView {
    float mOldY;
    int mDiff;

    public TitleListView(Context context) {
        super(context);
    }

    public TitleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            mOldY = event.getY();
        Log.e("yuhui", "mOldY : " + mOldY);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mOldY = event.getY();
                Log.i("yuhui", "mOldY : " + mOldY);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                mDiff = (int) (moveY - mOldY);
                Log.v("yuhui", "diff : " + mDiff);
                if (getAdapter() instanceof TitleAdapter) {
                    LinearLayout header = ((TitleAdapter) getAdapter()).getHeader();
                    header.setPadding(0, mDiff, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (getAdapter() instanceof TitleAdapter) {
                    LinearLayout header = ((TitleAdapter) getAdapter()).getHeader();
                    int mHeaderHeight =0; // header.getMeasuredHeight();
                    if (mDiff > 0) {
                        header.setPadding(0,-mHeaderHeight, 0, 0);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
