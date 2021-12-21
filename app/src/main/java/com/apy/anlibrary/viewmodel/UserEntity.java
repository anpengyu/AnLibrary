package com.apy.anlibrary.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.apy.anlibrary.BR;

public class UserEntity extends BaseObservable {

    private String account = "anpengyu";

    private String psw = "123456";
    @Bindable
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    @Bindable
    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
        notifyPropertyChanged(BR.psw);
    }
}
