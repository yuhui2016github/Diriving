package com.example.yuhui.driving;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by yuhui on 2016-7-14.
 */
public class MyClientActivity extends Activity {
    MyService.MyBinder myBinder;
    MyService myService;
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder) service;
            myService = myBinder.getService();
            Log.i("yuhui","client hello");
            myService.sayHello();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myclient_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent();
        intent.setClass(this, MyService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
        try {
            Class.forName("edu.qust.demo.Person");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(conn);
    }
}
