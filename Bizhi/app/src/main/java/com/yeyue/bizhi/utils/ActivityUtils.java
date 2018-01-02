package com.yeyue.bizhi.utils;

import android.app.Activity;
import android.content.Intent;

import com.yeyue.bizhi.constant.Constant;
import com.yeyue.bizhi.entity.AlbumBean;
import com.yeyue.bizhi.entity.DetailBean;
import com.yeyue.bizhi.entity.UserBean;
import com.yeyue.bizhi.entity.VerCategory;
import com.yeyue.bizhi.entity.VideoCategory;
import com.yeyue.bizhi.entity.WalCategory;
import com.yeyue.bizhi.ui.activity.AlbumDetailActivity;
import com.yeyue.bizhi.ui.activity.CategoryActivity;
import com.yeyue.bizhi.ui.activity.DayRecommendActivity;
import com.yeyue.bizhi.ui.activity.DetailActivity;
import com.yeyue.bizhi.ui.activity.FollowActivity;
import com.yeyue.bizhi.ui.activity.SearchActivity;
import com.yeyue.bizhi.ui.activity.SettingActivity;
import com.yeyue.bizhi.ui.activity.SkinActivity;
import com.yeyue.bizhi.ui.activity.UserActivity;
import com.yeyue.library.base.YeSkinDetailActivity;
import com.yeyue.library.constant.YeSkinConstant;
import com.yeyue.library.data.YeSkinItem;

/**
  *@describe Activity启动类
  *@author li.xiao
  *@time 2017-12-5 17:05
  */
public class ActivityUtils {
    public static void openDetailActivity(Activity activity, DetailBean detailBean){
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(Constant.DETAIL_INFO,detailBean);
        activity.startActivity(intent);
    }

    public static void openAlbumDetailActivity(Activity activity, AlbumBean albumBean){
        Intent intent = new Intent(activity, AlbumDetailActivity.class);
        intent.putExtra(Constant.ALBUM_INFO,albumBean);
        activity.startActivity(intent);
    }

    public static void openDayRecommendActivity(Activity activity){
        Intent intent = new Intent(activity, DayRecommendActivity.class);
        activity.startActivity(intent);
    }

    public static void openCategoryActivity(Activity activity,WalCategory.CategoryBean categoryBean){
        Intent intent = new Intent(activity, CategoryActivity.class);
        intent.putExtra(Constant.CATEGORY_ID,categoryBean);
        activity.startActivity(intent);
    }
    public static void openCategoryActivity(Activity activity,VerCategory categoryBean){
        Intent intent = new Intent(activity, CategoryActivity.class);
        intent.putExtra(Constant.VERTICAL_CATEGORY_ID,categoryBean);
        activity.startActivity(intent);
    }
    public static void openCategoryActivity(Activity activity,VideoCategory videoCategory){
        Intent intent = new Intent(activity, CategoryActivity.class);
        intent.putExtra(Constant.VIDEO_CATEGORY_ID,videoCategory);
        activity.startActivity(intent);
    }
    public static void openUserActivity(Activity activity,UserBean userBean){
        Intent intent = new Intent(activity, UserActivity.class);
        intent.putExtra(Constant.USER_INFO,userBean);
        activity.startActivity(intent);
    }
    public static void openSearchActivity(Activity activity){
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent);
    }

    public static void openYeSkinActivity(Activity activity){
        Intent intent = new Intent(activity, SkinActivity.class);
        activity.startActivity(intent);
    }

    public static void openYeSkinDetailActivity(Activity activity,YeSkinItem yeSkinImage){
        Intent intent = new Intent(activity, YeSkinDetailActivity.class);
        intent.putExtra(YeSkinConstant.YE_SKIN_INFO,yeSkinImage);
        activity.startActivity(intent);
    }

    public static void openYeSettingActivity(Activity activity){
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }
    public static void openFollowActivity(Activity activity){
        Intent intent = new Intent(activity, FollowActivity.class);
        activity.startActivity(intent);
    }
}
