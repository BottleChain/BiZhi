package com.yeyue.bizhi.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yeyue.bizhi.constant.AdapterConstant;
import com.yeyue.bizhi.contract.UserListContract;
import com.yeyue.bizhi.entity.AlbumBean;
import com.yeyue.bizhi.entity.BizhiBean;
import com.yeyue.bizhi.entity.HttpAlbumRequest;
import com.yeyue.bizhi.entity.HttpVertical;
import com.yeyue.bizhi.entity.HttpWallpaper;
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
public class UserListModel extends BaseModel implements UserListContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public UserListModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public Observable<List<BaseItem>> getUserWallPaper(String uid,int limit,int skip,String order,String action) {
        return mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getUserWallPaper(uid,limit,skip,order,action).flatMap(new Function<HttpWallpaper<BizhiBean>,ObservableSource<List<BaseItem>>>() {
                    @Override
                    public ObservableSource<List<BaseItem>> apply(@NonNull HttpWallpaper<BizhiBean> listReply) throws Exception {
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        //处理分类数据
                        if(listReply!=null && listReply.getRes()!=null){
                            if(listReply.getRes().getWallpaper()!=null && listReply.getRes().getWallpaper().size()>0){
                                baseItems.addAll(listReply.getRes().getWallpaper());
                            }
                        }
                        return Observable.just(baseItems);
                    }
                });
    }
    @Override
    public Observable<List<BaseItem>> getUserVertical(String uid,int limit,int skip,String order,String action) {
        return mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getUserVertical(uid,limit,skip,order,action).flatMap(new Function<HttpVertical<BizhiBean>,ObservableSource<List<BaseItem>>>() {
                    @Override
                    public ObservableSource<List<BaseItem>> apply(@NonNull HttpVertical<BizhiBean> listReply) throws Exception {
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        //处理分类数据
                        if(listReply!=null && listReply.getRes()!=null){
                            if(listReply.getRes().getVertical()!=null && listReply.getRes().getVertical().size()>0){
                                for (int i=0;i<listReply.getRes().getVertical().size();i++){
                                    BizhiBean bizhiBean = (BizhiBean) listReply.getRes().getVertical().get(i);
                                    bizhiBean.setItemtype(AdapterConstant.ITEM_VERTICAL_BIZHI);
                                    baseItems.add(bizhiBean);
                                }
                            }
                        }
                        return Observable.just(baseItems);
                    }
                });
    }
    @Override
    public Observable<List<BaseItem>> getUserAlbum(String uid,int limit,int skip,String order) {
        return mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getUserAlbum(uid,limit,skip,order).flatMap(new Function<HttpAlbumRequest<AlbumBean>,ObservableSource<List<BaseItem>>>() {
                    @Override
                    public ObservableSource<List<BaseItem>> apply(@NonNull HttpAlbumRequest<AlbumBean> listReply) throws Exception {
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        //处理分类数据
                        if(listReply!=null && listReply.getRes()!=null){
                            if(listReply.getRes().getAlbum()!=null && listReply.getRes().getAlbum().size()>0){
                                baseItems.addAll(listReply.getRes().getAlbum());
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