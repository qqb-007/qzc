package me.ele.sdk.up.request;

import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.PictureUploadResponse;

public class PictureUploadRequest extends Request<PictureUploadResponse> {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "picture.upload";
    }

    @Override
    public Class<PictureUploadResponse> getResponseClass() {
        return PictureUploadResponse.class;
    }
}
