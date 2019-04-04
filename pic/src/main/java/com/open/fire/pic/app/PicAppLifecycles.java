package com.open.fire.pic.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jess.arms.base.delegate.AppLifecycles;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.iocanary.IOCanaryPlugin;
import com.tencent.matrix.iocanary.config.IOConfig;
import com.tencent.mrs.plugin.IDynamicConfig;
import com.zk.qpm.manager.QPMManager;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.onAdaptListener;
import me.jessyan.autosize.utils.LogUtils;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-03
 */
public class PicAppLifecycles implements AppLifecycles {

    public static AtomicBoolean isFlowOpen = new AtomicBoolean(false);
    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        Fresco.initialize(application);
        initMetrix(application);
        initAutoSize(application);
        if (!isFlowOpen.get()) {
            QPMManager.getInstance().init(application);
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }

    private void initAutoSize(Application application) {
        AutoSizeConfig.getInstance()

                //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
                //如果没有这个需求建议不开启
                .setCustomFragment(true)

                //屏幕适配监听器
                .setOnAdaptListener(new onAdaptListener() {
                    @Override
                    public void onAdaptBefore(Object target, Activity activity) {
                        //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
                        //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
//                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
//                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
                    }

                    @Override
                    public void onAdaptAfter(Object target, Activity activity) {
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
                    }
                })

                //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
                .setLog(true)

                //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
                //AutoSize 会将屏幕总高度减去状态栏高度来做适配
                //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
//                .setUseDeviceSize(true)

                //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
                .setBaseOnWidth(true)

        //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
//                .setAutoAdaptStrategy(new AutoAdaptStrategy())
        ;
        //当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
        //在 Demo 中跳转的三方库中的 DefaultErrorActivity 就是在另外一个进程中, 所以要想适配这个 Activity 就需要调用 initCompatMultiProcess()
        AutoSize.initCompatMultiProcess(application);

    }

    private void initMetrix(Application application) {
        Matrix.Builder matrixBuilder = new Matrix.Builder(application); // build matrix
//        builder.patchListener(new TestPluginListener(this)); // add general pluginListener
        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo(); // dynamic config

        // init plugin
        IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .build());
        //add to matrix
        matrixBuilder.plugin(ioCanaryPlugin);

        //init matrix
        Matrix.init(matrixBuilder.build());

        // start plugin
        ioCanaryPlugin.start();


    }

    public class DynamicConfigImplDemo implements IDynamicConfig {
        public DynamicConfigImplDemo() {
        }

        public boolean isFPSEnable() {
            return true;
        }

        public boolean isTraceEnable() {
            return true;
        }

        public boolean isMatrixEnable() {
            return true;
        }

        public boolean isDumpHprof() {
            return false;
        }

        @Override
        public String get(String key, String defStr) {
            //hook to change default values
            return "";
        }

        @Override
        public int get(String key, int defInt) {
            //hook to change default values
            return 0;
        }

        @Override
        public long get(String key, long defLong) {
            //hook to change default values
            return 0;
        }

        @Override
        public boolean get(String key, boolean defBool) {
            //hook to change default values
            return false;
        }

        @Override
        public float get(String key, float defFloat) {
            //hook to change default values
            return 0;
        }
    }
}
