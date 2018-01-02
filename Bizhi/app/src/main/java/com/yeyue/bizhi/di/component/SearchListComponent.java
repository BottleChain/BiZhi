package com.yeyue.bizhi.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yeyue.bizhi.di.module.SearchListModule;

import com.yeyue.bizhi.ui.fragment.SearchListFragment;

@ActivityScope
@Component(modules = SearchListModule.class, dependencies = AppComponent.class)
public interface SearchListComponent {
    void inject(SearchListFragment fragment);
}