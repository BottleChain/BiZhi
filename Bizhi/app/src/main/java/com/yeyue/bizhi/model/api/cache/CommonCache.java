package com.yeyue.bizhi.model.api.cache;

import com.yeyue.bizhi.entity.HomePage;
import com.yeyue.bizhi.entity.HotSearch;
import com.yeyue.bizhi.entity.UserDetail;
import com.yeyue.bizhi.entity.WalDetail;
import com.yeyue.bizhi.model.api.HttpRequest;
import com.yeyue.library.data.BannerList;
import com.yeyue.library.data.BaseItem;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;

/**
 * Created by jess on 8/30/16 13:53
 * Contact with jess.yan.effort@gmail.com
 */
public interface CommonCache {

    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<List<BaseItem>> getListDataCache(Observable<List<BaseItem>> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<BannerList> getBannerList(Observable<BannerList> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<UserDetail> getUserDetail(Observable<UserDetail> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<HomePage> getHomePage(Observable<HomePage> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<HttpRequest<WalDetail>> getWalDetailDetail(Observable<HttpRequest<WalDetail>> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<HttpRequest<HotSearch>> getHotSearch(Observable<HttpRequest<HotSearch>> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);


}
