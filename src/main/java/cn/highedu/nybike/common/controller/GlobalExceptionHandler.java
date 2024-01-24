package cn.highedu.nybike.common.controller;
import cn.highedu.nybike.common.exception.ServiceCode;
import cn.highedu.nybike.common.exception.ServiceException;
import cn.highedu.nybike.common.pojo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 全局异常处理器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public JsonResult<Void> handleServiceException(ServiceException e) {
        // e.printStackTrace();
        log.warn(e.getMessage());
        return JsonResult.fail(e);
    }

    @ExceptionHandler
    public JsonResult handleBindException(BindException e) {
        String message = e.getFieldError().getDefaultMessage();
        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST, message);
    }
}
