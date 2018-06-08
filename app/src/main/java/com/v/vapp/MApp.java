package com.v.vapp;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * @author V
 * @since 2018/5/25
 */
public class MApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
