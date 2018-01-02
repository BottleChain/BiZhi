package com.yeyue.bizhi.utils;

import android.app.Activity;
import android.view.View;

import com.yeyue.bizhi.entity.AlbumBean;
import com.yeyue.bizhi.entity.BizhiBean;
import com.yeyue.bizhi.entity.DetailBean;
import com.yeyue.bizhi.entity.GankBean;
import com.yeyue.bizhi.entity.LiveBean;
import com.yeyue.bizhi.entity.UserBean;
import com.yeyue.bizhi.entity.VerCategory;
import com.yeyue.bizhi.entity.VideoBean;
import com.yeyue.bizhi.entity.VideoCategory;
import com.yeyue.bizhi.entity.WalCategory;
import com.yeyue.library.base.YeWebActivity;
import com.yeyue.library.widgets.viewbigimage.ViewBigImageActivity;

/**
  *@describe
  *@author li.xiao
  *@time 2017-11-2 13:59
  */
public class BindingUtils {
    public static void openGankDetail(View view, GankBean gankBean){
        if(gankBean!=null && view!=null){
            String url = gankBean.getUrl();
            if("福利".equals(gankBean.getType())){
                ViewBigImageActivity.openImageSingleActivity((Activity) view.getContext(),url);
            }else{
                YeWebActivity.loadUrl(view.getContext(),url,gankBean.getDesc());
            }
        }
    }

    public static void oepnBizhiDetail(View view, BizhiBean bizhiBean,int type){
        if(bizhiBean!=null && view!=null && view.getContext()!=null && view.getContext() instanceof Activity){
            DetailBean detailBean = new DetailBean();
            if(bizhiBean.getUser()==null){
                UserBean userBean = new UserBean();
                userBean.setName("一念夕雾");
                userBean.setAvatar("http://img0.adesk.com/download/59cf77ec0422085f6f76f58d");
                userBean.setId("5965cd0be7bce7312ef79fbf");
                detailBean.setUser(userBean);
            }else{
                detailBean.setUser(bizhiBean.getUser());
            }
            detailBean.setId(bizhiBean.getId());
            detailBean.setCover(bizhiBean.getPreview());
            detailBean.setThumb(bizhiBean.getThumb());
            detailBean.setRank(bizhiBean.getRank());
            detailBean.setFavs(bizhiBean.getFavs());
            detailBean.setType(type);
            ActivityUtils.openDetailActivity((Activity) view.getContext(),detailBean);
        }
    }

    public static void oepnLiveDetail(View view, LiveBean liveBean){
        if(liveBean!=null && view!=null && view.getContext()!=null && view.getContext() instanceof Activity){
            DetailBean detailBean = new DetailBean();
            UserBean userBean = new UserBean();
            userBean.setName("一念夕雾");
            userBean.setAvatar("http://img0.adesk.com/download/59cf77ec0422085f6f76f58d");
            userBean.setId("5965cd0be7bce7312ef79fbf");
            detailBean.setId(liveBean.getId());
            detailBean.setUser(userBean);
            detailBean.setCover(liveBean.getPreview());
            detailBean.setThumb(liveBean.getPreview());
            detailBean.setRank(liveBean.getRank());
            detailBean.setFavs(liveBean.getFavs());
            detailBean.setType(2);
            ActivityUtils.openDetailActivity((Activity) view.getContext(),detailBean);
        }
    }
    public static void openDayRecommendActivity(View view){
        if( view!=null && view.getContext()!=null && view.getContext() instanceof Activity){
            ActivityUtils.openDayRecommendActivity((Activity) view.getContext());
        }
    }

    public static void openCategoryActivity(View view, WalCategory.CategoryBean categoryBean){
        if( view!=null  && categoryBean!=null && view.getContext()!=null && view.getContext() instanceof Activity){
            ActivityUtils.openCategoryActivity((Activity) view.getContext(),categoryBean);
        }
    }
    public static void openCategoryActivity(View view, VerCategory categoryBean){
        if( view!=null  && categoryBean!=null && view.getContext()!=null && view.getContext() instanceof Activity){
            ActivityUtils.openCategoryActivity((Activity) view.getContext(),categoryBean);
        }
    }
    public static void openCategoryActivity(View view, VideoCategory videoCategory){
        if( view!=null  && videoCategory!=null && view.getContext()!=null && view.getContext() instanceof Activity){
            ActivityUtils.openCategoryActivity((Activity) view.getContext(),videoCategory);
        }
    }
    public static void openAlbumDetailActivity(View view, AlbumBean albumBean){
        if( view!=null  && albumBean!=null && view.getContext()!=null && view.getContext() instanceof Activity){
            ActivityUtils.openAlbumDetailActivity((Activity) view.getContext(),albumBean);
        }
    }

    public static void oepnVideoDetail(View view, VideoBean videoBean){
        if( view!=null  && videoBean!=null && view.getContext()!=null && view.getContext() instanceof Activity){
            ViewBigImageActivity.openImageSingleActivity((Activity) view.getContext(),videoBean.getView_video());
        }
    }
}
