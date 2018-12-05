package com.open.fire.pic.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.open.fire.pic.mvp.ui.activity.MainActivity;
import com.open.fire.pic.di.module.MainModule;

import dagger.Component;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-11-23
 */
@ActivityScope
@Component(modules = MainModule.class ,dependencies = AppComponent.class)
public interface MainComponent {


    /*使用dependencies  需要吧AppComponent的实例 作为builder 放进去*/

    void inject(MainActivity mainActivity);
    /*@Component.Builder
    interface Builder {
//        @BindsInstance
//        MainComponent.Builder view(MainContract.View view);
//        MainComponent.Builder appComponent(AppComponent appComponent);
        MainComponent build();
    }*/
}
