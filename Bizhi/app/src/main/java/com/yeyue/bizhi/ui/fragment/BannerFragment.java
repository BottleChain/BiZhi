package com.yeyue.bizhi.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.constant.Constant;
import com.yeyue.bizhi.contract.BannerContract;
import com.yeyue.bizhi.di.component.DaggerBannerComponent;
import com.yeyue.bizhi.di.module.BannerModule;
import com.yeyue.bizhi.entity.BizhiBean;
import com.yeyue.bizhi.entity.ComHeader;
import com.yeyue.bizhi.entity.DayRecommend;
import com.yeyue.bizhi.presenter.BannerPresenter;
import com.yeyue.bizhi.ui.adapter.CommonListAdapter;
import com.yeyue.library.base.YeBannerFragment;
import com.yeyue.library.data.BaseItem;

import java.util.ArrayList;


public class BannerFragment extends YeBannerFragment<BaseItem, BannerPresenter> implements
        BannerContract.View<BaseItem> {

    private int type;
    public static BannerFragment newInstance(int type) {
        BannerFragment fragment = new BannerFragment();
        fragment.type = type;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerBannerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .bannerModule(new BannerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData() {
        getBannerData(true);
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     *
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     */

    @Override
    public void setData(Object data) {

    }

    @Override
    public boolean enableRefresh() {
        return false;
    }

    @Override
    public boolean enableMore() {
        return true;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

    }

    @Override
    public boolean isToolbar() {
        return false;
    }


    @Override
    public void onDataRefresh() {
        getBannerData(true);
    }

    @Override
    public void onDataLoadMore() {
        getBannerData(false);
    }

    @Override
    public BaseQuickAdapter<BaseItem, BaseViewHolder> getAdapter() {
        BaseQuickAdapter adapter =  new CommonListAdapter(new ArrayList());
        //设置跨行跨列
        if(type==Constant.BANNER_DAY_RECOMMEND){
            adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
                @Override
                public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                    int spanSize = 6;
                    if(mPresenter.getmDatas()!=null && mPresenter.getmDatas().size()>0 && mPresenter.getmDatas().size()>position){
                        BaseItem item = mPresenter.getmDatas().get(position);
                        if(item instanceof ComHeader){
                            spanSize = 6;
                        }else if(item instanceof BizhiBean){
                            spanSize = 2;
                        }else if(item instanceof DayRecommend){
                            spanSize = 6;
                        }
                    }
                    return spanSize;
                }
            });
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        int count = 1;
        if (type == Constant.BANNER_DAY_RECOMMEND) {
            count = 6;
        }
        GridLayoutManager manager = new GridLayoutManager(getActivity(),count);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

    }
    public void getBannerData(boolean pullToRefresh){
        switch (type){
            case Constant.BANNER_DAY_RECOMMEND:
                mPresenter.getWalRecommend(pullToRefresh);
                break;
            case Constant.BANNER_HOME_ALBUM:
                mPresenter.getAlbum(pullToRefresh);
                break;
        }
    }
}
