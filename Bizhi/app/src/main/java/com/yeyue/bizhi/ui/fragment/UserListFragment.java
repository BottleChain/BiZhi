package com.yeyue.bizhi.ui.fragment;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.R;
import com.yeyue.bizhi.constant.Constant;
import com.yeyue.bizhi.contract.UserListContract;
import com.yeyue.bizhi.di.component.DaggerUserListComponent;
import com.yeyue.bizhi.di.module.UserListModule;
import com.yeyue.bizhi.presenter.UserListPresenter;
import com.yeyue.bizhi.ui.adapter.CommonListAdapter;
import com.yeyue.library.base.YeRecyFragment;
import com.yeyue.library.constant.YeConstant;
import com.yeyue.library.data.BaseItem;

import java.util.ArrayList;

/**
  *@describe 用户中心列表
  *@author li.xiao
  *@time 2017-12-7 15:06
  */
public class UserListFragment extends YeRecyFragment<BaseItem, UserListPresenter> implements UserListContract.View<BaseItem> {

    private int type;
    private String uid;
    public static UserListFragment newInstance(int type,String uid) {
        UserListFragment fragment = new UserListFragment();
        fragment.type = type;
        fragment.uid = uid;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerUserListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userListModule(new UserListModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void initData() {
        getData(true);
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }

    @Override
    public boolean enableRefresh() {
        return false;
    }

    @Override
    public boolean enableMore() {
        return true;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

    }

    @Override
    public boolean isToolbar() {
        return false;
    }


    @Override
    public void onDataRefresh() {
        getData(true);
    }

    @Override
    public void onDataLoadMore() {
        getData(false);
    }

    @Override
    public BaseQuickAdapter<BaseItem, BaseViewHolder> getAdapter() {
        return new CommonListAdapter(new ArrayList());
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        int count = 3;
        switch (type){
            case Constant.USER_PAGE_ALBUM:
                count = 1;
                break;
        }
        GridLayoutManager manager = new GridLayoutManager(getActivity(),count);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
    public void getData(boolean pullToRefresh){
        switch (type){
            case Constant.USER_PAGE_BIZHI:
                mPresenter.getUserWallPaper(uid,"new",null,pullToRefresh);
                break;
            case Constant.USER_PAGE_WORK:
                mPresenter.getUserWallPaper(uid,"new","upload",pullToRefresh);
                break;
            case Constant.USER_PAGE_ALBUM:
                mPresenter.getUserAlbum(uid,"new",pullToRefresh);
                break;
            case Constant.USER_PAGE_VERTICAL:
                mPresenter.getUserVertical(uid,"new","upload",pullToRefresh);
                break;
            case Constant.USER_PAGE_VERTICAL_WORK:
                mPresenter.getUserVertical(uid,"new","upload",pullToRefresh);
                break;
            case Constant.USER_PAGE_VIDEO:
                break;
            case Constant.USER_PAGE_LOCK:
                break;
        }
    }
    @Override
    public void onShowTransport(Context context, View view, String status) {
        super.onShowTransport(context, view, status);
        if(view==null || TextUtils.isEmpty(status)){
            return;
        }
        if(type==Constant.USER_PAGE_ALBUM && status.equals(YeConstant.LoadSir.EMPTY)){
            //用户专辑列表
            if(view.findViewById(R.id.tv_empty)!=null){
                TextView tvEmpty = view.findViewById(R.id.tv_empty);
                tvEmpty.setText("专辑为空");
            }
        }
    }
}
