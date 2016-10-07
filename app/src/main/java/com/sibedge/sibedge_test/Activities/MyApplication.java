package com.sibedge.sibedge_test.Activities;

import android.app.Application;
import android.support.multidex.MultiDex;

/**
 * Created by Sermilion on 07/10/2016.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
