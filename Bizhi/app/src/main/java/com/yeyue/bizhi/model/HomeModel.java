package com.yeyue.bizhi.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yeyue.bizhi.constant.AdapterConstant;
import com.yeyue.bizhi.contract.HomeContract;
import com.yeyue.bizhi.entity.AlbumBean;
import com.yeyue.bizhi.entity.AlbumDetail;
import com.yeyue.bizhi.entity.BizhiBean;
import com.yeyue.bizhi.entity.ComHeader;
import com.yeyue.bizhi.entity.DayRecommend;
import com.yeyue.bizhi.entity.HomePage;
import com.yeyue.bizhi.entity.HttpAlbumRequest;
import com.yeyue.bizhi.entity.HttpCategory;
import com.yeyue.bizhi.entity.HttpVertical;
import com.yeyue.bizhi.entity.HttpVideo;
import com.yeyue.bizhi.entity.HttpWallpaper;
import com.yeyue.bizhi.entity.VerCategory;
import com.yeyue.bizhi.entity.VideoBean;
import com.yeyue.bizhi.entity.VideoCategory;
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
public class HomeModel extends BaseModel implements HomeContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public Observable<List<BaseItem>> getHomepage(int limint,int start) {
        Observable<HomePage> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getHomepage(limint,start,"hot");
        return mRepositoryManager.obtainCacheService(CommonCache.class).getHomePage(observable,new DynamicKey("getHomepage"+limint+""+start),new EvictDynamicKey(false)).flatMap(new Function<HomePage,ObservableSource<List<BaseItem>>>() {
            @Override
            public ObservableSource<List<BaseItem>> apply(@NonNull HomePage listReply) throws Exception {
                List<BaseItem> baseItems = new ArrayList<BaseItem>();
                //处理首页数据
                if(listReply!=null && listReply.getRes()!=null){
                    if(listReply.getRes().getHomepage()!=null && listReply.getRes().getHomepage().size()>0){
                        //获取小编推荐
                        for (int i=0;i<listReply.getRes().getHomepage().size();i++){
                            DayRecommend homepageBean = listReply.getRes().getHomepage().get(i);
                            if(i==2){
                                //添加每日精选头部视图
                                homepageBean.setItemtype(AdapterConstant.ITEM_DAY_RECOMMEND);
                                homepageBean.setHasMore(true);
                                baseItems.add(homepageBean);
                            }
                            if(homepageBean.getItems()!=null && homepageBean.getType()!=14){
                                for (BizhiBean itemsBean:homepageBean.getItems()){
                                    if(homepageBean.getType()==13){
                                        //小编推荐
                                        itemsBean.setItemtype(AdapterConstant.ITEM_HOMEPAGE_RECOMMEND);
                                        baseItems.add(itemsBean);
                                    }else{
                                        if(i==2){
                                            //每日精选
                                            itemsBean.setItemtype(AdapterConstant.ITEM_BIZHI_DEFAULT);
                                            baseItems.add(itemsBean);
                                        }
                                    }

                                }
                            }
                        }
                    }
                    if(listReply.getRes().getWallpaper()!=null && listReply.getRes().getWallpaper().size()>0){
                        //获取小编推荐
                        if(start==0){
                            ComHeader comHeader = new ComHeader();
                            comHeader.setTitle("热门");
                            baseItems.add(comHeader);
                        }
                        baseItems.addAll(baseItems.size(),listReply.getRes().getWallpaper());

                    }
                }
                return Observable.just(baseItems);
            }
        });
    }


    @Override
    public Observable<List<BaseItem>> getWalCategory() {
        Observable<List<BaseItem>> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getWalCategory().flatMap(new Function<HttpRequest<WalCategory>,ObservableSource<List<BaseItem>>>() {
                    @Override
                    public ObservableSource<List<BaseItem>> apply(@NonNull HttpRequest<WalCategory> listReply) throws Exception {
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        //处理分类数据
                        if(listReply!=null && listReply.getRes()!=null){
                            if(listReply.getRes().getCategory()!=null && listReply.getRes().getCategory().size()>0){
                                baseItems.addAll(listReply.getRes().getCategory());
                            }
                        }
                        return Observable.just(baseItems);
                    }
                });
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable,new DynamicKey("getWalCategory"),new EvictDynamicKey(false));
    }
    @Override
    public Observable<List<BaseItem>> getWalNew(int limit,int start,String order) {
        Observable<List<BaseItem>> observable =  mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getWalNew(limit,start,order).flatMap(new Function<HttpWallpaper<BizhiBean>,ObservableSource<List<BaseItem>>>() {
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
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable,new DynamicKey("getWalNew"+limit+""+start+""+order),new EvictDynamicKey(false));
    }

    @Override
    public Observable<List<BaseItem>> getVerticalBizhi(int limit,int start,String order) {
        Observable<List<BaseItem>> observable =  mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getVerticalBizhi(limit,start,order).flatMap(new Function<HttpVertical<BizhiBean>,ObservableSource<List<BaseItem>>>() {
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
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable,new DynamicKey("getVerticalBizhi"+limit+""+start+""+order),new EvictDynamicKey(false));

    }
    @Override
    public Observable<List<BaseItem>> getVideoRecommend(String type,int limit,int start,String order) {
        Observable<List<BaseItem>> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getVideoList(type,limit,start,order).flatMap(new Function<HttpVideo<VideoBean>,ObservableSource<List<BaseItem>>>() {
                    @Override
                    public ObservableSource<List<BaseItem>> apply(@NonNull HttpVideo<VideoBean> listReply) throws Exception {
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        //处理分类数据
                        if(listReply!=null && listReply.getRes()!=null){
                            if(listReply.getRes().getVideowp()!=null && listReply.getRes().getVideowp().size()>0){
                                baseItems.addAll(listReply.getRes().getVideowp());
                            }
                        }
                        return Observable.just(baseItems);
                    }
                });
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable,new DynamicKey("getVideoRecommend"+type+limit+""+start+""+order),new EvictDynamicKey(false));
    }
    @Override
    public Observable<List<BaseItem>> getVerCategory() {
        Observable<List<BaseItem>> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getVerCategory().flatMap(new Function<HttpCategory<VerCategory>,ObservableSource<List<BaseItem>>>() {
                    @Override
                    public ObservableSource<List<BaseItem>> apply(@NonNull HttpCategory<VerCategory> listReply) throws Exception {
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        //处理分类数据
                        if(listReply!=null && listReply.getRes()!=null){
                            if(listReply.getRes().getCategory()!=null && listReply.getRes().getCategory().size()>0){
                                baseItems.addAll(listReply.getRes().getCategory());
                            }
                        }
                        return Observable.just(baseItems);
                    }
                });
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable,new DynamicKey("getVerCategory"),new EvictDynamicKey(false));
    }

    @Override
    public Observable<List<BaseItem>> getVideoCategoryDetail(String cateId,int limit,int start,String order) {
        Observable<List<BaseItem>> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getVideoCateGoryDetail(cateId,limit,start,order).flatMap(new Function<HttpVideo<VideoBean>,ObservableSource<List<BaseItem>>>() {
                    @Override
                    public ObservableSource<List<BaseItem>> apply(@NonNull HttpVideo<VideoBean> listReply) throws Exception {
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        //处理分类数据
                        if(listReply!=null && listReply.getRes()!=null){
                            if(listReply.getRes().getVideowp()!=null && listReply.getRes().getVideowp().size()>0){
                                baseItems.addAll(listReply.getRes().getVideowp());
                            }
                        }
                        return Observable.just(baseItems);
                    }
                });
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable,new DynamicKey("getVideoCateGoryDetail"+cateId+limit+""+start+order),new EvictDynamicKey(false));

    }

    @Override
    public Observable<List<BaseItem>> getVideoCategory() {
        Observable<List<BaseItem>> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getVideoCategory().flatMap(new Function<HttpCategory<VideoCategory>,ObservableSource<List<BaseItem>>>() {
                    @Override
                    public ObservableSource<List<BaseItem>> apply(@NonNull HttpCategory<VideoCategory> listReply) throws Exception {
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        //处理分类数据
                        if(listReply!=null && listReply.getRes()!=null){
                            if(listReply.getRes().getCategory()!=null && listReply.getRes().getCategory().size()>0){
                                baseItems.addAll(listReply.getRes().getCategory());
                            }
                        }
                        return Observable.just(baseItems);
                    }
                });
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable,new DynamicKey("getVideoCategory"),new EvictDynamicKey(false));
    }

    @Override
    public Observable<List<BaseItem>> getAlubmDetail(String albumId) {
        Observable<List<BaseItem>> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getAlbumDetail(albumId,30,1,"new").flatMap(new Function<HttpRequest<AlbumDetail>,ObservableSource<List<BaseItem>>>() {
                    @Override
                    public ObservableSource<List<BaseItem>> apply(@NonNull HttpRequest<AlbumDetail> listReply) throws Exception {
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
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable,new DynamicKey("getAlubmDetail"+albumId),new EvictDynamicKey(false));
    }

    @Override
    public Observable<List<BaseItem>> getWalCategoryList(String cId,int limit,int skip,String order) {
        Observable<List<BaseItem>> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getWalCategoryList(cId,limit,skip,order).flatMap(new Function<HttpWallpaper<BizhiBean>,ObservableSource<List<BaseItem>>>() {
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
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable,new DynamicKey("getWalCategoryList"+cId+limit+""+skip+order),new EvictDynamicKey(false));
    }
    @Override
    public Observable<List<BaseItem>> getWalCategoryAlbum(String cId,int limit,int skip,String order) {
        Observable<List<BaseItem>> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getWalCategoryAlbum(cId,limit,skip,order).flatMap(new Function<HttpAlbumRequest<AlbumBean>,ObservableSource<List<BaseItem>>>() {
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
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable,new DynamicKey("getWalCategoryAlbum"+cId+limit+""+skip+order),new EvictDynamicKey(false));
    }

    @Override
    public Observable<List<BaseItem>> getVerCategoryList(String cId,int limit,int skip,String order) {
        Observable<List<BaseItem>> observable = mRepositoryManager.obtainRetrofitService(BizhiService.class)
                .getVerCategoryList(cId,limit,skip,order).flatMap(new Function<HttpVertical<BizhiBean>,ObservableSource<List<BaseItem>>>() {
                    @Override
                    public ObservableSource<List<BaseItem>> apply(@NonNull HttpVertical<BizhiBean> listReply) throws Exception {
                        List<BaseItem> baseItems = new ArrayList<BaseItem>();
                        //处理分类数据
                        if(listReply!=null && listReply.getRes()!=null){
                            if(listReply.getRes().getVertical()!=null && listReply.getRes().getVertical().size()>0){
                                baseItems.addAll(listReply.getRes().getVertical());
                            }
                        }
                        return Observable.just(baseItems);
                    }
                });
        return mRepositoryManager.obtainCacheService(CommonCache.class).getListDataCache(observable,new DynamicKey("getVerCategoryList"+cId+limit+""+skip+order),new EvictDynamicKey(false));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}