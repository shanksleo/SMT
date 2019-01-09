package com.open.fire.utils_package.widget.view;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-29
 */
public class ViewUtils {
    /**
     * 移除孩子
     *
     * @param view
     */
    public static void removeChild(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(view);
        }
    }
}
