package com.yeyue.bizhi.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yeyue.bizhi.di.module.BannerModule;

import com.yeyue.bizhi.ui.fragment.BannerFragment;

@ActivityScope
@Component(modules = BannerModule.class, dependencies = AppComponent.class)
public interface BannerComponent {
    void inject(BannerFragment fragment);
}