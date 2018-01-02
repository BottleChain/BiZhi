package com.yeyue.bizhi.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.R;
import com.yeyue.bizhi.ui.fragment.HomePageFragment;
import com.yeyue.bizhi.ui.fragment.MineFragment;
import com.yeyue.bizhi.ui.fragment.VerticalPageFragment;
import com.yeyue.bizhi.ui.fragment.VideoPageFragment;
import com.yeyue.library.base.YeBottomBarActivity;

/**
 * Created by li.xiao on 2017-9-8.
 */

/**
 * @author li.xiao
 * @describe 应用主页
 * @time 2017-10-31 14:49
 */
public class MainActivity extends YeBottomBarActivity {

    @Override
    public Fragment getFragment(int tag) {
        Fragment fragment = null;
        switch (tag) {
            case R.id.tab_home:
                fragment = new HomePageFragment();
                break;
            case R.id.tab_vertical:
                fragment = new VerticalPageFragment();
                break;
            case R.id.tab_video:
                fragment = new VideoPageFragment();
                break;
            case R.id.tab_mine:
                fragment = new MineFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int inflateMenu() {
        return R.menu.main_tabs;
    }

    @Override
    public int defaultItem() {
        return R.id.tab_home;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initData(Bundle bundle) {

    }


}
