package com.open.fire.pic.alarm;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.jess.arms.base.ContextProvider;
import com.open.fire.utils_package.qqutils.stage.PreferenceHelper;

/**
 * @author shankshu
 * @date 2019/4/1 上午11:15
 */
public class AlarmWorker {


    /*静态方法，静态变量
    https://blog.csdn.net/winfredzen/article/details/78458233
    静态类：我们对嵌套类使用static关键字。static不能用于最外层的类。静态的嵌套类和其它外层的类别无二致，嵌套只是为了方便打包。
    只有 静态内部类，没有静态类的说法
    * */

    public static void start(){

        String time1 = PreferenceHelper.getInstance().getString("1_time");
        if (!TextUtils.isEmpty(time1) ) {
            String[] times = time1.split(":");
            if (times.length != 2) {
                times = time1.split("：");
            }
            String hour = times[0];
            int h = Integer.parseInt(hour);
            String minutes = times[1];
            int m = Integer.parseInt(minutes);
            AlarmManagerUtil.setAlarm(h,m,1,PreferenceHelper.getInstance().getString("1_num"));
            Log.d("hujinhao", "Alarm start: [1]");
        }
        String time2 = PreferenceHelper.getInstance().getString("2_time");
        if (!TextUtils.isEmpty(time2) ) {
            String[] times = time2.split(":");
            if (times.length != 2) {
                times = time2.split("：");
            }


            String hour = times[0];
            int h = Integer.parseInt(hour);
            String minutes = times[1];
            int m = Integer.parseInt(minutes);
            AlarmManagerUtil.setAlarm(h,m,2,PreferenceHelper.getInstance().getString("2_num"));
            Log.d("hujinhao", "Alarm start: [2]");
        }
        String time3 = PreferenceHelper.getInstance().getString("3_time");
        if (!TextUtils.isEmpty(time3) ) {
            String[] times = time3.split(":");
            if (times.length != 2) {
                times = time3.split("：");
            }
            String hour = times[0];
            int h = Integer.parseInt(hour);
            String minutes = times[1];
            int m = Integer.parseInt(minutes);
            AlarmManagerUtil.setAlarm(h,m,3,PreferenceHelper.getInstance().getString("3_num"));
            Log.d("hujinhao", "Alarm start: [3]");
        }
        PreferenceHelper.put("conf","start");
    }



    public static void end(){
        AlarmManagerUtil.cancelAlarm(ContextProvider.get(),"com.shanks.alarm",1);
        AlarmManagerUtil.cancelAlarm(ContextProvider.get(),"com.shanks.alarm",2);
        AlarmManagerUtil.cancelAlarm(ContextProvider.get(),"com.shanks.alarm",3);
        Log.d("hujinhao", "Alarm end: []");
        PreferenceHelper.put("conf","end");
    }


    public static void reflush(){
        Intent intent = new Intent("com.shanks.alarm");
        intent.setClass(ContextProvider.get(), LoongggAlarmReceiver.class);
        intent.putExtra("id", -1);
        ContextProvider.get().sendBroadcast(intent);

        end();
        start();
    }




}
