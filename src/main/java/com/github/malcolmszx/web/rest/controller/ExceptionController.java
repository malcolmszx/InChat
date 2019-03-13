package com.github.malcolmszx.web.rest.controller;

import com.github.malcolmszx.web.exception.ResourceNotFoundException;
import com.github.malcolmszx.web.rest.HttpContextHolder;
import com.github.malcolmszx.web.rest.HttpResponse;
import com.github.malcolmszx.web.rest.HttpStatus;

public class ExceptionController implements ExceptionHandler {
    /**
     * 处理异常
     * @param e
     */
    @Override
    public void doHandle(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(e instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }
        String errorMessage = e.getCause() == null ? "" : e.getCause().getMessage();
        if(errorMessage == null) {
            errorMessage = e.getMessage();
        }
        HttpResponse response = HttpContextHolder.getResponse();
        response.write(status, errorMessage);
        response.closeChannel();
    }
}
