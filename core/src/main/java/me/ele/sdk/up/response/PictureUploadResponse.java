package me.ele.sdk.up.response;

import me.ele.sdk.up.Response;

public class PictureUploadResponse extends Response<PictureUploadResponse.Data, String> {

    public static class Data {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
