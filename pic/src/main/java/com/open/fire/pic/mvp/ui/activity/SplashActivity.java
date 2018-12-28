package com.open.fire.pic.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.ContextProvider;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.rxtools.rx.RxLifecycleUtils;
import com.jess.arms.utils.ArmsUtils;
import com.open.fire.pic.R;
import com.open.fire.utils_package.base.DeviceUtils;
import com.open.fire.utils_package.base.StatusBarUtil;
import com.open.fire.utils_package.base.StatusBarUtilsPlus;
import com.open.fire.utils_package.widget.explosion_field.ExplosionField;
import com.open.fire.utils_package.widget.image_utils.BitmapUtils;
import com.open.fire.utils_package.widget.image_utils.FastBlur;
import com.open.fire.utils_package.widget.image_utils.FrescoImage;
import com.open.fire.utils_package.widget.image_utils.FrescoUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * class description
 *  I/ActivityManager: Displayed com.zhihu.android/.app.ui.activity.MainActivity: +1s412ms (total +1s978ms)
 *  这个可以作为启动时间参考
 * @author shankshu
 * Create on 2018-12-27
 */
public class SplashActivity extends BaseActivity {


    @BindView(R.id.iv_splash)
    FrescoImage ivSplash;
    @BindView(R.id.main_content)
    RelativeLayout mainContent;

    private String url = "http://s2.dgtle.com/forum/201812/26/134442c3qqqy8jkbs0qq75.jpg?imageView2/2/w/960/q/100";
    private String url2 = "http://s2.dgtle.com/forum/201812/26/134441c1qtxyj3f6hvbbq6.jpg?imageView2/2/w/960/q/100";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        if (StatusBarUtil.setStatusBarLightMode(this, true)) {
            StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, android.R.color.transparent

            ));
        }
        StatusBarUtilsPlus.setFullScreen(this, true);
        return R.layout.activity_splash;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        int i = new java.util.Random().nextInt(3);
        if (i == 0 || i == 2){
            getBackGroundImage(url);
        }else {
            getBackGroundImage(url2);
        }
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Long, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(Long aLong) throws Exception {
                        ExplosionField explosionField = ExplosionField.attach2Window(SplashActivity.this);
                        explosionField.explode(ivSplash);
                        return Observable.timer(1200, TimeUnit.MILLISECONDS);
                    }
                })
                /*.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ExplosionField explosionField = ExplosionField.attach2Window(SplashActivity.this);
                        explosionField.explode(ivSplash);
                    }
                })*/
                .compose(RxLifecycleUtils.bindToLifecycle(SplashActivity.this))
//                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        ArmsUtils.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        SplashActivity.this.finish();
                    }
                });

    }

    public void getBackGroundImage(String url) {
        if (ivSplash == null) {
            ivSplash = findViewById(R.id.iv_splash);
        }
        ivSplash.setImageByRealRatio(url);
        ivSplash.setRectBorderColorAndWidth(ArmsUtils.getColor(ContextProvider.get(), android.R.color.white), DeviceUtils.dpToPixel(2));

        final Uri uri = Uri.parse(url);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<Boolean> inDiskCacheSource = imagePipeline.isInDiskCache(uri);

        DataSubscriber<Boolean> subscriber = new BaseDataSubscriber<Boolean>() {
            @Override
            protected void onNewResultImpl(DataSource<Boolean> dataSource) {
                // 异常情况全部显示默认Launch页面
                if (!dataSource.isFinished()) {
                    return;
                }
                if (dataSource.getResult() == null || !dataSource.getResult()) {
                    return;
                }
                FrescoUtil.loadUrlImage(url, true, new BaseBitmapDataSubscriber() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    protected void onNewResultImpl(Bitmap bitmap) {
                        Bitmap blurBitmap = FastBlur.doBlur(bitmap, 15, false);
                        int blurBitmapWidth = blurBitmap.getWidth();
                        int blurBitmapHeight = blurBitmap.getHeight();

                        if (DeviceUtils.isPortrait(com.open.fire.utils_package.base.ContextProvider.get())){
                            int shouldWidth = (int) ((DeviceUtils.getScreenWidth(ContextProvider.get()) * blurBitmapHeight) / DeviceUtils.getScreenHeight(com.open.fire.utils_package.base.ContextProvider.get()));
                            int x = (blurBitmapWidth - shouldWidth) / 2;

                            Bitmap clipBitmap = BitmapUtils.clip(blurBitmap,x,0,shouldWidth,blurBitmapHeight);
                            mainContent.setBackground(new BitmapDrawable(clipBitmap));
                        }
                        if (DeviceUtils.isLandscape(com.open.fire.utils_package.base.ContextProvider.get())){
                            int shouldHeight = (int) ((DeviceUtils.getScreenHeight(ContextProvider.get()) * blurBitmapWidth) / DeviceUtils.getScreenWidth(com.open.fire.utils_package.base.ContextProvider.get()));
                            int y = (blurBitmapHeight - shouldHeight) / 2;
                            Bitmap clipBitmap = BitmapUtils.clip(blurBitmap,0,y,blurBitmapWidth,shouldHeight);
                            mainContent.setBackground(new BitmapDrawable(clipBitmap));
                        }
                    }

                    @Override
                    protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                    }
                });


            }


            @Override
            protected void onFailureImpl(DataSource<Boolean> dataSource) {
            }
        };
        inDiskCacheSource.subscribe(subscriber, UiThreadImmediateExecutorService.getInstance());

    }

    public  void loadIntoDiskCache(String url) {
        if (TextUtils.isEmpty(url)) return;
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .build();
//        imagePipeline.prefetchToBitmapCache(imageRequest, null);
        imagePipeline.prefetchToDiskCache(imageRequest, null);

    }




}
