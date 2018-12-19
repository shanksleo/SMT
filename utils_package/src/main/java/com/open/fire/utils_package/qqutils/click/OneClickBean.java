package com.open.fire.utils_package.qqutils.click;


import com.open.fire.utils_package.base.LogTimber;

import java.util.Calendar;

/**
 * @author shankshu
 * Create on 2018-12-11
 */
public class OneClickBean {
    private String methodName;
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    public OneClickBean(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean check() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return false;
        } else {
            LogTimber.printDebugLogs("Ckick Event " + "View Shake On Click Denied : _" , getMethodName());
            return true;
        }
    }
}

