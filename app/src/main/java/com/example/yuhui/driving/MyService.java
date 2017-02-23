package com.example.yuhui.driving;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.example.yuhui.driving.aidl.ICompute;
import com.example.yuhui.driving.aidl.ITaskCallback;


/**
 * Created by yuhui on 2016-7-14.
 */
public class MyService extends Service {
    MyBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void sayHello() {
        Handler handler = new Handler(Looper.getMainLooper());
        //要toast能够正常工作，需要在主线程上运行才行,本地service就不用，因为远程binder会运行在binder线程池中
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), "Mybinder Service is on!", Toast.LENGTH_LONG).show();
            }
        });
        Log.i("yuhui", "service hello");
    }

    public void callback(ITaskCallback cb) {
        try {
            cb.clientCallback();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    class MyBinder extends ICompute.Stub {

        public MyBinder() {
        }

        public MyService getService() {
            return MyService.this;
        }

        @Override
        public int add(int a, int b) throws RemoteException {
            return 0;
        }

        @Override
        public int get() throws RemoteException {
            return 0;
        }

        @Override
        public void sayHello() throws RemoteException {
            MyService.this.sayHello();

        }

        @Override
        public void callback(ITaskCallback cb) throws RemoteException {
            MyService.this.callback(cb);
        }


    }

    class LocalBinder extends Binder {
        public void sayHello() {
            Toast.makeText(getApplicationContext(), "LocalBinder Service is on!", Toast.LENGTH_LONG).show();
        }
    }
}
