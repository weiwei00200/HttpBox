package com.sammie.test;

public class AdResponse {

    /**
     * code : 0
     * msg : 获取成功
     * data : {"advert_id":10,"img_url":"https://img.xhuijia.com/advert/ogju9CF3l4S3E3oie3xBBFwrfkghkAHhCyhX5K3A.png","url":"https://school.xhuijia.com/app-start-page/","start_time":"2018-06-04 12:13:42","end_time":"2018-07-04 12:13:38","created_time":"2018-06-04 12:13:42"}
     */

    private int code;
    private String msg;
    private AdInfo data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AdInfo getData() {
        return data;
    }

    public void setData(AdInfo data) {
        this.data = data;
    }

    public static class AdInfo {
        /**
         * advert_id : 10
         * img_url : https://img.xhuijia.com/advert/ogju9CF3l4S3E3oie3xBBFwrfkghkAHhCyhX5K3A.png
         * url : https://school.xhuijia.com/app-start-page/
         * start_time : 2018-06-04 12:13:42
         * end_time : 2018-07-04 12:13:38
         * created_time : 2018-06-04 12:13:42
         */

        private int advert_id;
        private String img_url;
        private String url;
        private String start_time;
        private String end_time;
        private String created_time;

        public int getAdvert_id() {
            return advert_id;
        }

        public void setAdvert_id(int advert_id) {
            this.advert_id = advert_id;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }
    }
}
