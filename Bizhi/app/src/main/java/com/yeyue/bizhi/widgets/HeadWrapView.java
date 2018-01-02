package com.yeyue.bizhi.widgets;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yeyue.bizhi.R;
import com.yeyue.bizhi.entity.UserBean;
import com.yeyue.bizhi.utils.ActivityUtils;
import com.yeyue.bizhi.utils.TimeUtils;
import com.yeyue.library.utils.ImageLoadUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author xiaoli
 * @describe 用户头像信息
 * @time 2016/10/11 10:12
 */
public class HeadWrapView extends FrameLayout {
    private final static int TYPE_HEADER =1; //详情页头部
    private final static int TYPE_COMMENT =2; //评论
    private final static int TYPE_RANK =3; //排名
    private CircleImageView ivHeader;
    private TextView tvNickname;
    private TextView tvTime;

    private int type;
    private UserBean userBean;
    private boolean is_limit_nickname = false;

    public HeadWrapView(Context context) {
        super(context);
        init(context,null);
    }

    public HeadWrapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @TargetApi(21)
    public HeadWrapView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    public HeadWrapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        if(attrs!=null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HeadWarp);
            type = a.getInt(R.styleable.HeadWarp_headType,TYPE_HEADER);
            a.recycle();
        }
        View view = null;
        view = LayoutInflater.from(getContext()).inflate(R.layout.common_head_wrap_view, this,
                true);
        initView(view);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick();
            }
        });
    }

    public void handleClick() {
        if(userBean!=null && getContext()!=null && getContext()instanceof Activity){
            ActivityUtils.openUserActivity((Activity) getContext(),userBean);
        }
    }

    private void initView(View rootView) {
        ivHeader = (CircleImageView) rootView.findViewById(R.id.ivHeader);
        tvNickname = (TextView) rootView.findViewById(R.id.tvNickname);
        tvTime = (TextView) rootView.findViewById(R.id.tvTime);
    }

    public void setMember(UserBean member) {
        if(member==null){
            return;
        }
        userBean = member;
        int nicknameColor = R.color.color_Main_Body;
        switch (type){
            case TYPE_HEADER:
                nicknameColor = R.color.color_Main_Body;
                break;
            case TYPE_COMMENT:
                nicknameColor = R.color.color_Hint_Word;
                break;
            case TYPE_RANK:
                nicknameColor = R.color.color_Hint_Word;
                break;
        }
        Resources resource = (Resources) getContext().getResources();
        ColorStateList csl = (ColorStateList) resource.getColorStateList(nicknameColor);
        tvNickname.setTextColor(csl);

        ImageLoadUtils.showImageView(getContext(),ivHeader,member.getAvatar());
        tvNickname.setText(member.getName());

        tvTime.setText(TimeUtils.getDateString(member.getAtime()*1000,3));
    }
    static float dpToPx(Resources resources, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                resources.getDisplayMetrics());
    }
}
