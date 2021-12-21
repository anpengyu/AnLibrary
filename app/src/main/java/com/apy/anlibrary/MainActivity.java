package com.apy.anlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.apy.anlibrary.livedata.LiveDataManager;
import com.apy.anlibrary.livedata.LiveDataSampleActivity;
import com.apy.library.net.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LiveDataManager.mutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("LiveDataSampleActivity4",s);
            }
        });
    }

    public void click(View view){
        LiveDataManager.mutableLiveData.postValue("123456");
        startActivity(new Intent(this, LiveDataSampleActivity.class));
    }

    @Override
    public void initData() {

    }
}
