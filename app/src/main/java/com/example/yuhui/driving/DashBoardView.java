package com.example.yuhui.driving;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * Created by yuhui on 2016-5-17.
 */
public class DashBoardView extends SurfaceView implements SurfaceHolder.Callback {

    private int pointX;
    private int pointY;
    private int GRAY = 0xFF343434;
    private int BLUE = 0xE73F51B5;
    private int baseX = 0;
    private int baseY = 0;
    private int radius = 250;
    private Paint mPaint;
    private Paint textPaint;
    private Paint speedAreaPaint;
    private int speed = 0;
    //TODO: N eed fix the dpi apaptered with screen dpi
    private int mDensityDpi = 1;
    private SurfaceHolder mHolder;
    private boolean running;

    public DashBoardView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        textPaint = new Paint();
        speedAreaPaint = new Paint();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        pointX = width / 2;
        pointY = height / 3;
        mHolder = this.getHolder();
        mHolder.addCallback(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawDashBoardCircle(canvas);
        drawSpeedArea(canvas);
        drawScale(canvas);
        for (int i = 0; i <= 9; i++) {
            drawText(canvas, i * 20);
        }
        drawCenter(canvas);
    }

    private void drawSpeedArea(Canvas canvas) {
        int degree;
        if (speed < 180) {
            degree = speed * 30 / 20;
        } else {
            degree = 180 * 30 / 20;
        }
        RectF speedRectF = new RectF(pointX - (radius - 10), pointY - (radius - 10),
                pointX + (radius - 10), pointY + (radius - 10));
        speedAreaPaint.setColor(BLUE);
        speedAreaPaint.setAntiAlias(true);
        speedAreaPaint.setAlpha(180);
        speedAreaPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(speedRectF, 150, degree, true, speedAreaPaint);

        //不显示中间的内圈的扇形区域
        RectF speedRectFInner = new RectF();
        speedRectFInner = new RectF(pointX - (radius / 2), pointY - (radius / 2),
                pointX + (radius / 2), pointY + (radius / 2));
        mPaint.setColor(GRAY);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(speedRectFInner, 150 - 5, degree + 5, true, mPaint);
    }

    private void drawCenter(Canvas canvas) {
        textPaint.setTextSize(60);
        float tw = textPaint.measureText(String.valueOf(speed));
        baseX = (int) (pointX - tw / 2);
        baseY = (int) (pointY + Math.abs(textPaint.descent() + textPaint.ascent()) / 3);
        canvas.drawText(String.valueOf(speed), baseX, baseY, textPaint);
        //单位
        textPaint.setTextSize(20);
        tw = textPaint.measureText("km/h");
        baseX = (int) (pointX - tw / 2);
        baseY = (int) (pointY + radius / 4 + Math.abs(textPaint.descent() + textPaint.ascent()) / 4);
        canvas.drawText("km/h", baseX, baseY, textPaint);
    }

    private void drawScale(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3 * mDensityDpi);
        mPaint.setColor(BLUE);
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(pointX - radius, pointY, pointX - radius + 40, pointY, mPaint);
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

        mPaint.setStrokeWidth(1 * mDensityDpi);
        mPaint.setColor(BLUE);
        canvas.drawCircle(pointX, pointY, radius / 2, mPaint);
        mPaint.setStrokeWidth(3 * mDensityDpi);
        canvas.drawCircle(pointX, pointY, radius / 2 + 5, mPaint);
    }

    private void drawText(Canvas canvas, int value) {
        textPaint.setColor(Color.WHITE);
        String TEXT = String.valueOf(value);
        textPaint.setTextSize(20);
        switch (value) {
            case 0:
                // 计算Baseline绘制的起点X轴坐标
                baseX = (int) (pointX - (radius - 40) * Math.cos(Math.PI / 6));
                // 计算Baseline绘制的Y坐标
                baseY = (int) (pointY + (radius - 40) * Math.sin(Math.PI / 6));
                break;
            case 20:
                baseX = (int) (pointX - (radius - 40) + textPaint.measureText(TEXT) / 2);
                baseY = (int) (pointY + textPaint.measureText(TEXT) / 2);
                break;
            case 40:
                baseX = (int) (pointX - (radius - 40) * Math.cos(Math.PI / 6));
                baseY = (int) (pointY - (radius - 40) * Math.sin(Math.PI / 6));
                break;
            case 60:
                baseX = (int) (pointX - (radius - 40) * Math.cos(2 * Math.PI / 6));
                baseY = (int) (pointY - (radius - 40) * Math.sin(2 * Math.PI / 6));
                break;
            case 80:
                baseX = (int) (pointX - textPaint.measureText(TEXT) / 2);
                baseY = (int) (pointY - (radius - 40) + textPaint.measureText(TEXT) / 2);
                break;
            case 100:
                baseX = (int) (pointX + (radius - 40) * Math.cos(2 * Math.PI / 6) - textPaint.measureText(TEXT));
                baseY = (int) (pointY - (radius - 40) * Math.sin(2 * Math.PI / 6));
                break;
            case 120:
                baseX = (int) (pointX + (radius - 40) * Math.cos(Math.PI / 6) - textPaint.measureText(TEXT));
                baseY = (int) (pointY - (radius - 40) * Math.sin(Math.PI / 6));
                break;
            case 140:
                baseX = (int) (pointX + (radius - 50) - textPaint.measureText(TEXT));
                baseY = (int) (pointY + textPaint.measureText(TEXT) / 3);
                break;
            case 160:
                baseX = (int) (pointX + (radius - 40) * Math.cos(Math.PI / 6) - textPaint.measureText(TEXT));
                baseY = (int) (pointY + (radius - 40) * Math.sin(Math.PI / 6));
                break;
            case 180:
                baseX = (int) (pointX + (radius - 40) * Math.cos(2 * Math.PI / 6) - textPaint.measureText(TEXT));
                baseY = (int) (pointY + (radius - 40) * Math.sin(2 * Math.PI / 6));
                break;
        }
        canvas.drawText(TEXT, baseX, baseY, textPaint);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        running = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    Canvas canvas = mHolder.lockCanvas();
                    try {
                        drawDashBoardCircle(canvas);
                        drawSpeedArea(canvas);
                        drawScale(canvas);
                        for (int i = 0; i <= 9; i++) {
                            drawText(canvas, i * 20);
                        }
                        drawCenter(canvas);

                    } catch (NullPointerException ep) {
                        ep.printStackTrace();
                    } finally {
                        if (canvas != null) {
                            mHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        running = false;
    }
}
