package com.yeyue.bizhi.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.yeyue.bizhi.contract.SkinContract;
import com.yeyue.bizhi.model.YeSkinModel;


@Module
public class SkinModule {
    private SkinContract.View view;

    /**
     * 构建YeSkinModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SkinModule(SkinContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SkinContract.View provideYeSkinView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SkinContract.Model provideYeSkinModel(YeSkinModel model) {
        return model;
    }
}