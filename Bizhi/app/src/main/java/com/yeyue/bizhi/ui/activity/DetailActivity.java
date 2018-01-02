package com.yeyue.bizhi.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.constant.Constant;
import com.yeyue.bizhi.entity.DetailBean;
import com.yeyue.bizhi.ui.fragment.DetailFragment;
import com.yeyue.library.base.YeFragmentActivity;

/**
  *@describe 精选
  *@author Administrator
  *@time 2017/11/5 0005 下午 6:47
  */
public class DetailActivity extends YeFragmentActivity{
    private DetailBean detailBean;
    @Override
    public Fragment getFragment() {
        return  DetailFragment.newInstance(detailBean);
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initData(Bundle bundle) {
        if(getIntent()!=null && getIntent().hasExtra(Constant.DETAIL_INFO)){
            if(getIntent().hasExtra(Constant.DETAIL_INFO)){
                detailBean = (DetailBean) getIntent().getSerializableExtra(Constant.DETAIL_INFO);
            }
        }
        setTitle("壁纸");
        super.initData(bundle);
    }

    @Override
    public boolean isToolbar() {
        return true;
    }
}
