package com.sankuai.meituan.waimai.opensdk.vo;

public class VideoParam {
    private String app_poi_code;
    private String video_url_mp4;
    private Long video_id;
    private int video_state;

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public void setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
    }

    public String getVideo_url_mp4() {
        return video_url_mp4;
    }

    public void setVideo_url_mp4(String video_url_mp4) {
        this.video_url_mp4 = video_url_mp4;
    }

    public Long getVideo_id() {
        return video_id;
    }

    public void setVideo_id(Long video_id) {
        this.video_id = video_id;
    }

    public int getVideo_state() {
        return video_state;
    }

    public void setVideo_state(int video_state) {
        this.video_state = video_state;
    }

    @Override
    public String toString() {
        return "VideoParam{" +
                "app_poi_code='" + app_poi_code + '\'' +
                ", video_url_mp4='" + video_url_mp4 + '\'' +
                ", video_id=" + video_id +
                ", video_state=" + video_state +
                '}';
    }
}
