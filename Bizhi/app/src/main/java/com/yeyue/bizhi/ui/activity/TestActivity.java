package com.yeyue.bizhi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yeyue.bizhi.R;
import com.yeyue.bizhi.entity.UserBean;
import com.yeyue.library.base.YeHeaderContentActivity;
import com.yeyue.library.utils.ImageLoadUtils;

import butterknife.BindView;

/**
 * Created by li.xiao on 2017-12-12.
 */

public class TestActivity extends YeHeaderContentActivity {

    @BindView(R.id.ivCover)
    ImageView ivCover;
    @BindView(R.id.tvDesc)
    TextView tvDesc;

    @Override
    public int setHeaderId() {
        return R.layout.activity_album_detail_header;
    }

    @Override
    public int setContentId() {
        return R.layout.yeyue_callback_empty;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        UserBean userBean = new UserBean();
        userBean.setName("一念夕雾");
        userBean.setAvatar("http://img0.adesk.com/download/59cf77ec0422085f6f76f58d");
        userBean.setId("5965cd0be7bce7312ef79fbf");
        ImageLoadUtils.showImageView(getActivity(),ivCover,userBean.getAvatar());
        tvDesc.setText(userBean.getName());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

}
