package com.apy.anlibrary.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.multidex.MultiDex;

import com.apy.library.LibraryApplication;

public class MyApplication extends LibraryApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);


    }
}
