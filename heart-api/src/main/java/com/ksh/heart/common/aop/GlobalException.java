package com.ksh.heart.common.aop;

import com.ksh.heart.common.base.BaseException;
import com.ksh.heart.common.enumeration.RetEnum;
import com.ksh.heart.common.log.LogManager;
import com.ksh.heart.common.log.factory.LogTaskFactory;
import com.ksh.heart.common.utils.R;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 */
@ControllerAdvice
public class GlobalException extends BaseException {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R exception(IllegalArgumentException e) {
        e.printStackTrace();
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R exception(IllegalStateException e) {
        e.printStackTrace();
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R exception(BindException e) {
        e.printStackTrace();
        return R.fail(e.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R exception(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return R.fail(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R exception(Exception e) {
        e.printStackTrace();
        return R.fail(RetEnum.SERVER_EXCEPTION.getRet(),e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public R notFount(RuntimeException e) {
        e.printStackTrace();
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(null, e.getMessage()));
        return R.fail(RetEnum.SERVER_EXCEPTION.getRet(), RetEnum.SERVER_EXCEPTION.getMsg());
    }
}
