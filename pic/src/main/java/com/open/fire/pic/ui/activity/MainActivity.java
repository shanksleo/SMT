package com.open.fire.pic.ui.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.component.AppComponent;
import com.open.fire.pic.R;
import com.open.fire.pic.di.component.DaggerMainComponent;
import com.open.fire.pic.mvp.contract.MainContract;
import com.open.fire.pic.mvp.presenter.MainPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerMainComponent
                .builder()
                .build()
                .inject(this);
    }



}
