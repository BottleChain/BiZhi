package com.yeyue.bizhi.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yeyue.bizhi.di.module.FollowModule;

import com.yeyue.bizhi.ui.activity.FollowActivity;

@ActivityScope
@Component(modules = FollowModule.class, dependencies = AppComponent.class)
public interface FollowComponent {
    void inject(FollowActivity activity);
}