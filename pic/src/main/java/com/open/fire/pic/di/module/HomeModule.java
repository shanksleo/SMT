package com.open.fire.pic.di.module;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.IView;
import com.open.fire.pic.mvp.contract.HomeContract;
import com.open.fire.pic.mvp.model.entity.User;
import com.open.fire.utils_package.qqutils.ApiGsonParser;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-11-23
 */
@Module
public class HomeModule {
    private HomeContract.View mView;

    public HomeModule(HomeContract.View view) {
        mView = view;
    }

    @Provides
    Gson provideGson(){
        return ApiGsonParser.getParser();
    }

    @Provides
    HomeContract.View provideView() {
        return mView;
    }



}
