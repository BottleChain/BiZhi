package com.yeyue.library.utils;

import android.content.Context;
import android.widget.ImageView;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.base.App;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.yeyue.library.widgets.imageload.glide.YeGlideImageConfig;

import jp.wasabeef.glide.transformations.BlurTransformation;


/**
  *@describe 图片加载
  *@author li.xiao
  *@time 2017-8-31 9:20
  */
public class ImageLoadUtils {
    /**
     * 图片显示加载
     * @param context
     * @param imageView
     * @param url
     */
    public static void showImageView(Context context, ImageView imageView,String url){
        AppComponent mAppComponent = ((App) context.getApplicationContext()).getAppComponent();
        ImageLoader mImageLoader = mAppComponent.imageLoader();
        //判断网络是否为4G，4G网络不加载网络图片
        if(!StringUtils.isEmpty(url) && url.indexOf("http:")!=-1 && NetworkUtils.is4G()){
            url = "";
        }
        mImageLoader.loadImage(context,YeGlideImageConfig
                        .builder()
                        .url(url)
                        .imageView(imageView)
                        .build());
    }

    /**
     * 图片显示加载
     * @param context
     * @param imageView
     * @param url
     * @param placeholder
     */
    public static void showImageView(Context context, ImageView imageView,String url,int placeholder,int errorPic){
        AppComponent mAppComponent = ((App) context.getApplicationContext()).getAppComponent();
        ImageLoader mImageLoader = mAppComponent.imageLoader();
        //判断网络是否为4G，4G网络不加载网络图片
        if(!StringUtils.isEmpty(url) && url.indexOf("http:")!=-1 && NetworkUtils.is4G()){
            url = "";
        }
        mImageLoader.loadImage(context, YeGlideImageConfig
                .builder()
                .url(url)
                .imageView(imageView)
                .placeholder(placeholder)
                .errorPic(errorPic)
                .build());
    }
    /**
     * 添加图片高斯模糊
     * @param context
     * @param imageView
     * @param url
     * @param blurTransformation
     */
    public static void showImageView(Context context, ImageView imageView, String url, BlurTransformation blurTransformation){
        AppComponent mAppComponent = ((App) context.getApplicationContext()).getAppComponent();
        ImageLoader mImageLoader = mAppComponent.imageLoader();
        //判断网络是否为4G，4G网络不加载网络图片
        if(!StringUtils.isEmpty(url) && url.indexOf("http:")!=-1 && NetworkUtils.is4G()){
            url = "";
        }
        mImageLoader.loadImage(context, YeGlideImageConfig.builder().transformation(blurTransformation)
                .url(url).imageView(imageView).build());
    }

    public static void clear(Context context){
        AppComponent mAppComponent = ((App) context.getApplicationContext()).getAppComponent();
        ImageLoader mImageLoader = mAppComponent.imageLoader();
        mImageLoader.clear(mAppComponent.application(),YeGlideImageConfig.builder().isClearMemory(true).build());
    }
}
