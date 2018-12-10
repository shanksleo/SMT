package com.jess.arms.zqutils.date;

import android.os.CountDownTimer;

/**
 * class description
 *
 * @author shankshu
 * 计时器工具
 * Create on 2018-12-10
 */
public class CountDownUtils {

    //CountDownTimer 计时器需要在onDestory解除
    //想要跟随生命周期走RX
    public void startAdConsumeTime(long countDownTimes) {
        if (countDownTimes <= 0) {
            return;
        }
        CountDownTimer countDownTimer = new CountDownTimer(countDownTimes * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
            }
        };
        countDownTimer.start();
    }


}
