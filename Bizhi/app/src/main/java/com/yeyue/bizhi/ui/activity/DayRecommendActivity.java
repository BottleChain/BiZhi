package com.yeyue.bizhi.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.constant.Constant;
import com.yeyue.bizhi.ui.fragment.BannerFragment;
import com.yeyue.library.base.YeFragmentActivity;

/**
  *@describe 每日推荐
  *@author li.xiao
  *@time 2017-12-6 17:04
  */
public class DayRecommendActivity extends YeFragmentActivity{
    @Override
    public Fragment getFragment() {
        return BannerFragment.newInstance(Constant.BANNER_DAY_RECOMMEND);
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initData(Bundle bundle) {
        super.initData(bundle);
        setTitle("精选");
    }

    @Override
    public boolean isToolbar() {
        return true;
    }
}
