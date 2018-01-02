package com.yeyue.bizhi.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.contract.FollowContract;
import com.yeyue.bizhi.di.component.DaggerFollowComponent;
import com.yeyue.bizhi.di.module.FollowModule;
import com.yeyue.bizhi.presenter.FollowPresenter;
import com.yeyue.bizhi.ui.adapter.CommonListAdapter;
import com.yeyue.library.base.YeRecyActivity;
import com.yeyue.library.data.BaseItem;

import java.util.ArrayList;

/**
  *@describe 关注列表
  *@author li.xiao
  *@time 2017-12-28 14:45
  */
public class FollowActivity extends YeRecyActivity<BaseItem, FollowPresenter> implements FollowContract.View<BaseItem> {


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerFollowComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .followModule(new FollowModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.getFollow(true);
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
        mPresenter.getFollow(true);
    }

    @Override
    public void onDataLoadMore() {
        mPresenter.getFollow(false);
    }

    @Override
    public BaseQuickAdapter<BaseItem, BaseViewHolder> getAdapter() {
        return new CommonListAdapter(new ArrayList());
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean isToolbar() {
        return false;
    }
}
