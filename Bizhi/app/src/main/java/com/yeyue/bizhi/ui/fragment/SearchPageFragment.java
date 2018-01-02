package com.yeyue.bizhi.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.constant.Constant;
import com.yeyue.library.base.YePageFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/4 0004.
 */

public class SearchPageFragment extends YePageFragment {
    private String[] titles = {"壁纸", "竖屏壁纸", "动态壁纸","锁屏"};
    private int[] titleInts = { Constant.SEARCH_PAGE_BIZHI, Constant.SEARCH_PAGE_VERTICAL, Constant.SEARCH_PAGE_VIDEO, Constant.SEARCH_PAGE_LOCK};
    private String query;

    private ArrayList<Fragment> fragments;
    public static SearchPageFragment newInstance(String query) {
        SearchPageFragment fragment = new SearchPageFragment();
        fragment.query = query;
        return fragment;
    }

    @Override
    public String[] getTitlse() {
        return titles;
    }

    public void refreshSearch(String query){
        if(fragments!=null && fragments.size()>0){
            for (Fragment fragment:fragments){
                if(fragment!=null && fragment instanceof SearchListFragment){
                    SearchListFragment searchListFragment = (SearchListFragment) fragment;
                    searchListFragment.refreshSearch(query);
                }
            }
        }
    }
    @Override
    public ArrayList<Fragment> getFragment() {
        fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(SearchListFragment.newInstance(titleInts[i],query));
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

}
