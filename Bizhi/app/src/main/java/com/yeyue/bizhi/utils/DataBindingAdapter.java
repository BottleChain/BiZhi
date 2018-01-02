package com.yeyue.bizhi.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yeyue.bizhi.R;
import com.yeyue.bizhi.entity.CommentBean;
import com.yeyue.bizhi.entity.UserBean;
import com.yeyue.bizhi.widgets.CommentReplayTextView;
import com.yeyue.bizhi.widgets.HeadWrapView;
import com.yeyue.library.utils.ImageLoadUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
  *@describe 数据绑定操作
  *@author li.xiao
  *@time 2017-10-24 15:04
  */
public class DataBindingAdapter {
    /**
     * 设置mipmap资源图片
     */
    @BindingAdapter("android:mipmapSrc")
    public static void mipmapSrc(ImageView imageView, int resouce) {
        imageView.setImageResource(resouce);
    }

    /**
     * 设置图片
     */
    @BindingAdapter("android:showImgBg")
    public static void showImgBg(ImageView imageView, String url) {
        ImageLoadUtils.showImageView(imageView.getContext(),imageView,  url, R.color.color_placeholder,R.color.color_errorPic);
    }

    /**
     * 设置用户信息
     * @param headWrapView
     * @param userBean
     */
    @BindingAdapter("android:showUser")
    public static void showImgBg(HeadWrapView headWrapView, UserBean userBean) {
        headWrapView.setMember(userBean);
    }

    /**
     * 设置图片
     */
    @BindingAdapter("android:showDayRecommend")
    public static void showDayRecommend(TextView textView, long time) {
        String showTime = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM月");
        Date date = new Date(time/1000);
        showTime = simpleDateFormat.format(date);
        textView.setText(showTime+"");
    }

    /**
     * 设置评论
     * @param replayTextView
     * @param comment
     */
    @BindingAdapter("android:showComment")
    public static void showImgBg(CommentReplayTextView replayTextView, CommentBean comment) {
        replayTextView.setComment(comment);
    }
}
