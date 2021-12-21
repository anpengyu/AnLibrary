package com.apy.anlibrary.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class UserResponse  extends BaseObservable {

    private int code ;
    private String response;

    public UserResponse(int code, String response) {
        this.code = code;
        this.response = response;
    }

    @Bindable
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @Bindable
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
