package com.open.fire.pic.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;

import com.jess.arms.base.ContextProvider;
import com.open.fire.pic.app.CallUtils;
import com.open.fire.utils_package.qqutils.alert.ToastUtils;
import com.open.fire.utils_package.qqutils.stage.PreferenceHelper;

/**
 * @author shankshu
 * @date 2019/3/30 下午4:45
 */
public class LoongggAlarmReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!TextUtils.equals(action,"com.shanks.alarm")){
            return;
        }
        int id = intent.getIntExtra("id",-1);
        if (id == -1){

            Log.d("hujinhao", "id  -1   test");
            return;
        }
        String msg = intent.getStringExtra("nums");

        ToastUtils.showShort("msg:" + msg);
        CallUtils.callForward(ContextProvider.get(),"" + msg );
    }
}
