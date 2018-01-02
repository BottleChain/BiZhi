package com.yeyue.bizhi.contract;

import com.jess.arms.mvp.IModel;
import com.yeyue.library.contract.YeBannerView;
import com.yeyue.library.data.BannerList;
import com.yeyue.library.data.BaseBanner;

import java.util.List;

import io.reactivex.Observable;

public interface BannerContract {
    interface View<T> extends YeBannerView<T> {
        void setBannerData(List<BaseBanner> bannerData);
    }

    interface Model extends IModel {
        Observable<BannerList> getAlbum(int limit,int start,String type);
        Observable<BannerList> getWalRecommend(int limit,int start);
    }
}