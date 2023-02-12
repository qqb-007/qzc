package jd.sdk;

/**
 * ClassName:WebResponseDTOTO <br/>
 * Function: 通用响应类   <br/>
 * Date:     2015年4月13日 下午4:11:31 <br/>
 *
 * @author zhoudeming
 */
public class Response<T> {

    /**
     * 0:成功，其他失败
     */
    private String code;

    /**
     * 描述
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 加密数据
     */
    private String encryptData;

    /**
     * 获取 code
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置 code
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取 msg
     *
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置 msg
     *
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * data
     *
     * @return  the data
     */

    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    public String getEncryptData() {
        return encryptData;
    }

    public void setEncryptData(String encryptData) {
        this.encryptData = encryptData;
    }

    /**
     * 成功结果 (code="0",msg="",data=data)
     * @param data
     * @return
     */
    public static <R> Response<R> success(R data){
        Response<R> result = new Response<R>();
        result.setCode("0");
        result.setMsg("");
        result.setData(data);
        result.setEncryptData(null);
        return result;
    }

    /**
     * 成功结果 (code="0",msg="",data=data)
     * @param data
     * @return
     */
    public static <R> Response<R> success(R data,String encryptData){
        Response<R> result = new Response<R>();
        result.setCode("0");
        result.setMsg("");
        result.setData(data);
        result.setEncryptData(encryptData);
        return result;
    }

    /**
     * 失败结果
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static <R> Response<R> error(String code,String msg,R data){
        Response<R> result = new Response<R>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        result.setEncryptData(null);
        return result;
    }

    /**
     * 失败结果
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static <R> Response<R> error(String code,String msg,R data,String encryptData){
        Response<R> result = new Response<R>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        result.setEncryptData(encryptData);
        return result;
    }

    /**
     * 失败结果
     * @param code
     * @param msg
     * @return
     */
    public static <R> Response<R> error(String code,String msg){
        Response<R> result = new Response<R>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        result.setEncryptData(null);
        return result;
    }

    /**
     * 是否成功！
     * @return
     */
    public boolean isSuccess(){
        return "0".equals(this.code);
    }

    @Override
    public String toString() {
        return "WebResponseDTO{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", encryptData='" + encryptData + '\'' +
                '}';
    }

}
