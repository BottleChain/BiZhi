package com.yeyue.bizhi.entity;

import java.util.List;

/**
  *@describe 视频请求基类
  *@author Administrator
  *@time 2017/11/4 0004 下午 9:35
  */
public class HttpVideo<T> {

    private String msg;
    private ResBean<T> res;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResBean getRes() {
        return res;
    }

    public void setRes(ResBean res) {
        this.res = res;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResBean<T> {
        private List<T> videowp;

        public List<T> getVideowp() {
            return videowp;
        }

        public void setVideowp(List<T> videowp) {
            this.videowp = videowp;
        }
    }
}
