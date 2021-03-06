package com.yeyue.bizhi.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yeyue.bizhi.di.module.DetailModule;

import com.yeyue.bizhi.ui.fragment.DetailFragment;

@ActivityScope
@Component(modules = DetailModule.class, dependencies = AppComponent.class)
public interface DetailComponent {
    void inject(DetailFragment fragment);
}