package com.yeyue.bizhi.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yeyue.bizhi.di.module.UserListModule;

import com.yeyue.bizhi.ui.fragment.UserListFragment;

@ActivityScope
@Component(modules = UserListModule.class, dependencies = AppComponent.class)
public interface UserListComponent {
    void inject(UserListFragment fragment);
}