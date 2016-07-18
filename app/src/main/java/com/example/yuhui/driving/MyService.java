package com.example.yuhui.driving;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by yuhui on 2016-7-14.
 */
public class MyService extends Service {
    MyBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void sayHello() {
        Handler handler = new Handler(Looper.getMainLooper());
        //要toast能够正常工作，需要在Activity的主线程上运行才行
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), "Service is on!", Toast.LENGTH_LONG).show();
            }
        });
        Log.i("yuhui", "service hello");
    }


    class MyBinder extends Binder {

        public MyBinder() {
        }

        public MyService getService() {
            return MyService.this;
        }
    }
}