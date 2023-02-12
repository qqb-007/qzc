package info.batcloud.wxc.core.domain;

import info.batcloud.wxc.core.context.StaticContext;

public class Result {

    private boolean success;

    private String code;

    private String msg;

    private Object[] args;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void setCode(String code, Object... args) {
        this.code = code;
        this.args = args;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return StaticContext.messageSource.getMessage(code, args, code, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
