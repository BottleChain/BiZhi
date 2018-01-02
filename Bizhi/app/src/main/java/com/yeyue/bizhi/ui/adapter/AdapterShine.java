package com.yeyue.bizhi.ui.adapter;

/**
 * Created by li.xiao on 2017-9-25.
 */

import com.yeyue.bizhi.R;
import com.yeyue.bizhi.constant.AdapterConstant;

import java.util.HashMap;
import java.util.Map;

/**
  *@describe Adapter映射关系，
  *@author li.xiao
  *@time 2017-9-25 16:17
  */
public class AdapterShine{
    public static Map<Integer,Integer> fetchAdapterMap(){
        Map<Integer,Integer> maps = new HashMap<>();
        /*******************通用视图 START*******************/
        /*******************通用视图 END*******************/
        maps.put(AdapterConstant.ITEM_COMMON_HEADER, R.layout.item_common_header);
        maps.put(AdapterConstant.ITEM_BIZHI_DEFAULT, R.layout.item_bizhi_default);
        maps.put(AdapterConstant.ITEM_DAY_RECOMMEND, R.layout.item_day_recommend);
        maps.put(AdapterConstant.ITEM_HOMEPAGE_RECOMMEND, R.layout.item_home_recommend);
        maps.put(AdapterConstant.ITEM_WALLPAPER_CATEGORY, R.layout.item_wallpaper_category);
        maps.put(AdapterConstant.ITEM_VERTICAL_BIZHI, R.layout.item_vertical_bizhi);
        maps.put(AdapterConstant.ITEM_VERTICAL_CATEGORY, R.layout.item_vertical_category);
        maps.put(AdapterConstant.ITEM_ALBUM_DEFAULT, R.layout.item_album_default);
        maps.put(AdapterConstant.ITEM_VIDEO_DEFAULT, R.layout.item_video_default);
        maps.put(AdapterConstant.ITEM_VIDEO_CATEGORY, R.layout.item_video_category);
        maps.put(AdapterConstant.ITEM_BIZHI_DEATIL, R.layout.item_bizhi_detail);
        maps.put(AdapterConstant.ITEM_VERTICAL_DETAIL, R.layout.item_vertical_bizhi);
        maps.put(AdapterConstant.ITEM_COMMENT_DEFAULT, R.layout.item_comment_default);
        maps.put(AdapterConstant.ITEM_LIVE_DEFAULT, R.layout.item_live_default);
        return maps;
    }
}
