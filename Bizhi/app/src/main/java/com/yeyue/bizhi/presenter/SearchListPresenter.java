package com.yeyue.bizhi.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.yeyue.bizhi.contract.SearchListContract;
import com.yeyue.library.data.BaseItem;
import com.yeyue.library.presenter.YeListIPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class SearchListPresenter extends YeListIPresenter<SearchListContract.Model, SearchListContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<BaseItem> mDatas;
    private int page;
    @Inject
    public SearchListPresenter(SearchListContract.Model model, SearchListContract.View rootView
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

    public List<BaseItem> getmDatas() {
        return mDatas;
    }
    public void getSearchList(String query,boolean pullToRefresh) {
        getDataList(mModel.getSearchAll(query,pregPage,0), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }

    public void getSearchTag(String tag,String query,boolean pullToRefresh) {
        pregPage = 30;
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getSearchTag(tag,query,pregPage,start), mDatas, mRootView, mErrorHandler, pullToRefresh);
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