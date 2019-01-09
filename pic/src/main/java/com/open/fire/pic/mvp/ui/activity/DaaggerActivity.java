package com.open.fire.pic.mvp.ui.activity;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;
import com.open.fire.pic.R;
import com.open.fire.pic.di.component.DaggerImageComponent;
import com.open.fire.pic.di.module.ImageModule;
import com.open.fire.utils_package.base.LogTimber;
import com.open.fire.utils_package.base.StatusBarUtil;
import com.open.fire.utils_package.widget.image_utils.BitmapUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-29
 */
public class DaaggerActivity  extends AppCompatActivity {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;
    @Inject
    Cache<String,Object> mObjectCache;
    @Inject
    View mView;
    Bitmap m2Bitmap,m3Bitmap,m4Bitmap,m5Bitmap;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        StatusBarUtil.setStatusBarLightMode(DaaggerActivity.this,true);

        DaggerImageComponent.builder().appComponent(ArmsUtils.obtainAppComponentFromContext(DaaggerActivity.this)).imageModule(new ImageModule(this.getApplication())).build().inject(this);
//        DaggerImageComponent.builder().build().inject(this);

        LogTimber.printDebugLogs("b",mApplication.getPackageName());
        mObjectCache.clear();
        mView.setVisibility(View.VISIBLE);

        m2Bitmap = BitmapUtils.getBitmap(R.drawable.icon_qq);
        m3Bitmap = BitmapUtils.getBitmap(R.drawable.icon_sina);
        m4Bitmap = BitmapUtils.getBitmap(R.drawable.icon_qzone);
//        m5Bitmap = BitmapUtils.getBitmap(R.drawable.icon_qq);
        init();
    }

    private void init() {
        DraweeHierarchy draweeHierarchy = GenericDraweeHierarchyBuilder.newInstance(DaaggerActivity.this.getResources())
                .build();


    }


}
