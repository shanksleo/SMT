package com.jess.arms.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志打印工具
 *
 * @author shankshu
 * Create on 2018-12-17
 */
public class LogTimber {
    private LogTimber() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    private static String AppName = "";
    private static boolean PrintLine = true;
    private static boolean isLog = true;

    public static boolean isLog() {
        return isLog;
    }

    private static boolean isDebugMode = true;

    public static boolean isDebug() {
        return isDebugMode;
    }

    private static boolean isBeta = true;

    public static boolean isBeta() {
        return isBeta;
    }

    /*
     * 允许输出超长日志
     * */
    public static void printDebugLogs(String tag, String msg) {
        if (!isLog) return;

        if (!isDebugMode && !isBeta) return;

        if (TextUtils.isEmpty(tag)) tag = "";

        String systemTag = generateTag();

        Log.v(systemTag, "< !----      " + tag + "     ------ Begin   -----------------------------");
        Log.v(systemTag, "< !----      isMainThread _:_" + ThreadUtils.isMainThread() + "    -----------------------------");

        {
            msg = msg.trim();
            int index = 0;
            int maxLength = 3500;
            String sub;
            while (index < msg.length()) {
                if (msg.length() <= index + maxLength) {
                    sub = msg.substring(index);
                } else {
                    sub = msg.substring(index, index + maxLength);
                }

                index += maxLength;
                Log.d(systemTag + "  < !----      ", sub.trim());
            }
        }
        Log.v(systemTag, "< !----      " + tag + "     ------ End     -----------------------------");
    }


    /**
     * 生成tag
     */
    private static String generateTag() {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        String tag = "%s.%s";
        if (!TextUtils.isEmpty(AppName)) {
            tag = AppName + "__" + tag;
        }
        if (PrintLine) {
            tag += "(Line:%d)";
            tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        } else {
            tag = String.format(tag, callerClazzName, caller.getMethodName());
        }
        return tag;
    }


}
