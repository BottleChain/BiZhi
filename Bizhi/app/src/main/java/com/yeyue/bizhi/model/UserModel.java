package com.yeyue.bizhi.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yeyue.bizhi.contract.UserContract;
import com.yeyue.bizhi.entity.UserDetail;
import com.yeyue.bizhi.entity.WalCategory;
import com.yeyue.bizhi.model.api.HttpRequest;
import com.yeyue.bizhi.model.api.cache.CommonCache;
import com.yeyue.bizhi.model.api.service.BizhiService;
import com.yeyue.library.data.BaseItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

@ActivityScope
public class UserModel extends BaseModel implements UserContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public UserModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public Observable<UserDetail> getUser(String uId) {
        Observable<UserDetail> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getUserDetail(uId).flatMap(new Function<HttpRequest<UserDetail>,ObservableSource<UserDetail>>() {
                    @Override
                    public ObservableSource<UserDetail> apply(@NonNull HttpRequest<UserDetail> listReply) throws Exception {
                        return Observable.just(listReply.getRes());
                    }
                });
        return mRepositoryManager.obtainCacheService(CommonCache.class).getUserDetail(observable,new DynamicKey("getUser"+uId),new EvictDynamicKey(false));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}