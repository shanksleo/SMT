package com.open.fire.pic.mvp.ui.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.open.fire.pic.R;
import com.open.fire.pic.di.Baby;
import com.open.fire.pic.di.component.DaggerMainComponent;
import com.open.fire.pic.mvp.contract.MainContract;
import com.open.fire.pic.mvp.model.entity.User;
import com.open.fire.pic.mvp.presenter.MainPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject
    List<User> mList;
    @Inject
    Baby mBaby;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    }






}
