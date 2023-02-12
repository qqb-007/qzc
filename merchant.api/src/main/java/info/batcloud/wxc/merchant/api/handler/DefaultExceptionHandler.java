package info.batcloud.wxc.merchant.api.handler;

import info.batcloud.wxc.core.domain.ApiResponse;
import info.batcloud.wxc.core.domain.SystemResponse;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.merchant.api.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice(basePackageClasses = Application.class)
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger("exception.logger");

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseEntity<ApiResponse> handleControllerException(Exception ex) {
        ApiResponse apiResponse = new SystemResponse();
        apiResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiResponse.setSuccess(false);
        if (ex instanceof BizException) {
            apiResponse.setErrMsg(ex.getLocalizedMessage());
            logger.info(ex.getMessage(), ex);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else if (ex instanceof BadCredentialsException || ex instanceof AccessDeniedException) {
            apiResponse.setErrMsg("没有权限，请联系管理员解决");
            apiResponse.setCode(HttpStatus.FORBIDDEN.value());
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
        }
        logger.error(ex.getMessage(), ex);
        apiResponse.setErrMsg("未知错误，请联系管理员解决");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
