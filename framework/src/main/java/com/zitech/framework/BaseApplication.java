package com.zitech.framework;

import android.app.Application;

/**
 * Created by lu on 2016/6/12.
 */
public class BaseApplication extends Application {
    private static BaseApplication mInstance;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
