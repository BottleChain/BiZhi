package com.yeyue.bizhi.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.R;
import com.yeyue.bizhi.constant.Constant;
import com.yeyue.bizhi.contract.HomeContract;
import com.yeyue.bizhi.di.component.DaggerHomeComponent;
import com.yeyue.bizhi.di.module.HomeModule;
import com.yeyue.bizhi.entity.AlbumBean;
import com.yeyue.bizhi.entity.BizhiBean;
import com.yeyue.bizhi.entity.UserBean;
import com.yeyue.bizhi.presenter.HomePresenter;
import com.yeyue.bizhi.ui.adapter.CommonListAdapter;
import com.yeyue.bizhi.utils.BindingUtils;
import com.yeyue.library.base.YeHeaderRecyActivity;
import com.yeyue.library.data.BaseItem;
import com.yeyue.library.utils.ImageLoadUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author li.xiao
 * @describe 专辑详情
 * @time 2017-12-6 15:59
 */
public class AlbumDetailActivity extends YeHeaderRecyActivity<BaseItem, HomePresenter> implements HomeContract.View<BaseItem> {
    @BindView(R.id.ivCover)
    ImageView ivCover;
    @BindView(R.id.tvDesc)
    TextView tvDesc;

    private AlbumBean albumBean;
    private String albumId;
    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if(getIntent()!=null && getIntent().hasExtra(Constant.ALBUM_INFO)){
            albumBean = (AlbumBean) getIntent().getSerializableExtra(Constant.ALBUM_INFO);
        }
        if(albumBean==null){
            return;
        }
        albumId =  albumBean.getId();
        setTitle(albumBean.getName()+"");
        initHeader();
        mPresenter.getAlbumDetail(albumId, true);
    }

    @Override
    public int setHeaderId() {
        return R.layout.activity_album_detail_header;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if(view!=null && view.getTag()!=null){
            if(view.getTag() instanceof BizhiBean){
                BizhiBean bizhiBean = (BizhiBean) view.getTag();
                if(bizhiBean.getUser()==null){
                    UserBean userBean = new UserBean();
                    userBean.setName("张三");
                    userBean.setAvatar("http://img0.adesk.com/download/5790d268742aa7480bf44d3e");
                    bizhiBean.setUser(userBean);
                }
                BindingUtils.oepnBizhiDetail(view,bizhiBean,0);
            }
        }
    }

    @Override
    public void onDataRefresh() {
        mPresenter.getAlbumDetail(albumId, true);
    }

    @Override
    public void onDataLoadMore() {
        mPresenter.getAlbumDetail(albumId, false);
    }

    @Override
    public boolean enableRefresh() {
        return false;
    }

    @Override
    public boolean enableMore() {
        return false;
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        return new CommonListAdapter(new ArrayList());
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public View getLoadView() {
        return getRecyclerView();
    }

    public void initHeader(){
        tvDesc.setText(albumBean.getDesc());
        ImageLoadUtils.showImageView(getActivity(),ivCover,albumBean.getCover());
    }
}
