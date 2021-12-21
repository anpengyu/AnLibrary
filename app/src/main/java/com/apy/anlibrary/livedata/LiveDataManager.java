package com.apy.anlibrary.livedata;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

public class LiveDataManager {
    private static LiveDataManager instance;
    private Map<String,MutableLiveData> mutableLiveDataMap;

    private LiveDataManager() {
        mutableLiveDataMap = new HashMap<>();
    }

    public static LiveDataManager getInstance() {
        if (instance == null) {
            instance = new LiveDataManager();
        }
        return instance;
    }

    public static MutableLiveData<String> mutableLiveData = new MutableLiveData();

    public<T> MutableLiveData with(String key,Class<T> clazz){
        if(!mutableLiveDataMap.containsKey(key)){
            mutableLiveDataMap.put(key,new MutableLiveData<Object>());
        }
        return mutableLiveDataMap.get(key);
    }
}
