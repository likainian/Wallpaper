package com.qf.administrator.wallpaper.beans;

import java.util.List;

/**
 * Created by Administrator on 16/10/26.
 */

public class KeyWordBeans {

    /**
     * keyword : 汽车
     * imgs : ["http://bzpic.spriteapp.cn/picture2/M00/0C/39/wKiFWVRsDzGAeSvwAAFtGFI8Ifg950.jpg"]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String keyword;
        private List<String> imgs;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }
}
