package com.yeyue.bizhi.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.yeyue.bizhi.contract.HomeContract;
import com.yeyue.library.data.BaseItem;
import com.yeyue.library.presenter.YeListIPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class HomePresenter extends YeListIPresenter<HomeContract.Model, HomeContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<BaseItem> mDatas;
    private int page = 1;
    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
        mDatas = new ArrayList<>();
        pregPage = 30;
    }

    public List<BaseItem> getmDatas() {
        return mDatas;
    }
    public void getHome(boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getHomepage(pregPage,start), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }
    public void getCategory(boolean pullToRefresh) {
        getDataList(mModel.getWalCategory(), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }
    public void getWalNew(boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getWalNew(pregPage,start,"new"), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }

    /**
     * 竖屏热门
     * @param pullToRefresh
     */
    public void getVerticalHot(String type,boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getVerticalBizhi(pregPage,start,type), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }
    public void getVerCategory(boolean pullToRefresh) {
        getDataList(mModel.getVerCategory(), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }
    public void getVideoRecommend(String type,String order,boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getVideoRecommend(type,pregPage,start,order), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }

    public void getVideoCategoryDetail(String cateId,String order,boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getVideoCategoryDetail(cateId,pregPage,start,order), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }

    public void getVideoCategory(boolean pullToRefresh) {
        getDataList(mModel.getVideoCategory(), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }

    /**
     * 获取专辑详情
     * @param albumId
     * @param pullToRefresh
     */
    public void getAlbumDetail(String albumId,boolean pullToRefresh) {
        getDataList(mModel.getAlubmDetail(albumId), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }

    public void getWalCategoryList(String cId,String order,boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getWalCategoryList(cId,pregPage,start,order), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }
    public void getWalCategoryAlbum(String cId,String order,boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getWalCategoryAlbum(cId,pregPage,start,order), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }

    public void getVerCategoryList(String cId,String order,boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getVerCategoryList(cId,pregPage,start,order), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}