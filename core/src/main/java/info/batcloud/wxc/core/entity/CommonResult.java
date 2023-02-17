package info.batcloud.wxc.core.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;


/**
 * @ClassName: CommonResult
 * @Description:
 * @Author V
 * @Date 22/4/2022
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
    private String msg;
    private Integer code;
    private  T      data;


    public CommonResult(String message, Integer code){
        this(message,code,null);
    }
    public CommonResult(T data){
        this("数据获取成功",200,data);
    }
    public CommonResult(String message){
        this(message,200);
    }
    public  CommonResult fail(){
        return new CommonResult("数据获取失败,请稍后再试",500);
    }
    public CommonResult success(T data){
        return new CommonResult("数据获取成功",200,data);
    }
    public  CommonResult fail(Exception e){
        return new CommonResult(e.getMessage(),500);
    }
}
