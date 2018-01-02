package com.yeyue.bizhi.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yeyue.bizhi.constant.AdapterConstant;
import com.yeyue.bizhi.contract.BannerContract;
import com.yeyue.bizhi.entity.BizhiBean;
import com.yeyue.bizhi.entity.DayRecommend;
import com.yeyue.bizhi.entity.HttpAlbum;
import com.yeyue.bizhi.entity.WalRecommend;
import com.yeyue.bizhi.model.api.cache.CommonCache;
import com.yeyue.bizhi.model.api.service.BizhiService;
import com.yeyue.library.data.BannerList;
import com.yeyue.library.data.BaseBanner;
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
public class BannerModel extends BaseModel implements BannerContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public BannerModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public Observable<BannerList> getAlbum(int limit,int start,String type) {
        Observable<BannerList> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getAlbum(limit,start,type).flatMap(new Function<HttpAlbum,ObservableSource<BannerList>>() {
                    @Override
                    public ObservableSource<BannerList> apply(@NonNull HttpAlbum listReply) throws Exception {
                        BannerList bannerList = new BannerList();
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        List<BaseBanner> banners = new ArrayList<BaseBanner>();
                        //处理分类数据
                        if(listReply!=null && listReply.getRes()!=null){
                            if(listReply.getRes().getBanner()!=null && listReply.getRes().getBanner().size()>0){
                                for(HttpAlbum.ResBean.BannerBean bannerBean:listReply.getRes().getBanner()){
                                    bannerBean.setBaTitle("");
                                    bannerBean.setBaImg(bannerBean.getThumb());
                                    banners.add(bannerBean);
                                }
                            }
                            if(listReply.getRes().getAlbum()!=null && listReply.getRes().getAlbum().size()>0){
                                baseItems.addAll(listReply.getRes().getAlbum());
                            }
                        }
                        if(start==0){
                            bannerList.setBanners(banners);
                        }
                        bannerList.setData(baseItems);
                        return Observable.just(bannerList);
                    }
                });
        return mRepositoryManager.obtainCacheService(CommonCache.class).getBannerList(observable,new DynamicKey("getAlbum"+limit+""+start+type),new EvictDynamicKey(false));
    }

    @Override
    public Observable<BannerList> getWalRecommend(int limit,int start) {
        Observable<BannerList> observable =  mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getWalRecommend(limit,start,1).flatMap(new Function<WalRecommend,ObservableSource<BannerList>>() {
                    @Override
                    public ObservableSource<BannerList> apply(@NonNull WalRecommend listReply) throws Exception {
                        BannerList bannerList = new BannerList();
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        List<BaseBanner> banners = new ArrayList<BaseBanner>();
                        //处理分类数据
                        if(listReply!=null && listReply.getRes()!=null){
                            if(listReply.getRes().getBanner()!=null && listReply.getRes().getBanner().size()>0){
                                for(HttpAlbum.ResBean.BannerBean bannerBean:listReply.getRes().getBanner()){
                                    bannerBean.setBaTitle("");
                                    bannerBean.setBaImg(bannerBean.getThumb());
                                    banners.add(bannerBean);
                                }
                            }
                            if(listReply.getRes().getRecommend()!=null && listReply.getRes().getRecommend().size()>0){
                                //获取小编推荐
                                for (int i=0;i<listReply.getRes().getRecommend().size();i++){
                                    DayRecommend homepageBean = listReply.getRes().getRecommend().get(i);
                                    //添加每日精选头部视图
                                    homepageBean.setItemtype(AdapterConstant.ITEM_DAY_RECOMMEND);
                                    baseItems.add(homepageBean);
                                    if(homepageBean.getItems()!=null && homepageBean.getType()!=14){
                                        for (BizhiBean itemsBean:homepageBean.getItems()){
                                            itemsBean.setItemtype(AdapterConstant.ITEM_BIZHI_DEFAULT);
                                            baseItems.add(itemsBean);
                                        }
                                    }
                                }
                                baseItems.addAll(listReply.getRes().getRecommend());
                            }
                        }
                        if(start==0){
                            bannerList.setBanners(banners);
                        }
                        bannerList.setData(baseItems);
                        return Observable.just(bannerList);
                    }
                });
        return mRepositoryManager.obtainCacheService(CommonCache.class).getBannerList(observable,new DynamicKey("getWalRecommend"+limit+""+start),new EvictDynamicKey(false));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}