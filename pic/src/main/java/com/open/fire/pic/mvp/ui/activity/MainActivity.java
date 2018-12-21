package com.open.fire.pic.mvp.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jess.arms.utils.ArmsUtils;
import com.open.fire.pic.Bean;
import com.open.fire.pic.R;
import com.open.fire.pic.di.Baby;
import com.open.fire.pic.di.component.DaggerMainComponent;
import com.open.fire.pic.mvp.model.entity.User;
import com.open.fire.utils_package.base.DeviceUtils;
import com.open.fire.utils_package.base.LogTimber;
import com.open.fire.utils_package.qqutils.ApiGsonParser;
import com.open.fire.utils_package.widget.image_utils.FastBlur;
import com.open.fire.utils_package.widget.image_utils.FrescoImage;
import com.open.fire.utils_package.widget.image_utils.FrescoUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Bitmap mBitmap;
    private String url = "http://s2.dgtle.com/forum/201812/04/165923rukfbk3v2tv33ue3.jpg?imageView2/2/w/960/q/100";

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
        ButterKnife.bind(this);

        findViewById(R.id.iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArmsUtils.snackbarTextWithLong("哈哈哈");
            }
        });

//        iv.setImageBySize("http://s2.dgtle.com/forum/201812/04/165923rukfbk3v2tv33ue3.jpg?imageView2/2/w/960/q/100", (int)DeviceUtils.getScreenWidth(this), -1);
//        iv.setImageResource(R.drawable.icon_qq);
        LogTimber.printDebugLogs("Mian", "哈哈");

        FrescoUtil.loadUrlImage(url, true, new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                mBitmap = bitmap;
                int height = mBitmap.getHeight();
                int width = mBitmap.getWidth();

                int shouldHeight = ((int) DeviceUtils.getScreenWidth(MainActivity.this)) * height / width;
                ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
                layoutParams.height = shouldHeight;
                layoutParams.width = (int) DeviceUtils.getScreenWidth(MainActivity.this);
                iv.setLayoutParams(layoutParams);
                iv.setImageBitmap(FastBlur.doBlur(mBitmap, 15, true));
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
            }
        });

        init();
    }

    private void init() {
        Map jsonObject = new HashMap();

        jsonObject.put("a", null);
        jsonObject.put("b", "");
        jsonObject.put("c", "null");
        Gson g = new GsonBuilder().serializeNulls().create();

        String jsonObject1 = g.toJson(jsonObject);

        LogTimber.printDebugLogs("q", jsonObject.toString());

        Bean v = ApiGsonParser.fromJSONObject(jsonObject1, Bean.class);


    }


}
