package com.yeyue.bizhi.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yeyue.bizhi.contract.SearchContract;
import com.yeyue.bizhi.entity.HotSearch;
import com.yeyue.bizhi.model.api.HttpRequest;
import com.yeyue.bizhi.model.api.cache.CommonCache;
import com.yeyue.bizhi.model.api.service.BizhiService;

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
public class SearchModel extends BaseModel implements SearchContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public SearchModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public Observable<List<String>> getSearchKeyword(int first) {
        Observable<HttpRequest<HotSearch>> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getSearchKeyword(first);
        return mRepositoryManager.obtainCacheService(CommonCache.class).getHotSearch(observable,new DynamicKey("getWalCategory"),new EvictDynamicKey(true)).flatMap(new Function<HttpRequest<HotSearch>,ObservableSource<List<String>>>() {
            @Override
            public ObservableSource<List<String>> apply(@NonNull HttpRequest<HotSearch> listReply) throws Exception {
                List<String> baseItems = new ArrayList<String>();
                //处理分类数据
                if(listReply!=null && listReply.getRes()!=null){
                    if(listReply.getRes().getKeyword()!=null && listReply.getRes().getKeyword().size()>0){
                        baseItems.addAll(listReply.getRes().getKeyword().get(0).getItems());
                    }
                }
                return Observable.just(baseItems);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}