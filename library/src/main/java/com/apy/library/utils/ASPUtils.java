package com.apy.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ASPUtils {

    private static SharedPreferences sp;

    public static void init(@NonNull Context context) {
        sp = context.getSharedPreferences(name, mode);
    }

    public ASPUtils(Context context) {
        sp = context.getSharedPreferences(name, mode);
    }

    private final static String name = "config";
    private final static int mode = Context.MODE_PRIVATE;

    /**
     * 保存首选项
     */
    public static void saveBoolean(@NonNull Context context, String key, boolean value) {
        sp = context.getSharedPreferences(name, mode);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }
    /**
     * 保存首选项
     */
    public static void saveBoolean(String key, boolean value) {
        sp = UIUtils.getContext().getSharedPreferences(name, mode);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }
    public static void saveBoolean(String key) {
        sp = UIUtils.getContext().getSharedPreferences(name, mode);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, true);
        edit.apply();
    }


    public static void remove(@NonNull Context context, String key) {
        sp = context.getSharedPreferences(name, mode);
        sp.edit().remove(key).apply();
    }

    public static void saveInt(String key, int value) {
        sp = UIUtils.getContext().getSharedPreferences(name, mode);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static void saveInt(@NonNull Context context, String key, int value) {
        sp = context.getSharedPreferences(name, mode);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static void saveString(String key, String value) {
        sp = UIUtils.getContext().getSharedPreferences(name, mode);
        SharedPreferences.Editor edit = sp.edit();
        if(value!=null){
            edit.putString(key, value);
            edit.apply();
        }

    }

    public static String getString(String key) {
        return sp.getString(key, "");
    }

    @Nullable
    public static String getStringForResult(String key) {
        sp = UIUtils.getContext().getSharedPreferences(name, mode);
        return sp.getString(key, "0");
    }

    /**
     * 获取首选项
     */
    public static boolean getBoolean(@NonNull Context context, String key, boolean defValue) {
        sp = UIUtils.getContext().getSharedPreferences(name, mode);
        return sp.getBoolean(key, defValue);
    }
    public static boolean getBoolean(String key, boolean defValue) {
        sp = UIUtils.getContext().getSharedPreferences(name, mode);
        return sp.getBoolean(key, defValue);
    }
    public static boolean getBoolean(String key) {
        sp = UIUtils.getContext().getSharedPreferences(name, mode);
        return sp.getBoolean(key, false);
    }

    public static int getInt(@NonNull Context context, String key, int defValue) {
        sp = context.getSharedPreferences(name, mode);
        return sp.getInt(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        sp = UIUtils.getContext().getSharedPreferences(name, mode);
        return sp.getInt(key, defValue);
    }

    @Nullable
    public static String getString(@NonNull Context context, String key, String defValue) {
        sp = context.getSharedPreferences(name, mode);
        return sp.getString(key, defValue);
    }

    @Nullable
    public static String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }


    public static void clear() {
        sp = UIUtils.getContext().getSharedPreferences("config", 0);
        sp.edit().clear().apply();
    }


    public static void putStringSet(String key, HashSet<String> value) {
        sp = UIUtils.getContext().getSharedPreferences(name, mode);
        SharedPreferences.Editor edit = sp.edit();
        edit.putStringSet(key, value);
        edit.apply();
    }

    @Nullable
    public static HashSet<String> getStringSet(String prefCookies, HashSet<String> objects) {
        return (HashSet<String>) sp.getStringSet(prefCookies, objects);
    }

    /**
     * 批量保存
     *
     * @param map
     */
    public void put(@Nullable HashMap<String, Object> map) {
        if (map != null && map.size() > 0) {
            SharedPreferences.Editor edit = sp.edit();
            Set<String> set = map.keySet();
            for (String key : set) {
                Object value = map.get(key);
                if (value instanceof String) {
                    edit.putString(key, (String) value);
                } else if (value instanceof Integer) {
                    edit.putInt(key, (Integer) value);
                } else if (value instanceof Boolean) {
                    edit.putBoolean(key, (Boolean) value);
                } else if (value instanceof Float) {
                    edit.putFloat(key, (Float) value);
                } else if (value instanceof Long) {
                    edit.putLong(key, (Long) value);
                } else {
                    Log.w("SpUtil", "保存 >>跳过了一些不属于 string,int,boolean,float,long 类型的值.不支持的类型: " + map.toString());
                    // + value.getClass().getSimpleName()
                }
            }
            boolean b = edit.commit();
        }
    }
}