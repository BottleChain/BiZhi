package com.yeyue.bizhi.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.R;
import com.yeyue.library.base.YePageFragment;

import java.util.ArrayList;

/**
  *@describe 竖屏页面
  *@author Administrator
  *@time 2017/11/4 0004 下午 9:31
  */
public class VerticalPageFragment extends YePageFragment{
    private String[] titles = {"热门","最新","分类"};
    @Override
    public String[] getTitlse() {
        return titles;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_vertical_page;
    }
    @Override
    public ArrayList<Fragment> getFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i=0;i<titles.length;i++){
            fragments.add(VerticalFragment.newInstance(titles[i]));
        }
        return fragments;
    }

    @Override
    public void intToolBar() {

    }

    @Override
    public int getToolMenuId() {
        return 0;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public void setData(Object o) {

    }

    @Override
    public void initView(View mRootView) {

    }

    @Override
    public void onInitView() {

    }

}
