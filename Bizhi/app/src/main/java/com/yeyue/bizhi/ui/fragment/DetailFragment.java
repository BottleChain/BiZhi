package com.yeyue.bizhi.ui.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.yeyue.bizhi.R;
import com.yeyue.bizhi.contract.DetailContract;
import com.yeyue.bizhi.di.component.DaggerDetailComponent;
import com.yeyue.bizhi.di.module.DetailModule;
import com.yeyue.bizhi.entity.DetailBean;
import com.yeyue.bizhi.presenter.DetailPresenter;
import com.yeyue.bizhi.ui.adapter.CommonListAdapter;
import com.yeyue.bizhi.widgets.HeadWrapView;
import com.yeyue.library.base.YeRecyFragment;
import com.yeyue.library.data.BaseItem;
import com.yeyue.library.utils.ImageLoadUtils;
import com.yeyue.library.widgets.viewbigimage.ViewBigImageActivity;

import java.util.ArrayList;


public class DetailFragment extends YeRecyFragment<BaseItem, DetailPresenter> implements DetailContract.View<BaseItem> {

    private HeadWrapView headWrapView;
    private ImageView ivCover;
    private ImageView ivVerticalCover;
    private ImageView ivLiveCover;
    private ThumbUpView tvZan;
    private TextView tvCollect;
    private DetailBean detailBean;
    private String iD;
    public static DetailFragment newInstance(DetailBean detailBean) {
        DetailFragment fragment = new DetailFragment();
        fragment.detailBean = detailBean;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .detailModule(new DetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int getReHeaderView() {
        return R.layout.fragment_detail;
    }

    @Override
    public void initData() {
        if(detailBean!=null){
            iD = detailBean.getId();
        }
        headWrapView = (HeadWrapView) mYeHeaderView.findViewById(R.id.headWrapView);
        ivCover = (ImageView)mYeHeaderView.findViewById(R.id.ivCover);
        ivVerticalCover = (ImageView)mYeHeaderView.findViewById(R.id.ivVerticalCover);
        ivLiveCover = (ImageView)mYeHeaderView.findViewById(R.id.ivLiveCover);
        tvZan = (ThumbUpView) mYeHeaderView.findViewById(R.id.tvZan);
        tvCollect = (TextView) mYeHeaderView.findViewById(R.id.tvCollect);
        ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(detailBean!=null && !TextUtils.isEmpty(detailBean.getCover())){
                    ViewBigImageActivity.openImageSingleActivity(getActivity(),detailBean.getCover());
                }
            }
        });
        ivVerticalCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(detailBean!=null && !TextUtils.isEmpty(detailBean.getCover())){
                    ViewBigImageActivity.openImageSingleActivity(getActivity(),detailBean.getCover());
                }
            }
        });
        ivLiveCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(detailBean!=null && !TextUtils.isEmpty(detailBean.getCover())){
                    ViewBigImageActivity.openImageSingleActivity(getActivity(),detailBean.getCover());
                }
            }
        });
        tvZan.setCracksColor(Color.rgb(22, 33, 44));
        tvZan.setFillColor(Color.rgb(11, 200, 77));
        tvZan.setEdgeColor(Color.rgb(33, 3, 219));

        tvZan.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean isFollow) {
                mPresenter.follow(detailBean,isFollow);
            }
        });
        initHeader();
        mPresenter.getDetail(iD, true);
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
        return true;
    }

    @Override
    public boolean enableMore() {
        return false;
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
        mPresenter.getDetail(iD, true);
    }

    @Override
    public void onDataLoadMore() {
        mPresenter.getDetail(iD, false);
    }

    @Override
    public BaseQuickAdapter<BaseItem, BaseViewHolder> getAdapter() {
        return new CommonListAdapter(new ArrayList());
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void initHeader() {
        headWrapView.setMember(detailBean.getUser());
        ivCover.setVisibility(View.GONE);
        ivVerticalCover.setVisibility(View.GONE);
        ivLiveCover.setVisibility(View.GONE);
        if(detailBean.getType()==0){
            ivCover.setVisibility(View.VISIBLE);
            ImageLoadUtils.showImageView(getActivity(),ivCover,detailBean.getThumb());
        }else if(detailBean.getType()==1){
            ivVerticalCover.setVisibility(View.VISIBLE);
            ImageLoadUtils.showImageView(getActivity(),ivVerticalCover,detailBean.getThumb());
        }else{
            ivLiveCover.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(detailBean.getThumb()).into(ivLiveCover);
            //ImageLoadUtils.showImageView(getActivity(),ivVerticalCover,detailBean.getThumb());
        }
        tvCollect.setText(detailBean.getFavs()+"");
    }

}
