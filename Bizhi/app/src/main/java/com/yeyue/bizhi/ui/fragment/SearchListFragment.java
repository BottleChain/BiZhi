package com.yeyue.bizhi.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.constant.Constant;
import com.yeyue.bizhi.contract.SearchListContract;
import com.yeyue.bizhi.di.component.DaggerSearchListComponent;
import com.yeyue.bizhi.di.module.SearchListModule;
import com.yeyue.bizhi.entity.AlbumBean;
import com.yeyue.bizhi.entity.DayRecommend;
import com.yeyue.bizhi.presenter.SearchListPresenter;
import com.yeyue.bizhi.ui.adapter.CommonListAdapter;
import com.yeyue.library.base.YeRecyFragment;
import com.yeyue.library.data.BaseItem;

import java.util.ArrayList;

/**
  *@describe 搜索列表详情页
  *@author li.xiao
  *@time 2017-12-15 11:06
  */
public class SearchListFragment extends YeRecyFragment<BaseItem, SearchListPresenter> implements SearchListContract.View<BaseItem> {
    private int type;
    private String query;
    public static SearchListFragment newInstance(int type,String query) {
        SearchListFragment fragment = new SearchListFragment();
        fragment.type = type;
        fragment.query = query;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerSearchListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .searchListModule(new SearchListModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        getData(type,query,true);
    }

    public void refreshSearch(String query){
        this.query = query;
        if(isPrepared){
            onDataRefresh();
        }
    }
    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
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
        getData(type,query,true);
    }

    @Override
    public void onDataLoadMore() {
        getData(type,query,false);
    }

    @Override
    public BaseQuickAdapter<BaseItem, BaseViewHolder> getAdapter() {
        BaseQuickAdapter adapter =  new CommonListAdapter(new ArrayList());
        //设置跨行跨列
        adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                int spanSize = 2;
                if(mPresenter.getmDatas()!=null && mPresenter.getmDatas().size()>0 && mPresenter.getmDatas().size()>position){
                    BaseItem item = mPresenter.getmDatas().get(position);
                    if(item instanceof AlbumBean){
                        spanSize = 6;
                    }if(item instanceof DayRecommend){
                        spanSize = 6;
                    }
                }
                return spanSize;
            }
        });
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        int count = 6;
        GridLayoutManager manager = new GridLayoutManager(getActivity(),6);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    public void getData(int type,String query,boolean pullToRefresh){
        switch (type){
            case Constant.SEARCH_PAGE_ALL:
                mPresenter.getSearchList(query,pullToRefresh);
                break;
            case Constant.SEARCH_PAGE_BIZHI:
                mPresenter.getSearchTag("wallpaper",query,pullToRefresh);
                break;
            case Constant.SEARCH_PAGE_VERTICAL:
                mPresenter.getSearchTag("vertical",query,pullToRefresh);
                break;
            case Constant.SEARCH_PAGE_VIDEO:
                mPresenter.getSearchTag("live",query,pullToRefresh);
                break;
            case Constant.SEARCH_PAGE_LOCK:
                mPresenter.getSearchTag("lock",query,pullToRefresh);
                break;
        }
    }
}
