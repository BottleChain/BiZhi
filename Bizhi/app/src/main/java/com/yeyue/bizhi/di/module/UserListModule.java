package com.yeyue.bizhi.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.yeyue.bizhi.contract.UserListContract;
import com.yeyue.bizhi.model.UserListModel;


@Module
public class UserListModule {
    private UserListContract.View view;

    /**
     * 构建UserListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserListModule(UserListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserListContract.View provideUserListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    UserListContract.Model provideUserListModel(UserListModel model) {
        return model;
    }
}