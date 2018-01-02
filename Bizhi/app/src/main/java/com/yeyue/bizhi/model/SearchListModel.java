package com.yeyue.bizhi.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yeyue.bizhi.contract.SearchListContract;
import com.yeyue.bizhi.entity.SearchAll;
import com.yeyue.bizhi.entity.SearchTags;
import com.yeyue.bizhi.model.api.HttpRequest;
import com.yeyue.bizhi.model.api.service.BizhiService;
import com.yeyue.library.data.BaseItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

@ActivityScope
public class SearchListModel extends BaseModel implements SearchListContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public SearchListModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public Observable<List<BaseItem>> getSearchAll(String query,int limit,int skip) {
        return  mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getSearchAll(query,limit,skip).flatMap(new Function<HttpRequest<SearchAll>,ObservableSource<List<BaseItem>>>() {
                    @Override
                    public ObservableSource<List<BaseItem>> apply(@NonNull HttpRequest<SearchAll> listReply) throws Exception {
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        //处理分类数据
                        if(listReply!=null && listReply.getRes()!=null){
                            if(listReply.getRes().getSearch()!=null && listReply.getRes().getSearch().size()>0){
                                for (int i=0;i<listReply.getRes().getSearch().size();i++){
                                    baseItems.addAll(baseItems.size(),listReply.getRes().getSearch().get(i).getItems());
                                }
                            }
                        }
                        return Observable.just(baseItems);
                    }
                });
    }
    @Override
    public Observable<List<BaseItem>> getSearchTag(String tag,String query,int limit,int skip) {
        return  mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getSearchTag(tag,query,limit,skip).flatMap(new Function<HttpRequest<SearchTags>,ObservableSource<List<BaseItem>>>() {
                    @Override
                    public ObservableSource<List<BaseItem>> apply(@NonNull HttpRequest<SearchTags> listReply) throws Exception {
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        //处理分类数据
                        if(listReply!=null && listReply.getRes()!=null){
                            if(listReply.getRes().getAlbum()!=null && listReply.getRes().getAlbum().size()>0){
                                baseItems.addAll(baseItems.size(),listReply.getRes().getAlbum());
                            }
                            if(listReply.getRes().getLive()!=null && listReply.getRes().getLive().size()>0){
                                baseItems.addAll(baseItems.size(),listReply.getRes().getLive());
                            }
                            if(listReply.getRes().getVertical()!=null && listReply.getRes().getVertical().size()>0){
                                baseItems.addAll(baseItems.size(),listReply.getRes().getVertical());
                            }
                            if(listReply.getRes().getWallpaper()!=null && listReply.getRes().getWallpaper().size()>0){
                                baseItems.addAll(baseItems.size(),listReply.getRes().getWallpaper());
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