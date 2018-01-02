package com.yeyue.bizhi.ui.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.yeyue.bizhi.R;
import com.yeyue.bizhi.utils.ActivityUtils;
import com.yeyue.library.base.YeSettingFragment;
import com.yeyue.library.data.YeSettingItem;

/**
 * Created by li.xiao on 2017-12-27.
 */

public class MineFragment extends YeSettingFragment{
    @Override
    public int inflateMenu() {
        return R.xml.main_mine;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if(view!=null && view.getTag()!=null){
            if(view.getTag() instanceof YeSettingItem){
                YeSettingItem item = (YeSettingItem) view.getTag();
                if("换肤".equals(item.getTitle())){
                    ActivityUtils.openYeSkinActivity(getActivity());
                }else if("其他设置".equals(item.getTitle())){
                    ActivityUtils.openYeSettingActivity(getActivity());
                }else if("我的关注".equals(item.getTitle())){
                    ActivityUtils.openFollowActivity(getActivity());
                }
            }
        }
    }
}
