package com.example.yuhui.driving;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by yuhui on 2016-7-21.
 */
public class DrawingActivity extends Activity {
    DrawingView dv;
    private Paint mPaint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dv = new DrawingView(this);
        setContentView(dv);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(8);


        Log.d("onCreate", "onCreateActivityMain=========================");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //运行到这个方法的时候系统才会肯定得告知你这个activity已经加载完毕。可以和用户进行交互了,也就是ContentView可以用了
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        TextView textView_1 = new TextView(this);
        textView_1.setText("PopupWindow1");
        PopupWindow popupWindow1 = new PopupWindow(textView_1,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, false);
        textView_1.setTextColor(Color.BLUE);
        popupWindow1.setOutsideTouchable(false);
        popupWindow1.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_back));
        popupWindow1.showAtLocation(dv, Gravity.LEFT | Gravity.CENTER_HORIZONTAL, 0, 0);

        TextView textView_2 = new TextView(this);
        textView_2.setText("PopupWindow2");
        textView_2.setTextColor(Color.RED);
        PopupWindow popupWindow2 = new PopupWindow(textView_2,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, false);
        popupWindow1.setOutsideTouchable(false);
        popupWindow2.showAtLocation(dv, Gravity.RIGHT | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public class DrawingView extends View {

        public int width;
        public int height;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

        public DrawingView(Context c) {
            super(c);
            context = c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            mBitmapPaint.setColor(Color.BLUE);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.RED);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);
            Log.d("DrawingView", "DrawingView=========================");

        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);

            Log.d("OnSizeChanged", "OnSizeChanged=========================");

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

            canvas.drawPath(mPath, mPaint);

            canvas.drawPath(circlePath, circlePaint);

            Log.d("onDraw", "onDraw================================");
        }

        private float mX, mY, sX, sY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            sX = mX = x;
            sY = mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
//                mPath.moveTo(mX,mY);
//                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mPath.quadTo(mX, mY, x, y);//连续调用多次quadTo，起点会是上一次划线的终点
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }
}
