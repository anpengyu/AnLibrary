package com.apy.anlibrary

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() ,SharedPreferences.OnSharedPreferenceChangeListener{
    private val sharedPreferences1: SharedPreferences?
        get() {
            var sharedPreferences = getSharedPreferences("test", MODE_PRIVATE)
            return sharedPreferences
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        SharedPreferences
        var sharedPreferences = sharedPreferences1
        sharedPreferences?.edit()?.putString("haha","haha")

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key != null) {
            Log.e("MainActivity",key+"   "+sharedPreferences?.getString(key,"default"))
        }
    }

    fun click(view: android.view.View) {
        sharedPreferences1?.edit()?.putString("haha","heihei")
    }
}