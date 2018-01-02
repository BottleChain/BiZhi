package com.yeyue.bizhi.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.jess.arms.di.component.AppComponent;
import com.yeyue.library.base.YePageFragment;

import java.util.ArrayList;

/**
 * Created by li.xiao on 2017-11-2.
 */

public class CategoryFragment extends YePageFragment{
    private String[] titles = {"福利","all","Android","iOS","休息视频","前端","拓展资源","瞎推荐","App"};
    @Override
    public String[] getTitlse() {
        return titles;
    }

    @Override
    public ArrayList<Fragment> getFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (String title:titles){
            fragments.add(HomeFragment.newInstance(1));
        }
        return fragments;
    }

    @Override
    public void initData() {
        super.initData();
        setTitle("标签");
    }

    @Override
    public void intToolBar() {

    }

    @Override
    public int getToolMenuId() {
        return 0;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void initView(View mRootView) {

    }

    @Override
    public void onInitView() {

    }

    @Override
    public boolean isToolbar() {
        return true;
    }
}
