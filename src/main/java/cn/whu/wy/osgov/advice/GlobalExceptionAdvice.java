package cn.whu.wy.osgov.advice;

import cn.whu.wy.osgov.dto.response.ResponseCode;
import cn.whu.wy.osgov.dto.response.ResponseEntity;
import cn.whu.wy.osgov.exception.UserNotFundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author WangYong
 * Date 2022/05/06
 * Time 11:38
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(UserNotFundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity userNotFoundHandler(UserNotFundException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new ResponseEntity(ResponseCode.USER_NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity commonExceptionHandler(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new ResponseEntity(1, e.getMessage());
    }

    // 可继续定义一些其他的异常处理器
}
