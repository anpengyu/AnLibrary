package com.apy.anlibrary.livedata;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.apy.anlibrary.R;
import com.apy.library.net.base.BaseActivity;

public class LiveDataSampleActivity extends BaseActivity {

    private MutableLiveData<String> liveData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedata);

        liveData = new MutableLiveData<>();
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("LiveDataSampleActivity1",s);
            }
        });
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("LiveDataSampleActivity2",s);
            }
        });
        LiveDataManager.mutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("LiveDataSampleActivity3",s);
            }
        });
    }

    public void click(View view) {
        liveData.postValue("abc");
        LiveDataManager.mutableLiveData.postValue("123");
    }

    @Override
    public void initData() {

    }
}
