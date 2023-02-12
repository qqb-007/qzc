package info.batcloud.wxc.admin.handler;

import info.batcloud.wxc.admin.Application;
import info.batcloud.wxc.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = Application.class)
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger("exception.logger");

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseEntity<String> handleControllerException(Exception ex) {
        if (ex instanceof BizException) {
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>("未知错误，请联系管理员解决", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
