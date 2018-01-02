package com.yeyue.library.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeyue.library.adapter.YeCommonListAdapter;
import com.yeyue.library.data.BaseItem;
import com.yeyue.library.utils.YeSettingManage;

import java.util.ArrayList;
import java.util.List;

/**
  *@describe 通用设置界面
  *@author li.xiao
  *@time 2017-12-26 14:10
  */
public abstract class YeSettingFragment extends YeRecyFragment{

    public abstract int inflateMenu();
    /**
     * 获取默认设置，例如非Wifi下不加载图片，清除缓存等等
     * @return
     */
    protected List<BaseItem> getDefaultItems(){
        YeSettingManage utils = new YeSettingManage(getActivity());
        List<BaseItem> baseItems = utils.readMenu(inflateMenu());
        return baseItems;
    }


    @Override
    public void onDataRefresh() {

    }

    @Override
    public void onDataLoadMore() {

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
    public BaseQuickAdapter<BaseItem, BaseViewHolder> getAdapter() {
        BaseQuickAdapter adapter =  new YeCommonListAdapter(new ArrayList());
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(),1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void initData() {
        refreshUI(getDefaultItems());
    }

    @Override
    public View getLoadView() {
        return null;
    }

    @Override
    public boolean isToolbar() {
        return false;
    }

    @Override
    public void killMyself() {

    }
}
