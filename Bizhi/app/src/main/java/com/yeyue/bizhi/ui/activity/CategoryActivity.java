package com.yeyue.bizhi.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.constant.Constant;
import com.yeyue.bizhi.entity.VerCategory;
import com.yeyue.bizhi.entity.VideoCategory;
import com.yeyue.bizhi.entity.WalCategory;
import com.yeyue.bizhi.ui.fragment.HomeFragment;
import com.yeyue.library.base.YePageActivity;

import java.util.ArrayList;

/**
 * Created by li.xiao on 2017-12-7.
 */

public class CategoryActivity extends YePageActivity{
    private String[] titles = {"热门","最新","专辑"};
    private String[] verTitles = {"热门","最新"};
    private WalCategory.CategoryBean categoryBean;
    private VerCategory verCategory;
    private VideoCategory videoCategory;
    private String cId;
    @Override
    public String[] getTitlse() {
        if(verCategory!=null){
            return verTitles;
        }
        return titles;
    }

    @Override
    public ArrayList<Fragment> getFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        if(verCategory!=null){
            fragments.add(HomeFragment.newInstance(Constant.VERTICAL_CATEGORY_HOT,cId));
            fragments.add(HomeFragment.newInstance(Constant.VERTICAL_CATEGORY_NEW,cId));
        }else if(categoryBean!=null){
            fragments.add(HomeFragment.newInstance(Constant.WALLPAPER_CATEGORY_HOT,cId));
            fragments.add(HomeFragment.newInstance(Constant.WALLPAPER_CATEGORY_NEW,cId));
            fragments.add(HomeFragment.newInstance(Constant.WALLPAPER_CATEGORY_ALBUM,cId));
        }else if(videoCategory!=null){
            fragments.add(HomeFragment.newInstance(Constant.VIDEO_CATEGORY_HOT,cId));
            fragments.add(HomeFragment.newInstance(Constant.VIDEO_CATEGORY_NEW,cId));
        }
        return fragments;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if(getIntent()!=null){
            if(getIntent().hasExtra(Constant.CATEGORY_ID)){
                categoryBean = (WalCategory.CategoryBean) getIntent().getSerializableExtra(Constant.CATEGORY_ID);
                if(categoryBean!=null){
                    cId = categoryBean.getId();
                    setTitle(categoryBean.getName()+"");
                }
            }
            if(getIntent().hasExtra(Constant.VERTICAL_CATEGORY_ID)){
                verCategory = (VerCategory) getIntent().getSerializableExtra(Constant.VERTICAL_CATEGORY_ID);
                if(verCategory!=null){
                    cId = verCategory.getId();
                    setTitle(verCategory.getName()+"");
                }
            }
            if(getIntent().hasExtra(Constant.VIDEO_CATEGORY_ID)){
                videoCategory = (VideoCategory) getIntent().getSerializableExtra(Constant.VIDEO_CATEGORY_ID);
                if(videoCategory!=null){
                    cId = videoCategory.getId();
                    setTitle(videoCategory.getName()+"");
                }
            }
        }
        super.initData(savedInstanceState);
    }

    @Override
    public boolean isToolbar() {
        return true;
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
