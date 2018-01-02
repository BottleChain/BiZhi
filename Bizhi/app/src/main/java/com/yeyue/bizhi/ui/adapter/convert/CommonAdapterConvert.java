package com.yeyue.bizhi.ui.adapter.convert;

import android.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseViewHolder;
import com.yeyue.bizhi.BR;
import com.yeyue.bizhi.R;
import com.yeyue.bizhi.constant.AdapterConstant;
import com.yeyue.bizhi.entity.AlbumBean;
import com.yeyue.bizhi.entity.BizhiBean;
import com.yeyue.bizhi.entity.ComHeader;
import com.yeyue.bizhi.entity.CommentBean;
import com.yeyue.bizhi.entity.DayRecommend;
import com.yeyue.bizhi.entity.LiveBean;
import com.yeyue.bizhi.entity.VerCategory;
import com.yeyue.bizhi.entity.VideoBean;
import com.yeyue.bizhi.entity.VideoCategory;
import com.yeyue.bizhi.entity.WalCategory;
import com.yeyue.bizhi.utils.TimeUtils;
import com.yeyue.library.data.BaseItem;
/**
  *@describe 通用视图
  *@author li.xiao
  *@time 2017-9-25 16:27
  */
public class CommonAdapterConvert {
    public static void convert(BaseViewHolder helper, BaseItem data,ViewDataBinding binding){
        switch (helper.getItemViewType()) {
            case AdapterConstant.ITEM_COMMON_HEADER:
                if(data !=null && data instanceof ComHeader){
                    ComHeader item = (ComHeader) data;
                    helper.setVisible(R.id.vLeftDefault,false);
                    helper.setVisible(R.id.ivIcon,false);
                    helper.setTextColor(R.id.tvDefault, helper.getConvertView().getContext().getResources().getColor(R.color.color_Main_Body));
                    switch (item.getType()){
                        case ComHeader.DEFAULT:
                            helper.setVisible(R.id.vLeftDefault,true);
                            break;
                        case ComHeader.ICON_INSET:
                            helper.setVisible(R.id.ivIcon,true);
                            helper.setBackgroundRes(R.id.ivIcon,item.getLeftIcon());
                            helper.setTextColor(R.id.tvDefault, helper.getConvertView().getContext().getResources().getColor(R.color.color_Hint_Word));
                            break;
                        case ComHeader.NO_LEFT_ICON:
                            break;
                    }
                    binding.setVariable(BR.title,item.getTitle());
                }
                break;
            case AdapterConstant.ITEM_HOMEPAGE_RECOMMEND:
            case AdapterConstant.ITEM_BIZHI_DEFAULT:
            case AdapterConstant.ITEM_VERTICAL_BIZHI:
                if(data !=null && data instanceof BizhiBean){
                    BizhiBean item = (BizhiBean) data;
                    binding.setVariable(BR.image,item.getThumb());
                    binding.setVariable(BR.bizhi,item);
                }
                break;
            case AdapterConstant.ITEM_DAY_RECOMMEND:
                if(data !=null && data instanceof DayRecommend){
                    DayRecommend item = (DayRecommend) data;
                    //根据时间戳获取月份和时间
                    String day = TimeUtils.timeFormat(item.getStime(),"dd");
                    String month = "/"+TimeUtils.timeFormat(item.getStime(),"MM")+"月";
                    binding.setVariable(BR.dayTime,day);
                    binding.setVariable(BR.month,month);
                    binding.setVariable(BR.day,item);
                }
                break;
            case AdapterConstant.ITEM_WALLPAPER_CATEGORY:
                if(data !=null && data instanceof WalCategory.CategoryBean){
                    WalCategory.CategoryBean item = (WalCategory.CategoryBean) data;
                    binding.setVariable(BR.category,item);
                }
                break;
            case AdapterConstant.ITEM_VERTICAL_CATEGORY:
                if(data !=null && data instanceof VerCategory){
                    VerCategory item = (VerCategory) data;
                    binding.setVariable(BR.category,item);
                }
                break;
            case AdapterConstant.ITEM_ALBUM_DEFAULT:
                if(data !=null && data instanceof AlbumBean){
                    AlbumBean item = (AlbumBean) data;
                    binding.setVariable(BR.album,item);
                }
                break;
            case AdapterConstant.ITEM_VIDEO_DEFAULT:
                if(data !=null && data instanceof VideoBean){
                    VideoBean item = (VideoBean) data;
                    binding.setVariable(BR.video,item);
                }
                break;
            case AdapterConstant.ITEM_VIDEO_CATEGORY:
                if(data !=null && data instanceof VideoCategory){
                    VideoCategory item = (VideoCategory) data;
                    binding.setVariable(BR.category,item);
                }
                break;
            case AdapterConstant.ITEM_BIZHI_DEATIL:
            case AdapterConstant.ITEM_VERTICAL_DETAIL:
                if(data !=null && data instanceof BizhiBean){
                    BizhiBean item = (BizhiBean) data;
                    binding.setVariable(BR.user,item.getUser());
                    binding.setVariable(BR.bizhi,item);
                }
                break;
            case AdapterConstant.ITEM_COMMENT_DEFAULT:
                if(data !=null && data instanceof CommentBean){
                    CommentBean item = (CommentBean) data;
                    binding.setVariable(BR.comment,item);
                    binding.setVariable(BR.user,item.getUser());
                }
                break;
            case AdapterConstant.ITEM_LIVE_DEFAULT:
                if(data !=null && data instanceof LiveBean){
                    LiveBean item = (LiveBean) data;
                    binding.setVariable(BR.live,item);
                }
                break;
        }
    }
}
