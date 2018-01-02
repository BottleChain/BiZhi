package com.yeyue.bizhi.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yeyue.bizhi.di.module.HomeModule;
import com.yeyue.bizhi.ui.activity.AlbumDetailActivity;
import com.yeyue.bizhi.ui.fragment.HomeFragment;
import com.yeyue.bizhi.ui.fragment.VerticalFragment;

import dagger.Component;

@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment fragment);
    void inject(VerticalFragment fragment);
    void inject(AlbumDetailActivity activity);
}