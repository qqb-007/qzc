package info.batcloud.wxc.core.domain;

/**
 * Created by Administrator on 2017-07-29.
 */
public class SystemResponse<T> extends ApiResponse<T>{

    public SystemResponse(){
        super();
        this.setLevel(ApiResponseLevel.SYSTEM);
    }

}
