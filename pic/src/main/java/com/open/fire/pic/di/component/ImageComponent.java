package com.open.fire.pic.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.open.fire.pic.di.module.ImageModule;
import com.open.fire.pic.mvp.ui.activity.DaaggerActivity;

import dagger.Component;
import dagger.Module;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-29
 */
@ActivityScope
@Component(modules = ImageModule.class ,dependencies = AppComponent.class)
public interface ImageComponent {
    void inject(DaaggerActivity daaggerActivity);
}
