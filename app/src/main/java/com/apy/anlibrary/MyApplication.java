package com.apy.anlibrary;

import androidx.multidex.MultiDex;

import com.apy.library.LibraryApplication;

public class MyApplication extends LibraryApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
