package com.apy.anlibrary.viewmodel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.apy.anlibrary.R;
import com.apy.anlibrary.databinding.ActivityMainBinding;
import com.apy.anlibrary.livedata.LiveDataManager;
import com.apy.library.net.base.BaseActivity;

public class ViewModelActivity extends BaseActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_viewmodel);

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, factory);

        mainViewModel = viewModelProvider.get(MainViewModel.class);
        mainViewModel.getUser();
        mainViewModel.getUserResponse().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                Log.e("userResponse",userResponse.getResponse());
            }
        });
        viewDataBinding.setMainViewmodel(mainViewModel);

        LiveDataManager.mutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("LiveDataSampleActivity4",s);
            }
        });
    }

    public void click(View view){
        mainViewModel.loadData();
    }

    @Override
    public void initData() {

    }
}
