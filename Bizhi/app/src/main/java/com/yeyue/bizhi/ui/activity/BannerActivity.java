package com.yeyue.bizhi.ui.activity;

import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.constant.Constant;
import com.yeyue.bizhi.ui.fragment.HomeFragment;
import com.yeyue.library.base.YePageActivity;

import java.util.ArrayList;

/**
 * Created by li.xiao on 2017-12-4.
 */

public class BannerActivity extends YePageActivity {
    private String[] titles = {"0","1","2","3","4","5","6","7","8","9","10"};
    @Override
    public String[] getTitlse() {
        return titles;
    }

    @Override
    public ArrayList<Fragment> getFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i=0;i<titles.length;i++){
            fragments.add(HomeFragment.newInstance(Constant.HOME_PAGE_CATEGORY));
        }
        return fragments;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public boolean isToolbar() {
        return false;
    }

    @Override
    public void killMyself() {

    }

    @Override
    public View getLoadView() {
        return null;
    }
}
