package com.yeyue.bizhi.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.yeyue.bizhi.contract.SearchListContract;
import com.yeyue.bizhi.model.SearchListModel;


@Module
public class SearchListModule {
    private SearchListContract.View view;

    /**
     * 构建SearchListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SearchListModule(SearchListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SearchListContract.View provideSearchListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SearchListContract.Model provideSearchListModel(SearchListModel model) {
        return model;
    }
}