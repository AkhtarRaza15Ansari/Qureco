package com.sriyaan.qureco;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.sriyaan.modal.FontsOverride;

/**
 * Created by Akhtar on 25-08-2016.
 */
public class MyApplication extends MultiDexApplication{
    @Override
    protected void attachBaseContext(Context newBase){
        MultiDex.install(newBase);
        super.attachBaseContext(newBase);

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}