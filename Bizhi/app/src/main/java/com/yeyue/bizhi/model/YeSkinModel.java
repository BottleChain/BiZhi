package com.yeyue.bizhi.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yeyue.bizhi.contract.SkinContract;
import com.yeyue.bizhi.entity.HttpSkins;
import com.yeyue.bizhi.model.api.cache.CommonCache;
import com.yeyue.bizhi.model.api.service.BizhiService;
import com.yeyue.library.data.BaseItem;
import com.yeyue.library.data.YeSkinItem;

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
public class YeSkinModel extends BaseModel implements SkinContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public YeSkinModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public Observable<List<BaseItem>> getSkinList() {
        Observable<List<BaseItem>> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getSkinList().flatMap(new Function<HttpSkins,ObservableSource<List<BaseItem>>>() {
                    @Override
                    public ObservableSource<List<BaseItem>> apply(@NonNull HttpSkins listReply) throws Exception {
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        //处理分类数据
                        if(listReply!=null && listReply.getData()!=null){
                            if(listReply.getData().size()>0){
                                for (int i=0;i<listReply.getData().size();i++){
                                    YeSkinItem item = listReply.getData().get(i);
                                    if(item!=null){
                                        item.setResourcePath("http://111.230.154.222/skins/"+item.getSortName()+"/star.skin");
                                    }
                                    baseItems.add(item);
                                }
                            }
                        }
                        return Observable.just(baseItems);
                    }
                });
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable,new DynamicKey("getSkinList"),new EvictDynamicKey(false));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}