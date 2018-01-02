package com.yeyue.bizhi.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yeyue.bizhi.di.module.SkinModule;
import com.yeyue.bizhi.ui.activity.SkinActivity;

import dagger.Component;

@ActivityScope
@Component(modules = SkinModule.class, dependencies = AppComponent.class)
public interface SkinComponent {
    void inject(SkinActivity activity);
}