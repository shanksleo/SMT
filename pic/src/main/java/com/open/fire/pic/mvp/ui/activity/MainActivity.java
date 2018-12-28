package com.open.fire.pic.mvp.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewStub;

import com.jess.arms.utils.ArmsUtils;
import com.open.fire.pic.R;
import com.open.fire.pic.di.Baby;
import com.open.fire.pic.di.component.DaggerMainComponent;
import com.open.fire.pic.mvp.model.entity.User;
import com.open.fire.pic.mvp.ui.CustomView;
import com.open.fire.utils_package.base.StatusBarUtil;
import com.open.fire.utils_package.widget.image_utils.FrescoImage;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    List<User> mList;
    @Inject
    Baby mBaby;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //需要在setcontentview 之前
        DaggerMainComponent
                .builder()
                .appComponent(ArmsUtils.obtainAppComponentFromContext(MainActivity.this))
                .build()
                .inject(this);
        if (StatusBarUtil.setStatusBarLightMode(this, true)) {
            StatusBarUtil.transparencyBar(this);
        }
        setContentView(R.layout.activity_main);

    }


}
