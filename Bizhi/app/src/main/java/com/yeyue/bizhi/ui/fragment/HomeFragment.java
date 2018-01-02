package com.yeyue.bizhi.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.constant.AdapterConstant;
import com.yeyue.bizhi.constant.Constant;
import com.yeyue.bizhi.contract.HomeContract;
import com.yeyue.bizhi.di.component.DaggerHomeComponent;
import com.yeyue.bizhi.di.module.HomeModule;
import com.yeyue.bizhi.entity.BizhiBean;
import com.yeyue.bizhi.entity.ComHeader;
import com.yeyue.bizhi.entity.DayRecommend;
import com.yeyue.bizhi.presenter.HomePresenter;
import com.yeyue.bizhi.ui.adapter.CommonListAdapter;
import com.yeyue.library.base.YeRecyFragment;
import com.yeyue.library.data.BaseItem;

import java.util.ArrayList;

/**
  *@describe 列表通用Fragment
  *@author li.xiao
  *@time 2017-12-7 14:37
  */
public class HomeFragment extends YeRecyFragment<BaseItem, HomePresenter> implements HomeContract.View<BaseItem> {

    private int type;
    private String extend;
    public static HomeFragment newInstance(int type) {
        HomeFragment fragment = new HomeFragment();
        fragment.type = type;
        return fragment;
    }
    public static HomeFragment newInstance(int type,String extend) {
        HomeFragment fragment = new HomeFragment();
        fragment.type = type;
        fragment.extend = extend;
        return fragment;
    }
    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void initData() {
        getData(true);
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
        if(type== Constant.HOME_PAGE_CATEGORY){
            return false;
        }
        return true;
    }

    @Override
    public boolean enableMore() {
        boolean enable = false;
        switch (type){
            case Constant.HOME_PAGE_NEW:
            case Constant.HOME_PAGE_RECOMMEND:
            case Constant.WALLPAPER_CATEGORY_ALBUM:
            case Constant.WALLPAPER_CATEGORY_HOT:
            case Constant.WALLPAPER_CATEGORY_NEW:
            case Constant.VERTICAL_CATEGORY_HOT:
            case Constant.VERTICAL_CATEGORY_NEW:
            case Constant.VIDEO_CATEGORY_HOT:
            case Constant.VIDEO_CATEGORY_NEW:
                enable = true;
                break;
        }
        return enable;
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
        getData(true);
    }

    @Override
    public void onDataLoadMore() {
        getData(false);
    }

    @Override
    public BaseQuickAdapter<BaseItem, BaseViewHolder> getAdapter() {
        BaseQuickAdapter adapter =  new CommonListAdapter(new ArrayList());
        //设置跨行跨列
        if(type==Constant.HOME_PAGE_RECOMMEND){
            adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
                @Override
                public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                    int spanSize = 2;
                    if(mPresenter.getmDatas()!=null && mPresenter.getmDatas().size()>0 && mPresenter.getmDatas().size()>position){
                        BaseItem item = mPresenter.getmDatas().get(position);
                        if(item instanceof ComHeader){
                            spanSize = 6;
                        }else if(item instanceof BizhiBean){
                            BizhiBean bizhiBean = (BizhiBean) item;
                            if(bizhiBean.getItemType()== AdapterConstant.ITEM_HOMEPAGE_RECOMMEND){
                                spanSize = 3;
                            }
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
        int count = 6;
        switch (type){
            case Constant.HOME_PAGE_CATEGORY:
            case Constant.VIDEO_PAGE_CATEGORY:
                count = 2;
                break;
            case Constant.VIDEO_PAGE_RECOMMEND:
            case Constant.VIDEO_PAGE_HOT:
            case Constant.VIDEO_PAGE_NEW:
            case Constant.VIDEO_PAGE_YULE:
            case Constant.VIDEO_CATEGORY_HOT:
            case Constant.VIDEO_CATEGORY_NEW:
            case Constant.HOME_PAGE_NEW:
            case Constant.WALLPAPER_CATEGORY_HOT:
            case Constant.WALLPAPER_CATEGORY_NEW:
            case Constant.VERTICAL_CATEGORY_HOT:
            case Constant.VERTICAL_CATEGORY_NEW:
                count = 3;
                break;
            case Constant.WALLPAPER_CATEGORY_ALBUM:
                count = 1;
                break;
        }
        GridLayoutManager manager = new GridLayoutManager(getActivity(),count);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }

    public void getData(boolean pullToRefresh){
        switch (type){
            case Constant.HOME_PAGE_CATEGORY:
                //主页壁纸分类
                mPresenter.getCategory(pullToRefresh);
                break;
            case Constant.HOME_PAGE_NEW:
                //主页最新
                mPresenter.getWalNew(pullToRefresh);
                break;
            case Constant.HOME_PAGE_RECOMMEND:
                //主页推荐
                mPresenter.getHome(pullToRefresh);
                break;
            case Constant.VIDEO_PAGE_RECOMMEND:
                //视频壁纸推荐
                mPresenter.getVideoRecommend("featured","hot",pullToRefresh);
                break;
            case Constant.VIDEO_PAGE_YULE:
                //视频壁纸娱乐
                mPresenter.getVideoCategoryDetail("59b25abbe7bce76bc834198a","new",pullToRefresh);
                break;
            case Constant.VIDEO_CATEGORY_HOT:
                //视频壁纸娱乐
                mPresenter.getVideoCategoryDetail(extend,"hot",pullToRefresh);
                break;
            case Constant.VIDEO_CATEGORY_NEW:
                //视频壁纸娱乐
                mPresenter.getVideoCategoryDetail(extend,"new",pullToRefresh);
                break;
            case Constant.VIDEO_PAGE_NEW:
                //视频壁纸推荐
                mPresenter.getVideoRecommend("videowp","new",pullToRefresh);
                break;
            case Constant.VIDEO_PAGE_HOT:
                //视频壁纸热门
                mPresenter.getVideoRecommend("videowp","hot",pullToRefresh);
                break;
            case Constant.VIDEO_PAGE_CATEGORY:
                //视频壁纸分类
                mPresenter.getVideoCategory(pullToRefresh);
                break;
            case Constant.WALLPAPER_CATEGORY_HOT:
                //分类热门
                mPresenter.getWalCategoryList(extend,"hot",pullToRefresh);
                break;
            case Constant.WALLPAPER_CATEGORY_NEW:
                //分类最新
                mPresenter.getWalCategoryList(extend,"new",pullToRefresh);
                break;
            case Constant.WALLPAPER_CATEGORY_ALBUM:
                //分类专辑
                mPresenter.getWalCategoryAlbum(extend,"new",pullToRefresh);
                break;
            case Constant.VERTICAL_CATEGORY_HOT:
                //竖屏分类热门
                mPresenter.getVerCategoryList(extend,"hot",pullToRefresh);
                break;
            case Constant.VERTICAL_CATEGORY_NEW:
                //竖屏分类最新
                mPresenter.getVerCategoryList(extend,"new",pullToRefresh);
                break;
            default:
                mPresenter.getVideoRecommend("videowp","hot",pullToRefresh);
                break;
        }

    }
}
