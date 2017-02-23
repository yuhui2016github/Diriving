package com.example.yuhui.driving;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.yuhui.driving.adapter.TitleAdapter;
import com.example.yuhui.driving.animation.AnimationActivity;
import com.example.yuhui.driving.coordinatorlayout.CoordinatorLayoutLearnActivity;
import com.example.yuhui.driving.customview.TitleListView;
import com.example.yuhui.driving.fragments.FlipCardFragmentActivity;
import com.example.yuhui.driving.fragments.FragmentsActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements TitleAdapter.OnItemClickListener {

    @BindView(R.id.recycler_view)
    TitleListView recyclerView;

    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] tempTitles = getResources().getStringArray(R.array.titles);
        List<String> titles = Arrays.asList(tempTitles);
        TitleAdapter titleAdapter = new TitleAdapter(this);
        titleAdapter.setTitles(titles);
        titleAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(titleAdapter);
    }


    @Override
    public void onClick(String item) {
        Intent intent = new Intent();
        if (item.equals("Fragments")) {
            intent.setClass(this, FragmentsActivity.class);
        } else if (item.equals("DashBoardView")) {
            intent.setClass(this, DashBoardActivity.class);
        } else if (item.equals("Animation")) {
            intent.setClass(this, AnimationActivity.class);
        } else if (item.equals("CoordinatorLayout")) {
            intent.setClass(this, CoordinatorLayoutLearnActivity.class);
        } else if (item.equals("Client and Service")) {
            intent.setClass(this, MyClientActivity.class);
        } else if (item.equals("FlipCardFragmentActivity")) {
            intent.setClass(this, FlipCardFragmentActivity.class);
        } else if (item.equals("DrawingActivity")) {
            intent.setClass(this, DrawingActivity.class);
        } else if (item.equals("ui in workThread")) {
            NonUiThread nonUiThread = new NonUiThread();
            nonUiThread.start();
            return;
        } else if (item.equals("update ui in UIThread")) {
            //由于tx所在的viewroot是在子线程创建的，因此如果在主线程操作tx会报
            // ViewRootImpl$CalledFromWrongThreadException:
            // Only the original thread that created a view hierarchy can touch its views.
            // ViewRootImpl是在windowManager.addView() -> WindowManagerGlobal.addView()中创建的
            // 一方面ViewRootImpl作为整个控件树的根部，它负责触发空间的测量、布局、绘制以及输入事件的派发
            // 另一方面ViewRootImpl还负责与WMS交互通信以调整窗口的位置大小，以及对来自WMS的事件（如窗口尺寸改变等）作出处理
            tx.setText("update ui in UIThread");
            return;
        } else {
            return;
        }
        startActivity(intent);
    }

    class NonUiThread extends Thread {
        private static final String TAG = "NonUiThread";

        @Override
        public void run() {
            Looper.prepare();
            tx = new TextView(MainActivity.this);
            tx.setText("non-UiThread update textview");
            Log.i(TAG, "" + Thread.currentThread());
            WindowManager windowManager = MainActivity.this.getWindowManager();
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    200, 200, 200, 200, WindowManager.LayoutParams.FIRST_SUB_WINDOW,
                    WindowManager.LayoutParams.TYPE_TOAST, PixelFormat.TRANSLUCENT);
            windowManager.addView(tx, params);
            Looper.loop();
        }
    }
}
