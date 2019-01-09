package com.open.fire.pic.di.module;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.open.fire.pic.R;
import com.open.fire.utils_package.qqutils.ApiGsonParser;

import dagger.Module;
import dagger.Provides;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-29
 */
@Module
public class ImageModule {
    Application mApplication;

    public ImageModule(Application application) {
        mApplication = application;
    }

    @Provides
    public View provideView(Application application){
        return LayoutInflater.from(application).inflate(R.layout.in,null);
    }


}
