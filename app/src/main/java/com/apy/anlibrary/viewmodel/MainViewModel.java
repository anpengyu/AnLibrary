package com.apy.anlibrary.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel  extends ViewModel {
    private UserEntity userEntity;
    private MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();

    public UserEntity getUser(){
        UserEntity userEntity = new UserEntity();
        userEntity.setPsw("123456");
        userEntity.setAccount("abcd");
        this.userEntity = userEntity;
        return userEntity;
    }

    public MutableLiveData<UserResponse> getUserResponse(){
        return userResponse;
    }

    private int i=0;
    public void loadData(){


        UserResponse userResponse1 = new UserResponse(i,"登录成功"+i++);
        userResponse.postValue(userResponse1);
    }
}
