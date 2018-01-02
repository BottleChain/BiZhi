package com.yeyue.bizhi.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeyue.bizhi.R;
import com.yeyue.bizhi.constant.AdapterConstant;
import com.yeyue.library.data.BaseExpandItem;

import java.util.List;

/**
  *@describe 树形结果
  *@author li.xiao
  *@time 2017-10-24 17:14
  */
public class ExpandListAdapter extends BaseMultiItemQuickAdapter<BaseExpandItem, BaseViewHolder> {

    public ExpandListAdapter(List data) {
        super(data);
       // addItemType(AdapterConstant.ITEM_BOOK_RANK_TYPE,R.layout.item_rank_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseExpandItem data) {
        ViewDataBinding binding = (ViewDataBinding) helper.itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        switch (helper.getItemViewType()) {
            case AdapterConstant.ITEM_HOME_GANK:

                break;
        }
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }
}