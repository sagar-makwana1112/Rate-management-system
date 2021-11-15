package com.rms.handler;

import java.util.Date;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rms.exception.SaveUpdateEntityException;
import com.rms.vo.RestErrorVO;

@Component
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {SaveUpdateEntityException.class})
    public @ResponseBody RestErrorVO handleSaveEntityException(SaveUpdateEntityException ex) {
        return new RestErrorVO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), new Date().toString());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public @ResponseBody RestErrorVO handleEntityNotFoundException(EntityNotFoundException ex) {
        return new RestErrorVO(HttpStatus.NOT_FOUND, ex.getMessage(), new Date().toString());
    }
}
