// ICompute.aidl
package com.example.yuhui.driving.aidl;
import com.example.yuhui.driving.aidl.ITaskCallback;

// Declare any non-default types here with import statements

interface ICompute {
     int add(int a,int b);
     int get();
     void sayHello();
     void callback(ITaskCallback cb);
}
