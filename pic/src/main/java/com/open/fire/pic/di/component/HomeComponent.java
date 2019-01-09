package com.open.fire.pic.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.open.fire.pic.mvp.contract.HomeContract;
import com.open.fire.pic.di.module.HomeModule;
import com.open.fire.pic.mvp.ui.activity.HomeActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-11-23
 */
@ActivityScope
@Component(modules = HomeModule.class ,dependencies = AppComponent.class)
public interface HomeComponent {


    /*使用dependencies  需要吧AppComponent的实例 作为builder 放进去*/

//    void inject(HomeActivity homeActivity);
   /* @Component.Builder
    interface Builder {
        @BindsInstance
        HomeComponent.Builder view(HomeContract.View view);
        HomeComponent.Builder appComponent(AppComponent appComponent);
        HomeComponent build();
    }*/
}
