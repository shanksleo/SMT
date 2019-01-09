package com.open.fire.pic.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.integration.ConfigModule;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.iocanary.IOCanaryPlugin;
import com.tencent.matrix.iocanary.config.IOConfig;
import com.tencent.matrix.resource.ResourcePlugin;
import com.tencent.matrix.resource.config.ResourceConfig;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;
import com.tencent.matrix.util.MatrixLog;
import com.tencent.mrs.plugin.IDynamicConfig;
import com.tencent.sqlitelint.SQLiteLintPlugin;
import com.tencent.sqlitelint.config.SQLiteLintConfig;

import java.util.List;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-03
 */
public class PicConfig implements ConfigModule {
    private static final String TAG = PicConfig.class.getSimpleName();

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {



    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        lifecycles.add(new PicAppLifecycles());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {

    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {

    }




}
