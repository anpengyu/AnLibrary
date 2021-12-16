package com.apy.library.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.apy.library.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.ACTIVITY_SERVICE;

public class UIUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        ASPUtils.init(context);
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    /**
     * 判断本应用是否已经位于最前端
     *
     * @return 本应用已经位于最前端时，返回 true；否则返回 false
     */
    private static boolean isRunningForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses(); /*枚举进程*/
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfoList) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(context.getApplicationInfo().processName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将应用置为前台应用
     */
    public static void setTopApp(Context context) {
//        if (!isRunningForeground(context)) { /*获取ActivityManager*/
            ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE); /*获得当前运行的task(任务)*/
            if (activityManager == null) {
                return;
            }
            List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) { /*找到本应用的 task，并将它切换到前台*/
                if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
                    activityManager.moveTaskToFront(taskInfo.id, 0);
                    break;
                }
            }
//        }
    }

    public static void openLed(Activity activity) {
        activity.sendBroadcast(new Intent("duoyue.intent.action.SDK_LR_FLASH_LED_ON"));
    }

    public static void closeLed(Activity activity) {
        activity.sendBroadcast(new Intent("duoyue.intent.action.SDK_LR_FLASH_LED_OFF"));
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 删除其他版本APK，只留最新
     *
     * @param file APK存放地址
     * @param path 最新版本APK
     */
    public static void deleteFileRetain(File file, File path) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (!f.equals(path)) {
                        boolean delete = f.delete();
                        if (delete) {
                            Logger.e("删除成功");
                        }
                    }
                }
            }
        }
    }

    public static String getCurrentDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前时间 年-月-日 时：分：秒
     *
     * @return 2019年04月17日 13:30:20
     */
    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getCurrentDate(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// HH:mm:ss
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    public static boolean isRunService(Context context, String serviceName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("PrivateApi")
    public static Activity getActivity() {
        Class activityThreadClass;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean ishasSimCard(Context context, boolean isSendSMS) {
        TelephonyManager telMgr = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = false; // 没有SIM卡
                break;
        }
        if (isSendSMS) {
            return false;
        } else {
            return result;
        }

        //        return result;
    }

    @SuppressLint({"SimpleDateFormat", "Recycle"})
    public static int getCallHistoryList(Activity activity, int num) {
        Cursor cs;
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_CALL_LOG}, 1000);

        }
        cs = activity.getContentResolver().query(CallLog.Calls.CONTENT_URI, //系统方式获取通讯录存储地址
                new String[]{
                        CallLog.Calls.CACHED_NAME,  //姓名
                        CallLog.Calls.NUMBER,    //号码
                        CallLog.Calls.TYPE,  //呼入/呼出(2)/未接
                        CallLog.Calls.DATE,  //拨打时间
                        CallLog.Calls.DURATION,   //通话时长
                        "phone_account_address"
                }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        int i = 0;
        if (cs != null && cs.getCount() > 0) {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date_today = simpleDateFormat.format(date);
            for (cs.moveToFirst(); (!cs.isAfterLast()) && i < num; cs.moveToNext(), i++) {
                String callName = cs.getString(0);  //名称
                String callNumber = cs.getString(1);  //号码
                String localPhone = cs.getString(5);  //号码
                ASPUtils.saveString("LOCAL_PHONE",localPhone);
                //如果名字为空，在通讯录查询一次有没有对应联系人
                if (callName == null || callName.equals("")) {
                    String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME};
                    //设置查询条件
                    String selection = ContactsContract.CommonDataKinds.Phone.NUMBER + "='" + callNumber + "'";
                    Cursor cursor = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            cols, selection, null, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                    }
                    cursor.close();
                }
                //通话类型
                int callType = Integer.parseInt(cs.getString(2));
                switch (callType) {
                    case CallLog.Calls.INCOMING_TYPE:
                        //                        callTypeStr = CallLogInfo.CALLIN;
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        //                        callTypeStr = CallLogInfo.CALLOUT;
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        //                        callTypeStr = CallLogInfo.CAllMISS;
                        break;
                    default:
                        //其他类型的，例如新增号码等记录不算进通话记录里，直接跳过
                        Log.i("ssss", "" + callType);
                        i--;
                        continue;
                }
                //拨打时间
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date callDate = new Date(Long.parseLong(cs.getString(3)));
                String callDateStr = sdf.format(callDate);
                if (callDateStr.equals(date_today)) { //判断是否为今天
                    sdf = new SimpleDateFormat("HH:mm");
                    callDateStr = sdf.format(callDate);
                } else if (date_today.contains(callDateStr.substring(0, 7))) { //判断是否为当月
                    sdf = new SimpleDateFormat("dd");
                    int callDay = Integer.parseInt(sdf.format(callDate));

                    int day = Integer.parseInt(sdf.format(date));
                    if (day - callDay == 1) {
                        callDateStr = "昨天";
                    } else {
                        sdf = new SimpleDateFormat("MM-dd");
                        callDateStr = sdf.format(callDate);
                    }
                } else if (date_today.contains(callDateStr.substring(0, 4))) { //判断是否为当年
                    sdf = new SimpleDateFormat("MM-dd");
                    callDateStr = sdf.format(callDate);
                }

                //通话时长
                int callDuration = Integer.parseInt(cs.getString(4));
                //                String startTime = cs.getString(3);
                //                Log.i("Msg11","callName"+callName);
                //                Log.i("Msg11","callNumber"+callNumber);
                //                Log.i("Msg11","callTypeStr"+callTypeStr);
                Log.e("Msg11", "callDateStr" + callDateStr);
                Log.e("Msg11", "callDurationStr" + callDuration);
                return callDuration;
            }
        }
        return 0;
    }

    /**
     * 判断是否包含SIM卡
     *
     * @return 状态
     */
    public static boolean ishasSimCard(Context context) {
//        return true;
        return ishasSimCard(context, false);
    }

    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    boolean delete = f.delete();
                    Logger.e("删除文件" + delete);
                }
            }
        }

    }

    /**
     * 获取EditText的值
     */
    public static String getEditText(@NonNull EditText et) {
        return et.getText().toString().trim();
    }

    public static boolean isEmpty(EditText et) {
        return TextUtils.isEmpty(getEditText(et));
    }

    /**
     * 获取当前时间
     */
    public static String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 获取当前时间 和时间
     */
    public static String getDateTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    public static String getDateTimeSecond() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 判断当前是否有网络
     */
    public static boolean isNetworkConnected() {
        if (getContext() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            assert mConnectivityManager != null;
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable()
                        && mNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public static int getPswWeek(String time){
        String Week = "";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(Objects.requireNonNull(format.parse(time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek = c.get(Calendar.DAY_OF_WEEK);
        return wek;
    }

    /**
     * 根据当前日期获得是星期几
     * time=yyyy-MM-dd
     */
    public static String getWeek(String time) {
        String Week = "";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(Objects.requireNonNull(format.parse(time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek = c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "周日";
        }
        if (wek == 2) {
            Week += "周一";
        }
        if (wek == 3) {
            Week += "周二";
        }
        if (wek == 4) {
            Week += "周三";
        }
        if (wek == 5) {
            Week += "周四";
        }
        if (wek == 6) {
            Week += "周五";
        }
        if (wek == 7) {
            Week += "周六";
        }
        return Week;
    }

    public static String getWeekStr(int wek) {
        String Week = "";
        if (wek == 0) {
            Week = "周一";
        }
        if (wek == 1) {
            Week = "周二";
        }
        if (wek == 2) {
            Week = "周三";
        }
        if (wek == 3) {
            Week = "周四";
        }
        if (wek == 4) {
            Week = "周五";
        }
        if (wek == 5) {
            Week = "周六";
        }
        if (wek == 6) {
            Week = "周日";
        }
        return Week;
    }

    public static void createFileDirs(String path) {
        File file1 = new File(path);
        if (!file1.exists()) {
            boolean mkdirs = file1.mkdirs();
            if (!mkdirs) {
                AToast.showTextToast("目录创建失败");
            }
        }
    }

    //TODO:获取刷卡卡号转换为学生正规卡号
    public static String cardEvent(final long cardId) {
        String s = Long.toHexString(cardId);
        List<String> result = new ArrayList<>();
        int iLen = s.length();
        while (iLen >= 2) {
            String tmp = s.substring(0, 2);
            result.add(tmp);
            s = s.substring(2);
            iLen = s.length();
        }
        StringBuilder string = new StringBuilder();
        for (int i = result.size() - 1; i >= 0; i--) {
            string.append(result.get(i));
        }
        String mCardId = String.valueOf(Long.parseLong(string.toString(), 16));
        if (mCardId.length() < 10) {
            mCardId = "0" + mCardId;
        }
        return mCardId;
    }

    /**
     * 获取文件夹中所有文件数量
     *
     * @param dir 文件夹
     * @return 总数量
     */
    public static int getFileNums(File dir) {
        if (dir == null) {
            return 0;
        }
        int count = 0;
        if (dir.exists() && dir.isDirectory() && dir.listFiles() != null) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file != null && file.isDirectory()) {
                    continue;
                }
                count++;
            }
        }
        return count;
    }

    public static List<String> getPkgList() {
        List<String> packages = new ArrayList<String>();
        try {
            Process p = Runtime.getRuntime().exec("pm list packages");
            InputStreamReader isr = new InputStreamReader(p.getInputStream(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                line = line.trim();
                if (line.length() > 8) {
                    String prefix = line.substring(0, 8);
                    if (prefix.equalsIgnoreCase("package:")) {
                        line = line.substring(8).trim();
                        if (!TextUtils.isEmpty(line)) {
                            packages.add(line);
                        }
                    }
                }
                line = br.readLine();
            }
            br.close();
            p.destroy();
        } catch (Throwable t) {

        }
        return packages;
    }


}