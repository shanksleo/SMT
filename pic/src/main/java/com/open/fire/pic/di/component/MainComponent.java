package com.open.fire.pic.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.open.fire.pic.mvp.contract.MainContract;
import com.open.fire.pic.ui.activity.MainActivity;
import com.open.fire.pic.di.module.MainModule;

import dagger.BindsInstance;
import dagger.Component;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-11-23
 */
@ActivityScope
@Component(modules = MainModule.class , dependencies = AppComponent.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        MainComponent.Builder view(MainContract.View view);
        MainComponent.Builder appComponent(AppComponent appComponent);
        MainComponent build();
    }
}
