/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yeyue.bizhi.widgets;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.yeyue.bizhi.R;
import com.yeyue.bizhi.entity.CommentBean;
import com.yeyue.bizhi.entity.UserBean;
import com.yeyue.bizhi.utils.ActivityUtils;

/**
  *@describe 评论回复
  *@author li.xiao
  *@time 2017-12-6 15:16
  */
public class CommentReplayTextView extends TextView {

    public CommentReplayTextView(Context context) {
        this(context, null);
    }

    public CommentReplayTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentReplayTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setComment(CommentBean comment){
        if(comment==null){
            setText("");
            return;
        }
        if(comment.getReply_user()==null || TextUtils.isEmpty(comment.getReply_user().getName())){
            setText(comment.getContent());
            return;
        }
        CommentBean.ReplyUserBean reply = comment.getReply_user();
        String replyUser = "回复 "+reply.getName()+":";
        int end = replyUser.length();

        SpannableString spanableInfo = new SpannableString(replyUser+comment.getContent());
        spanableInfo.setSpan(new Clickable(reply), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        append(spanableInfo);
        //setMovementMethod()该方法必须调用，否则点击事件不响应
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    class Clickable extends ClickableSpan {
        private CommentBean.ReplyUserBean user;

        public Clickable(CommentBean.ReplyUserBean name) {
            super();
            this.user = name;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(ContextCompat.getColor(getContext(), R.color.color_Hint_Word));
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View v) {
            if(user!=null && getContext()!=null && getContext()instanceof Activity){
                UserBean userBean = new UserBean();
                userBean.setId(user.getId());
                ActivityUtils.openUserActivity((Activity) getContext(),userBean);
            }
        }
    }
}
