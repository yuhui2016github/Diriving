package com.example.yuhui.driving;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by yuhui on 2016-5-17.
 */
public class DashBoardView extends View {

    private int pointX = 500;
    private int pointY = 500;
    private int GRAY = 0xFF343434;
    private int BLUE = 0xE73F51B5;
    int radius = 300;
    private Paint mPaint;
    //TODO: Need fix the dpi apaptered with screen dpi
    private int mDensityDpi = 10;

    public DashBoardView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawDashBoardCircle(canvas);
        drawScale(canvas);


    }

    private void drawScale(Canvas canvas) {
        mPaint.setStrokeWidth(3 * mDensityDpi);
        mPaint.setColor(BLUE);
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(pointX - radius, pointY, pointX - radius + 20, pointY, mPaint);
            } else {
                canvas.drawLine(pointX - radius, pointY, pointX - radius + 10, pointY, mPaint);
            }
            canvas.rotate(6, pointX, pointY);
        }
    }

    private void drawDashBoardCircle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(GRAY);
        canvas.drawCircle(pointX, pointY, radius, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setColor(BLUE);
        mPaint.setStrokeWidth(4 * mDensityDpi);
        canvas.drawCircle(pointX, pointY, radius, mPaint);
        mPaint.setStrokeWidth(3 * mDensityDpi);
        canvas.drawCircle(pointX, pointY, radius - 10, mPaint);

        mPaint.setStrokeWidth(5 * mDensityDpi);
        mPaint.setColor(BLUE);
        canvas.drawCircle(pointX, pointY, radius / 2, mPaint);
        mPaint.setStrokeWidth(3 * mDensityDpi);
        canvas.drawCircle(pointX, pointY, radius / 2 + 5, mPaint);
    }

    private void drawText(Canvas canvas, int value) {
        String TEXT = String.valueOf(value);
        Paint textPaint = new Paint();
        textPaint.setColor(BLUE);
        textPaint.setStyle(Paint.Style.STROKE);
        int baseX = 0;
        int baseY = 0;
        switch (value) {
            case 0:
                // 计算Baseline绘制的起点X轴坐标
                baseX = (int) (pointX - radius * Math.cos(Math.PI / 6) + textPaint.measureText(TEXT) / 2);
                // 计算Baseline绘制的Y坐标
                baseY = (int) (pointY + radius * Math.sin(Math.PI / 6));
                break;
            case 20:
                baseX = (int) (pointX - radius + 50 + textPaint.measureText(TEXT) / 2);
                baseY = (int) (pointY);
                break;
            case 40:
                baseX = (int) (pointX - radius * Math.cos(Math.PI / 6));
                baseY = (int) (pointY - radius * Math.sin(Math.PI / 6));
                break;
            case 60:
                baseX = (int) (pointX - radius * Math.cos(2 * Math.PI / 6));
                baseY = (int) (pointY - radius * Math.sin(2 * Math.PI / 6));
                break;
            case 80:
                baseX = (int) (pointX);
                baseY = (int) (pointY - radius);
                break;
            case 100:
                baseX = (int) (pointX + radius * Math.cos(2 * Math.PI / 6) - textPaint.measureText(TEXT));
                baseY = (int) (pointY - radius * Math.sin(2 * Math.PI / 6));
                break;
            case 120:
                baseX = (int) (pointX + radius * Math.cos(Math.PI / 6) - textPaint.measureText(TEXT));
                baseY = (int) (pointY - radius * Math.sin(Math.PI / 6));
                break;
            case 140:
                baseX = (int) (pointX + radius - textPaint.measureText(TEXT));
                baseY = (int) (pointY);
                break;
            case 160:
                baseX = (int) (pointX + radius * Math.cos(Math.PI / 6) - textPaint.measureText(TEXT));
                baseY = (int) (pointY + radius * Math.sin(Math.PI / 6));
                break;
        }
        canvas.drawText(TEXT, baseX, baseY, textPaint);
    }
}
