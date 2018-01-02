package com.yeyue.bizhi.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yeyue.bizhi.R;
import com.yeyue.bizhi.contract.DetailContract;
import com.yeyue.bizhi.entity.ComHeader;
import com.yeyue.bizhi.entity.DetailBean;
import com.yeyue.bizhi.entity.WalDetail;
import com.yeyue.bizhi.model.api.HttpRequest;
import com.yeyue.bizhi.model.api.cache.CommonCache;
import com.yeyue.bizhi.model.api.service.BizhiService;
import com.yeyue.bizhi.utils.DaoManager;
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
public class DetailModel extends BaseModel implements DetailContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public DetailModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public Observable<List<BaseItem>> getDetail(String iD) {
        Observable<HttpRequest<WalDetail>> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getWalDetail(iD);
        return mRepositoryManager.obtainCacheService(CommonCache.class).getWalDetailDetail(observable,new DynamicKey("getDetail"+iD),new EvictDynamicKey(true)).flatMap(new Function<HttpRequest<WalDetail>,ObservableSource<List<BaseItem>>>() {
            @Override
            public ObservableSource<List<BaseItem>> apply(@NonNull HttpRequest<WalDetail> listReply) throws Exception {
                List<BaseItem> baseItems = new ArrayList<BaseItem>();
                if(listReply!=null && listReply.getRes()!=null){
                    //处理专辑
                    if(listReply.getRes().getAlbum()!=null && listReply.getRes().getAlbum().size()>0){
                        ComHeader comHeader = new ComHeader();
                        comHeader.setType(ComHeader.NO_LEFT_ICON);
                        comHeader.setTitle("相关");
                        baseItems.add(comHeader);
                        baseItems.addAll(baseItems.size(),listReply.getRes().getAlbum());
                    }
                    //处理热门评论
                    if(listReply.getRes().getHot()!=null && listReply.getRes().getHot().size()>0){
                        ComHeader comHeader = new ComHeader();
                        comHeader.setType(ComHeader.ICON_INSET);
                        comHeader.setLeftIcon(R.mipmap.comment_hot);
                        comHeader.setTitle("热门评论");
                        baseItems.add(comHeader);
                        baseItems.addAll(baseItems.size(),listReply.getRes().getHot());
                    }
                    //处理最新评论
                    if(listReply.getRes().getComment()!=null && listReply.getRes().getComment().size()>0){
                        ComHeader comHeader = new ComHeader();
                        comHeader.setType(ComHeader.ICON_INSET);
                        comHeader.setLeftIcon(R.mipmap.comment_new);
                        comHeader.setTitle("最新评论");
                        baseItems.add(comHeader);
                        baseItems.addAll(baseItems.size(),listReply.getRes().getComment());
                    }
                }
                return Observable.just(baseItems);
            }
        });
    }

    @Override
    public void follow(DetailBean detailBean, boolean isFollow) {
        if(isFollow){
            DaoManager.getInstance().getDaoSession().getDetailBeanDao().insert(detailBean);
        }else{
            DaoManager.getInstance().getDaoSession().getDetailBeanDao().delete(detailBean);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}