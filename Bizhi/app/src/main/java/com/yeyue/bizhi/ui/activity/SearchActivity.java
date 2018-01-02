package com.yeyue.bizhi.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.R;
import com.yeyue.bizhi.contract.SearchContract;
import com.yeyue.bizhi.di.component.DaggerSearchComponent;
import com.yeyue.bizhi.di.module.SearchModule;
import com.yeyue.bizhi.presenter.SearchPresenter;
import com.yeyue.bizhi.ui.fragment.SearchPageFragment;
import com.yeyue.library.base.YeSearchFragmentActivity;
import com.yeyue.library.widgets.flowlayout.TagColor;
import com.yeyue.library.widgets.flowlayout.TagGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author li.xiao
 * @describe 搜索界面
 * @time 2017-12-14 16:48
 */
public class SearchActivity extends YeSearchFragmentActivity<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.tagHotSearch)
    TagGroup tagHotSearch;
    private SearchPageFragment searchPageFragment;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .searchModule(new SearchModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public Fragment getFragment(String query) {
        if (searchPageFragment == null) {
            searchPageFragment = SearchPageFragment.newInstance(query);
        }
        return searchPageFragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mPresenter.getSearchKeyword(1);
    }


    @Override
    public void refreshSearch(String query) {
        if(searchPageFragment!=null){
            searchPageFragment.refreshSearch(query);
        }
    }

    @OnClick(R.id.tvRechange)
    public void onViewClicked() {
        mPresenter.getSearchKeyword(0);
    }

    @Override
    public void setHotSearch(List<String> hots) {
        if(hots!=null && hots.size()>0){
            List<TagColor> colors = TagColor.getRandomColors(hots.size());
            tagHotSearch.setTags(colors,(String[]) hots.toArray(new String[hots.size()]));
            tagHotSearch.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                @Override
                public void onTagClick(String tag) {
                    searchQuery(tag);
                }
            });
        }else{
            tagHotSearch.setVisibility(View.GONE);
        }
    }
}
