package info.batcloud.wxc.core.domain;

/**
 * Created by Administrator on 2017-07-29.
 */
public class BusinessResponse<T> extends ApiResponse<T>{

    public BusinessResponse(){
        super();
        this.setLevel(ApiResponseLevel.BUSINESS);
    }

    public BusinessResponse(T data) {
        this();
        this.setData(data);
    }

    public static <T> BusinessResponse<T> ok(T data) {
        BusinessResponse<T> response = new BusinessResponse<>(data);
        response.setSuccess(true);
        return response;
    }

    public static <T> BusinessResponse<T> error(String errMsg) {
        BusinessResponse<T> response = new BusinessResponse<>();
        response.setErrMsg(errMsg);
        response.setSuccess(false);
        return response;
    }

}
