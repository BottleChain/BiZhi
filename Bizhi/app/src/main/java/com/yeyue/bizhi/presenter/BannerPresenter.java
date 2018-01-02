package com.yeyue.bizhi.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.yeyue.bizhi.contract.BannerContract;
import com.yeyue.library.data.BaseItem;
import com.yeyue.library.presenter.YeBannerPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class BannerPresenter extends YeBannerPresenter<BannerContract.Model, BannerContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<BaseItem> mDatas;
    private int page = 1;
    @Inject
    public BannerPresenter(BannerContract.Model model, BannerContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
        mDatas = new ArrayList<>();
        pregPage = 10;
    }

    public List<BaseItem> getmDatas(){
        return mDatas;
    }
    /**
     * 获取专辑
     * @param pullToRefresh
     */
    public void getAlbum(boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getBannerList(mModel.getAlbum(pregPage,start,"new"), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }
    /**
     * 获取专辑
     * @param pullToRefresh
     */
    public void getWalRecommend(boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getBannerList(mModel.getWalRecommend(pregPage,start), mDatas, mRootView, mErrorHandler, pullToRefresh);
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