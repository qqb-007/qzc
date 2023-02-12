package info.batcloud.wxc.core.exception;

import info.batcloud.wxc.core.context.StaticContext;

/**
 * Created by Administrator on 2016/1/26.
 */
public class BizException extends RuntimeException {

    public BizException(String text) {
        super(StaticContext.messageSource.getMessage(text, null, text, null));
    }

    public BizException(String text, Object[] params) {
        super(StaticContext.messageSource.getMessage(text, params, text, null));
    }

    public BizException(String text, Throwable throwable) {
        super(text, throwable);
    }

    public static BizException of(String text) {
        return new BizException(text);
    }

}
