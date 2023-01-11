package com.stf.springbootapi.handler;

import com.stf.springbootapi.exception.InvalidRequestException;
import com.stf.springbootapi.exception.NotFoundException;
import com.stf.springbootapi.resource.ErrorResource;
import com.stf.springbootapi.resource.FieldResource;
import com.stf.springbootapi.resource.InvalidErrorResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 处理资源找不到异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleNotFound(RuntimeException e) {
        ErrorResource errorResource = new ErrorResource(e.getMessage());
        ResponseEntity<Object> result = new ResponseEntity<Object>(errorResource, HttpStatus.NOT_FOUND);
        logger.warn("Return ---------------- {}", result);
        return result;
    }

    /**
     * 处理参数验证失败
     *
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    public ResponseEntity<?> handleInvalidRequest(InvalidErrorResource e) {
        Errors errors = (Errors) e.getErrors();
        List<FieldResource> fieldResources = new ArrayList<>();
        List<FieldError> errorList = errors.getFieldErrors();
        for (FieldError error : errorList) {
            FieldResource filedResource = new FieldResource(error.getObjectName(),
                    error.getField(),
                    error.getCode(),
                    error.getDefaultMessage());
            fieldResources.add(filedResource);
        }
        InvalidErrorResource ier = new InvalidErrorResource(e.getMessage(), fieldResources);
        ResponseEntity<Object> result = new ResponseEntity<Object>(ier, HttpStatus.BAD_REQUEST);
        logger.warn("Return ---------------- {}", result);
        return result;
    }


    /**
     * 处理全局异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(Exception e) {
        logger.error("Error ------- {}", e.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
