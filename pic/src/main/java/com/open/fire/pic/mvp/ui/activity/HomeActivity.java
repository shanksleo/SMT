package com.open.fire.pic.mvp.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewStub;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.open.fire.pic.R;
import com.open.fire.pic.mvp.contract.HomeContract;
import com.open.fire.pic.mvp.model.entity.User;
import com.open.fire.pic.mvp.ui.CustomView;
import com.open.fire.utils_package.base.StatusBarUtil;
import com.open.fire.utils_package.widget.image_utils.FrescoImage;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class HomeActivity<HomePresenter> extends BaseActivity implements HomeContract.View {
    @Inject
    List<User> mList;


    @BindView(R.id.iv)
    FrescoImage iv;

    ViewStub mViewStub;
    CustomView mCustomView;
    /*
    *
    * if (mCustomView == null) {
                    try {
                        mCustomView = (CustomView) ((ViewStub) findViewById(R.id.view_stub)).inflate();
//                        findViewById(R.id.view_stub).setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                    }
                }
    * */

    private Bitmap mBitmap;



    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
//        DaggerHomeComponent.builder().appComponent(appComponent).view(HomeActivity.this).inject(this).build();

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        if (StatusBarUtil.setStatusBarLightMode(this, true)) {
            StatusBarUtil.transparencyBar(this);
        }
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public RxPermissions getRxPermissions() {
        return null;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
