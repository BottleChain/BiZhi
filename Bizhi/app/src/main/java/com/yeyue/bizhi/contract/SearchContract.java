package com.yeyue.bizhi.contract;

import com.jess.arms.mvp.IModel;
import com.yeyue.library.contract.YeSearchView;

import java.util.List;

import io.reactivex.Observable;

public interface SearchContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends YeSearchView {
        void setHotSearch(List<String> hots);
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {
        Observable<List<String>> getSearchKeyword(int first);
    }
}