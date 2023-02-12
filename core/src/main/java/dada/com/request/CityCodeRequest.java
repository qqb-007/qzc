package dada.com.request;

import dada.com.response.CityCodeResponse;

public class CityCodeRequest extends Request<CityCodeResponse> {
    @Override
    public String getUrl() {
        return "/api/cityCode/list";
    }

    @Override
    public Class<CityCodeResponse> getResponseClass() {
        return CityCodeResponse.class;
    }
}
