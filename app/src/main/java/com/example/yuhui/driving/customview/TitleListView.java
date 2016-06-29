package com.example.yuhui.driving.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mOldY = event.getY();
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int mHeaderHeight = ((TitleAdapter) getAdapter()).getHeaderHeight();
        LinearLayoutManager mLayoutManager = (LinearLayoutManager) getLayoutManager();
        int firstPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        int lastPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
        Log.e("yuhui", "lastPosition" + lastPosition + "   mLayoutManager().getChildCount  " + mLayoutManager.getChildCount());
        Log.i("yuhui", "mDiff " + mDiff);
        Log.i("yuhui", "mOldY " + mOldY);
        // TODO: 2016-6-29  需要控制好，当滑动是从第二个页面开始的情况
        if (lastPosition == mLayoutManager.getChildCount()) {
            if (mDiff > 0) {
                mOldY = event.getY();
            }
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                mDiff = (int) (moveY - mOldY);
                if (getAdapter() instanceof TitleAdapter) {
                    LinearLayout header = ((TitleAdapter) getAdapter()).getHeader();


                    if (firstPosition <= 1 && mDiff > 0) {
                        Log.i("yuhui", "ACTION_MOVE mDiff " + mDiff + "   mHeaderHeight " + mHeaderHeight);
                        header.setPadding(0, mDiff - mHeaderHeight, 0, 0);
                        return true;
                    }
                    if (header.getPaddingTop() > -mHeaderHeight) {
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (getAdapter() instanceof TitleAdapter) {
                    if (firstPosition <= 1) {
                        final LinearLayout header = ((TitleAdapter) getAdapter()).getHeader();
                        ValueAnimator animator = new ValueAnimator().ofInt(header.getPaddingTop(),-mHeaderHeight);
                        animator.setDuration(500);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                int animatedValue = (int) animation.getAnimatedValue();
                                header.setPadding(0, animatedValue, 0, 0);
                            }
                        });
                        animator.start();
                        return true;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
