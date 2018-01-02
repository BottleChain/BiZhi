package com.yeyue.bizhi.app;

import com.jess.arms.base.BaseApplication;

/**
 * Created by li.xiao on 2017-9-22.
 */

public class AppContext extends BaseApplication{
    private static AppContext appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

    public static AppContext getInstance(){
        return appContext;
    }
}
