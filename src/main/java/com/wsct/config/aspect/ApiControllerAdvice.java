package com.wsct.config.aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import com.wsct.util.json.GlobalReturnCode;
import com.wsct.util.json.JsonResult;
import com.wsct.util.string.StringUtil;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
/**
 * 该类主要解决以下问题
 * 1、处理api接口的跨域
 * 2、处理conttroller层的异常，返回状态为500
 * 3、处理请求的参数异常，返回状态为400
 *
 */
@ControllerAdvice("com.wsct.*.controller")
public class ApiControllerAdvice {
    /**
     * 定义日志处理
     */
    private static Logger logger = LoggerFactory.getLogger("controllerLog");


    /**
     * 系统异常处理，比如：404,500
     *
     * @param request  request
     * @param response response
     * @param e        异常
     * @return JsonResult结构
     * @throws Exception 异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        logger.error("系统异常", e);
        logException(request);
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new JsonResult(false, GlobalReturnCode.SYSTEM_PATH_NOEXIST);
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new JsonResult(false, GlobalReturnCode.SYSTEM_ERROR);
        }
    }

  
    /**
     * 打印所有异常信息
     *
     * @param request request
     */
    private void logException(HttpServletRequest request) {
        try {
            logger.error("请求路径：" + request.getServletPath());
            logger.error("请求参数：" + request.getParameterMap().toString());
            logger.error("请求header:" + StringUtil.getHeaderValue(request.getHeaderNames(), request));
            logger.error("请求body:" + StringUtil.getBodyString(request.getReader()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 拦截@Valid请求参数验证不通过的异常
     *
     * @param request   request
     * @param response  response
     * @param exception 验证不通过的异常
     * @return 执行结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @Order(0)
    public JsonResult handler(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException exception) {
        logger.info("请求的参数不正确", exception);
        logException(request);
        String validation_message;
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult != null && bindingResult.getFieldError() != null) {
            validation_message = bindingResult.getFieldError().getDefaultMessage();
        } else {
            validation_message = exception.getMessage();
        }
        logger.info("参数错误信息：" + validation_message);
        return new JsonResult(false, GlobalReturnCode.PARAM_ERROR, validation_message);
    }
    /**
     * 400 - Bad Request
     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public String handleMissingServletRequestParameterException(HttpServletRequest request, HttpServletResponse response,MissingServletRequestParameterException e) {
//        logger.error("缺少请求参数", e);
//        logException(request);
//        return "缺少请求参数";
//    }
//    /**
//     * 400 - Bad Request
//     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public String handleHttpMessageNotReadableException(HttpServletRequest request, HttpServletResponse response,HttpMessageNotReadableException e) {
//        logger.error("参数解析失败", e);
//        logException(request);
//        return "参数解析失败";
//    }
// 
//    /**
//     * 400 - Bad Request
//     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public String handleMethodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response,MethodArgumentNotValidException e) {
//        logger.error("参数验证失败", e);
//        logException(request);
//        BindingResult result = e.getBindingResult();
//        FieldError error = result.getFieldError();
//        String field = error.getField();
//        String code = error.getDefaultMessage();
//        String message = String.format("%s:%s", field, code);
//        return "参数验证失败="+message;
//    }
// 
//    /**
//     * 400 - Bad Request
//     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(BindException.class)
//    public String handleBindException(HttpServletRequest request, HttpServletResponse response,BindException e) {
//        logger.error("参数绑定失败", e);
//        logException(request);
//        BindingResult result = e.getBindingResult();
//        FieldError error = result.getFieldError();
//        String field = error.getField();
//        String code = error.getDefaultMessage();
//        String message = String.format("%s:%s", field, code);
//        return "参数绑定失败="+message;
//    }
// 
// 
//    /**
//     * 400 - Bad Request
//     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(ConstraintViolationException.class)
//    public String handleServiceException(HttpServletRequest request, HttpServletResponse response,ConstraintViolationException e) {
//        logger.error("参数验证失败", e);
//        logException(request);
//        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//        ConstraintViolation<?> violation = violations.iterator().next();
//        String message = violation.getMessage();
//        return "参数验证失败" + message;
//    }
// 
//    /**
//     * 400 - Bad Request
//     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(ValidationException.class)
//    public String handleValidationException(HttpServletRequest request, HttpServletResponse response,ValidationException e) {
//        logger.error("参数验证失败", e);
//        logException(request);
//        return "参数验证失败";
//    }
// 
// 
//    /**
//     * 405 - Method Not Allowed
//     */
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public String handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpServletResponse response,HttpRequestMethodNotSupportedException e) {
//        logger.error("不支持当前请求方法", e);
//        logException(request);
//        return "request_method_not_supported";
//    }
// 
//    /**
//     * 415 - Unsupported Media Type
//     */
//    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//    public String handleHttpMediaTypeNotSupportedException(HttpServletRequest request, HttpServletResponse response,HttpMediaTypeNotSupportedException e) {
//        logger.error("不支持当前媒体类型", e);
//        logException(request);
//        return "content_type_not_supported";
//    }
//    /**
//     * 业务层需要自己声明异常的情况
//     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(ServiceException.class)
//    public String handleServiceException(HttpServletRequest request, HttpServletResponse response,ServiceException e) {
//        logger.error("业务逻辑异常", e);
//        logException(request);
//        return "业务逻辑异常：" + e.getMessage();
//    }
//    /**
//     * 操作数据或库出现异常
//     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(DataDoException.class)
//    public String handleException(HttpServletRequest request, HttpServletResponse response,DataDoException e) {
//        logger.error("操作数据库出现异常:", e);
//        logException(request);
//        return "操作数据库出现异常：字段重复、有外键关联等";
//    }
// 
// 
//    /**
//     * 获取其它异常。包括500
//     * @param e
//     * @return
//     * @throws Exception
//     */
//   @ExceptionHandler(value = Exception.class)
//    public String defaultErrorHandler(Exception e){
//        logger.error("Exception", e);
//        return "其它异常：" + e.getMessage();
// 
//    }
}
