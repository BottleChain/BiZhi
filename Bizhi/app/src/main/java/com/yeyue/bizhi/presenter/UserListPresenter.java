package com.yeyue.bizhi.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.yeyue.bizhi.contract.UserListContract;
import com.yeyue.library.data.BaseItem;
import com.yeyue.library.presenter.YeListIPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class UserListPresenter extends YeListIPresenter<UserListContract.Model, UserListContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<BaseItem> mDatas;
    private int page = 1;
    @Inject
    public UserListPresenter(UserListContract.Model model, UserListContract.View rootView
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

    public void getUserWallPaper(String uid,String order,String action,boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getUserWallPaper(uid,pregPage,start,order,action), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }

    public void getUserVertical(String uid,String order,String action,boolean pullToRefresh) {
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getUserVertical(uid,pregPage,start,order,action), mDatas, mRootView, mErrorHandler, pullToRefresh);
    }
    public void getUserAlbum(String uid,String order,boolean pullToRefresh) {
        pregPage = 10;
        if(pullToRefresh){
            page = 1;
        }else{
            page++;
        }
        int start = (page-1)*pregPage;
        getDataList(mModel.getUserAlbum(uid,pregPage,start,order), mDatas, mRootView, mErrorHandler, pullToRefresh);
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