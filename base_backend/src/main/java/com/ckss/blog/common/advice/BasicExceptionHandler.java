package com.ckss.blog.common.advice;

import com.ckss.blog.common.exception.BlogException;
import com.ckss.blog.common.web.domain.AjaxResult;
import com.ckss.blog.common.web.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @className: BlException
 * @description: 全局异常处理
 * @author: cksspk
 * @date: 2020/3/24
 **/

@Slf4j
@ControllerAdvice
public class BasicExceptionHandler {

    /**
     * 返回异常2
     * @param e
     * @return
     */
    @ExceptionHandler(BlogException.class)
    @ResponseBody
    public Result handleException(BlogException e) {
        log.info("BlException:"+e.getMessage());
        return Result.error().message(e.getMessage()).code(e.getCode());
    }


    //待修改2020年3月28日
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleException(RuntimeException e) {
        log.info("BlException:"+e.getMessage());
        return AjaxResult.error(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    }


}
