package com.open.fire.pic.mvp.ui.activity;

import android.app.AlarmManager;
import android.app.Application;
import android.content.ContentProvider;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.google.gson.Gson;
import com.jess.arms.base.ContextProvider;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.open.fire.pic.R;
import com.open.fire.pic.alarm.AlarmManagerUtil;
import com.open.fire.pic.alarm.AlarmWorker;
import com.open.fire.pic.alarm.LoongggAlarmReceiver;
import com.open.fire.pic.app.CallUtils;
import com.open.fire.pic.di.component.DaggerImageComponent;
import com.open.fire.pic.di.module.ImageModule;
import com.open.fire.pic.service.AlarmWorkService;
import com.open.fire.utils_package.base.StatusBarUtil;
import com.open.fire.utils_package.qqutils.stage.PreferenceHelper;
import com.open.fire.utils_package.widget.image_utils.BitmapUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tbruyelle.rxpermissions2.RxPermissionsFragment;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-29
 */
public class DaaggerActivity extends AppCompatActivity {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;
    @Inject
    Cache<String, Object> mObjectCache;
    @Inject
    View mView;
    Bitmap m2Bitmap, m3Bitmap, m4Bitmap, m5Bitmap;
    @BindView(R.id.tv_config)
    TextView tvConfig;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_start)
    TextView tvStart;

    public static HashMap<String,String> map;

    public static AtomicBoolean isStarted = new AtomicBoolean(false);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        ButterKnife.bind(this);
        StatusBarUtil.setStatusBarLightMode(DaaggerActivity.this, true);

        DaggerImageComponent.builder().appComponent(ArmsUtils.obtainAppComponentFromContext(DaaggerActivity.this)).imageModule(new ImageModule(this.getApplication())).build().inject(this);
//        DaggerImageComponent.builder().build().inject(this);

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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("当前拨号前缀     \n" + PreferenceHelper.getInstance().getString("pre_num") + "\n\n");
        stringBuilder.append("当前设定时间1    \n" + PreferenceHelper.getInstance().getString("1_time") + "\n\n");
        stringBuilder.append("当前被呼叫用户1  \n" + PreferenceHelper.getInstance().getString("1_num") + "\n\n");
        stringBuilder.append("当前设定时间2    \n" + PreferenceHelper.getInstance().getString("2_time") + "\n\n");
        stringBuilder.append("当前被呼叫用户2  \n" + PreferenceHelper.getInstance().getString("2_num") + "\n\n");
        stringBuilder.append("当前设定时间3    \n" + PreferenceHelper.getInstance().getString("3_time") + "\n\n");
        stringBuilder.append("当前被呼叫用户3  \n" + PreferenceHelper.getInstance().getString("3_num") + "\n\n");

        tvMessage.setText(stringBuilder);

        String status =  PreferenceHelper.getInstance().getString("conf");
        if (TextUtils.equals("end",status)) {
            tvStart.setText("开启");
            isStarted.set(false);
        }
        if (TextUtils.equals("start",status)) {
            tvStart.setText("运行中。。");
            isStarted.set(true);
        }

    }


    @OnClick(R.id.tv_config)
    public void onViewClicked() {

        ArmsUtils.startActivity(new Intent(DaaggerActivity.this, ConfigActivity.class));
    }


    @OnClick(R.id.tv_start)
    public void onStartViewClicked() {

        if (isStarted.get()) {
            AlarmWorker.end();

            tvStart.setText("开启");
            isStarted.set(false);
        } else {
            AlarmWorker.start();

            tvStart.setText("运行中。。");
            isStarted.set(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
