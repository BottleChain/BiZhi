package com.yeyue.library.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.yeyue.library.R;
import com.yeyue.library.constant.YeConstant;
import com.yeyue.library.contract.YeBaseUiActivityView;

/**
 * Created by li.xiao on 2017-7-18.
 */

public abstract class BaseUiActivity<P extends IPresenter> extends BaseActivity<P> implements YeBaseUiActivityView, IView {
    public Toolbar mToolbar;
    protected LinearLayout mLlContent;
    private LoadService loadService;
    private  View ye_base_parent;
    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.yeyue_common_ui_activity;
    }

    @Override
    public void initView(View mRootView) {
        mLlContent = (LinearLayout) mRootView.findViewById(R.id.ye_base_content);
        ye_base_parent = mRootView.findViewById(R.id.ye_base_parent);
        getLayoutInflater().inflate(getContentViewId(), (LinearLayout) mRootView.findViewById(R.id.ye_base_content), true);
        mToolbar = (Toolbar) mRootView.findViewById(R.id.ye_toolbar);
        if(getBackgroundRes()!=0){
            mLlContent.setBackgroundResource(getBackgroundRes());
        }
        if(isToolbar()){
            mToolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavLeftClick();
                }
            });
        }else{
            mToolbar.setVisibility(View.GONE);
        }
}
    @Override
    public void findView() {

    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID != 0){
            //如果initView返回0,框架则不会调用setContentView()
            View mRootView = (View) getLayoutInflater().inflate(layoutResID, null);
            setContentView(mRootView);
            initView(mRootView);
            findView();
            onInitView();
        }
    }

    @Override
    public void onInitView() {
        if(getLoadView()!=null){
            loadService = LoadSir.getDefault().register(getLoadView(), new Callback.OnReloadListener() {
                @Override
                public void onReload(View view) {
                    onViewReload();
                }
            });
            showLoadSirView(YeConstant.LoadSir.LOADING);
        }

    }

    @Override
    public void setTitle(String title) {
        if(isToolbar()){
            getSupportActionBar().setTitle(title);
        }else{
            mToolbar.setTitle(title);
        }
    }

    @Override
    public void setAppContentBackground(int res) {
        if(ye_base_parent!=null && res!=0){
            ye_base_parent.setBackgroundResource(res);
        }
    }

    @Override
    public void setAppNavBackGround(int res) {
        if(mToolbar!=null && res!=0){
            mToolbar.setBackgroundResource(res);
        }
    }

    @Override
    public int getBackgroundRes() {
        return 0;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onNavLeftClick() {
        onBackPressed();
    }

    @Override
    public View getLoadView() {
        return mLlContent;
    }

    @Override
    public void onViewReload() {

    }

    @Override
    public void showLoadSirView(String status) {
        if(loadService!=null){
            if(YeConstant.LoadSir.SUCCESS.equals(status)){
                //加载完成
                loadService.showSuccess();
                return;
            }
            AppComponent appComponent = ((BaseApplication)getApplication()).getAppComponent();
            if(appComponent.extras().containsKey(status) && appComponent.extras().get(status) instanceof Callback){
                Callback callback = (Callback) appComponent.extras().get(status);
                loadService.setCallBack(callback.getClass(), new Transport() {
                    @Override
                    public void order(Context context, View view) {
                        onShowTransport(context,view,status);
                    }
                });
                loadService.showCallback(callback.getClass());
            }
        }
    }

    /**
     * 自定义错误，加载，识别页面数据
     * @param context
     * @param view
     * @param status
     */
    @Override
    public void onShowTransport(Context context, View view, String status) {

    }
}
