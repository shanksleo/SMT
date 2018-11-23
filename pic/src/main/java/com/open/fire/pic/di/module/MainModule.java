package com.open.fire.pic.di.module;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.open.fire.pic.mvp.contract.MainContract;
import com.open.fire.pic.mvp.model.entity.User;
import com.squareup.haha.perflib.Main;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-11-23
 */
@Module
public class MainModule {



    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(MainContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(MainContract.View view) {
        return new GridLayoutManager(view.getActivity(), 2);
    }

    @ActivityScope
    @Provides
    static List<User> provideUserList() {
        return new ArrayList<>();
    }


}
