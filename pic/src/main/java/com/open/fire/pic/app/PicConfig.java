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
        Fresco.initialize(context);

        Matrix.Builder matrixBuilder = new Matrix.Builder((Application) context.getApplicationContext()); // build matrix
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



    public class DynamicConfigImplDemo implements IDynamicConfig {
        public DynamicConfigImplDemo() {}

        public boolean isFPSEnable() { return true;}
        public boolean isTraceEnable() { return true; }
        public boolean isMatrixEnable() { return true; }
        public boolean isDumpHprof() {  return false;}

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
