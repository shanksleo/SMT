package com.open.fire.pic.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.jess.arms.base.ContextProvider;
import com.jess.arms.utils.LogUtils;
import com.open.fire.pic.alarm.AlarmManagerUtil;
import com.open.fire.utils_package.base.LogTimber;
import com.open.fire.utils_package.qqutils.stage.PreferenceHelper;
import com.xdandroid.hellodaemon.AbsWorkService;

/**
 * @author shankshu
 * @date 2019/3/30 下午2:21
 */
public class AlarmWorkService extends AbsWorkService {
    /**
     * 是否 任务完成, 不再需要服务运行?
     * @return 应当停止服务, true; 应当启动服务, false; 无法判断, null.
     */
    @Override
    public Boolean shouldStopService(Intent intent, int flags, int startId) {
        String conf = PreferenceHelper.getInstance().getString("conf");
        return TextUtils.equals(conf,"end");
    }

    @Override
    public void startWork(Intent intent, int flags, int startId) {

        Log.d("hujinhao", "startWork: [intent, flags, startId]");


    }

    @Override
    public void stopWork(Intent intent, int flags, int startId) {
        Log.d("hujinhao", "stopWork: [intent, flags, startId]");

    }
    /**
     * 任务是否正在运行?
     * @return 任务正在运行, true; 任务当前不在运行, false; 无法判断, null.
     */
    @Override
    public Boolean isWorkRunning(Intent intent, int flags, int startId) {
        String conf = PreferenceHelper.getInstance().getString("conf");
        return TextUtils.equals(conf,"start");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent, Void alwaysNull) {
        return null;
    }
    //服务被杀时调用, 可以在这里面保存数据.
    @Override
    public void onServiceKilled(Intent rootIntent) {
        LogUtils.debugInfo("被杀了");
    }
}
