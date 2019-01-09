package com.open.fire.utils_package.base;

import android.content.Context;
import android.content.res.Resources;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-29
 */
public class Resource {
    private static final String TAG = Resource.class.getSimpleName();
    /**
     * 获得资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }
    /**
     * 获得颜色
     */
    public static int getColor(Context context, int rid) {
        return getResources(context).getColor(rid);
    }
    /**
     * 得到字符数组
     */
    public static String[] getStringArray(Context context, int id) {
        return getResources(context).getStringArray(id);
    }

    /**
     * 从 dimens 中获得尺寸
     *
     * @param context
     * @param id
     * @return
     */
    public static int getDimens(Context context, int id) {
        return (int) getResources(context).getDimension(id);
    }
    /**
     * 从String 中获得字符
     *
     * @return
     */

    public static String getString(Context context, int stringID) {
        return getResources(context).getString(stringID);
    }

}
