package com.open.fire.utils_package.base;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-25
 */
public class StatusBarUtilsPlus {

    /**
     * 全屏,并且沉侵式状态栏
     * 去除顶栏和低栏，一般使用在启动页
     *
     * @param activity
     */
    public static void statuInScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //https://blog.csdn.net/QQxiaoqiang1573/article/details/79867127
            /*
             * View.SYSTEM_UI_FLAG_LOW_PROFILE   设置状态栏和导航栏中的图标变小，变模糊或者弱化其效果。这个标志一般用于游戏，电子书，视频，或者不需要去分散用户注意力的应用软件。
             * View.SYSTEM_UI_FLAG_HIDE_NAVIGATION    隐藏导航栏，点击屏幕任意区域，导航栏将重新出现，并且不会自动消失。
             * View.SYSTEM_UI_FLAG_FULLSCREEN    隐藏状态栏，点击屏幕区域不会出现，需要从状态栏位置下拉才会出现。
             * View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION   将布局内容拓展到导航栏的后面。
             * View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN   将布局内容拓展到状态的后面。
             * View.SYSTEM_UI_FLAG_LAYOUT_STABLE  稳定布局，主要是在全屏和非全屏切换时，布局不要有大的变化。(就是界面上的Button不会出现顶栏消失而网上弹一下的效果)一般和View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN、View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION搭配使用。同时，android:fitsSystemWindows要设置为true。
             * View.SYSTEM_UI_FLAG_IMMERSIVE       使状态栏和导航栏真正的进入沉浸模式,即全屏模式，如果没有设置这个标志，设置全屏时，我们点击屏幕的任意位置，就会恢复为正常模式。所以，View.SYSTEM_UI_FLAG_IMMERSIVE都是配合View.SYSTEM_UI_FLAG_FULLSCREEN和View.SYSTEM_UI_FLAG_HIDE_NAVIGATION一起使用的。
             * View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY       它的效果跟View.SYSTEM_UI_FLAG_IMMERSIVE一样。但是，它在全屏模式下，用户上下拉状态栏或者导航栏时，这些系统栏只是以半透明的状态显示出来，并且在一定时间后会自动消失

             *  */

            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(attrs);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /*
     *  沉浸式适配方案
     *
     * */
    public static void setFullScreen(Activity activity, boolean mVisible) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (mVisible) { //全屏
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LOW_PROFILE
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            } else { //非全屏
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                );
            }
        } else {
            if (mVisible) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            } else {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
        }

    }



    /*
     *  getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
     *  如果前面使用的是setSystemUiVisibility  ，那么顶栏会变成灰色原色
     *  如果是 getWindow().addFlags(
     *                WindowManager.LayoutParams.FLAG_FULLSCREEN);
     *                那么顶栏会变成透明
     *
     * */

    /*
    *View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        稳定布局，主要是在全屏和非全屏切换时，布局不要有大的变化。(就是界面上的Button不会出现顶栏消失而网上弹一下的效果)
        一般和View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN、View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION搭配使用。
        同时，android:fitsSystemWindows要设置为true。


    *private void toggle() {
        if (mVisible) {
            getWindow().getDecorView().setSystemUiVisibility(
                      View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }

        mVisible = !mVisible;
    }

    <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:fitsSystemWindows="true"
        android:background="@drawable/bg"
        >
    </FrameLayout>
    *
    *
    * */

}
