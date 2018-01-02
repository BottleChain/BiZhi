package com.yeyue.bizhi.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.R;
import com.yeyue.bizhi.contract.SkinContract;
import com.yeyue.bizhi.di.component.DaggerSkinComponent;
import com.yeyue.bizhi.di.module.SkinModule;
import com.yeyue.bizhi.presenter.SkinPresenter;
import com.yeyue.library.base.YeLiSkinActivity;
import com.yeyue.library.constant.YeSkinConstant;
import com.yeyue.library.data.BaseItem;
import com.yeyue.library.data.YeSkinItem;

import java.util.ArrayList;
import java.util.List;

/**
  *@describe 皮肤管理器
  *@author li.xiao
  *@time 2017-12-20 17:25
  */
public class SkinActivity extends YeLiSkinActivity<BaseItem, SkinPresenter> implements SkinContract.View<BaseItem> {


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerSkinComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .skinModule(new SkinModule(this))
                .build()
                .inject(this);
    }

    @Override
    public List<YeSkinItem> getLoaclSkin() {
        String[] titles = {"官方白","官方黑"};
        int[] res = {R.color.white,R.color.black};
        String[] paths = {"","night.skin","dark.skin"};
        List<YeSkinItem> baseItems = new ArrayList<YeSkinItem>();
        for (int i=0;i<titles.length;i++){
            YeSkinItem item = new YeSkinItem();
            item.setItemtype(YeSkinConstant.YE_ITEM_SKIN_DEFAULT);
            item.setTitle(titles[i]);
            item.setCoverRes(res[i]);
            item.setResourceName(paths[i]);
            baseItems.add(item);
        }
        return baseItems;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitle("换肤");
        mPresenter.getSkin(true);
    }

    @Override
    public boolean enableRefresh() {
        return false;
    }

    @Override
    public boolean enableMore() {
        return false;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

    }

    @Override
    public void onDataRefresh() {
        mPresenter.getSkin(true);
    }

    @Override
    public void onDataLoadMore() {
        mPresenter.getSkin(false);
    }


    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public boolean isToolbar() {
        return true;
    }
}
