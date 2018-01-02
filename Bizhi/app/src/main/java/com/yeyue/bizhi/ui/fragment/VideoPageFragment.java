package com.yeyue.bizhi.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.constant.Constant;
import com.yeyue.library.base.YePageFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/4 0004.
 */

public class VideoPageFragment extends YePageFragment{
    private String[] titles = {"推荐","娱乐","最新","热门","分类"};
    private int[] titleInts = {Constant.VIDEO_PAGE_RECOMMEND,Constant.VIDEO_PAGE_YULE,Constant.VIDEO_PAGE_NEW,Constant.VIDEO_PAGE_HOT,Constant.VIDEO_PAGE_CATEGORY};
    @Override
    public String[] getTitlse() {
        return titles;
    }

    @Override
    public ArrayList<Fragment> getFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i=0;i<titles.length;i++){
            fragments.add(HomeFragment.newInstance(titleInts[i]));
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
