package com.open.fire.pic.mvp.ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogTimber;
import com.jess.arms.widget.StatusBarUtil;
import com.open.fire.pic.R;
import com.open.fire.pic.di.Baby;
import com.open.fire.pic.di.component.DaggerMainComponent;
import com.open.fire.pic.mvp.model.entity.User;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    List<User> mList;
    @Inject
    Baby mBaby;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //需要在setcontentview 之前
        /*if (StatusBarUtil.setStatusBarLightMode(this, true)) {
            StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, android.R.color.transparent));
        }*/
        ArmsUtils.statuInScreen(this);
        setContentView(R.layout.activity_main);
        DaggerMainComponent
                .builder()
                .appComponent(ArmsUtils.obtainAppComponentFromContext(MainActivity.this))
                .build()

                .inject(this);

        findViewById(R.id.iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArmsUtils.snackbarTextWithLong("哈哈哈");
            }
        });

        LogTimber.printDebugLogs("Mian","哈哈");
    }






}
