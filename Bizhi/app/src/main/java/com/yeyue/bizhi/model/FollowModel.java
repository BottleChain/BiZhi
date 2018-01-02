package com.yeyue.bizhi.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yeyue.bizhi.contract.FollowContract;
import com.yeyue.bizhi.entity.BizhiBean;
import com.yeyue.bizhi.utils.DaoManager;
import com.yeyue.library.data.BaseItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class FollowModel extends BaseModel implements FollowContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public FollowModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public Observable<List<BaseItem>> getFollow() {
        List<BaseItem> baseItems = new ArrayList<>();
        List<BizhiBean> bizhiBeans = DaoManager.getInstance().getDaoSession().loadAll(BizhiBean.class);
        if(bizhiBeans!=null){
            baseItems.addAll(bizhiBeans);
        }
        return Observable.just(baseItems);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}