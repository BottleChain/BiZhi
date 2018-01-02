package com.yeyue.bizhi.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.R;
import com.yeyue.bizhi.constant.Constant;
import com.yeyue.bizhi.utils.ActivityUtils;
import com.yeyue.library.base.YePageFragment;

import java.util.ArrayList;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/4 0004.
 */

public class HomePageFragment extends YePageFragment {
    private String[] titles = {"推荐", "分类", "最新", "专辑"};
    private int[] titleInts = {Constant.HOME_PAGE_RECOMMEND, Constant.HOME_PAGE_CATEGORY, Constant.HOME_PAGE_NEW, Constant.HOME_PAGE_ALBUM};
    private boolean hasChange;
    @Override
    public String[] getTitlse() {
        return titles;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public ArrayList<Fragment> getFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            if (i == 3) {
                fragments.add(BannerFragment.newInstance(Constant.BANNER_HOME_ALBUM));
            } else {
                fragments.add(HomeFragment.newInstance(titleInts[i]));
            }
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

    @OnClick(R.id.ivSearch)
    public void onViewClicked() {
        ActivityUtils.openSearchActivity(getActivity());
    }
}
