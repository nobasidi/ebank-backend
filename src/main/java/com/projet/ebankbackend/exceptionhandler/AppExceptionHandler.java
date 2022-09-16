package com.projet.ebankbackend.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.projet.ebankbackend.exceptions.AccountAlreadyActivateException;
import com.projet.ebankbackend.exceptions.AccountNotActivateException;
import com.projet.ebankbackend.exceptions.EntityAlreadyExistException;
import com.projet.ebankbackend.exceptions.EntityNotFoundException;
import com.projet.ebankbackend.exceptions.OperationImpossibleException;

@RestControllerAdvice
public class AppExceptionHandler 
{

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public ExceptionDto handle(EntityNotFoundException exception, WebRequest request)
    {
        return new ExceptionDto(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(value=HttpStatus.CONFLICT)
    public ExceptionDto handle(EntityAlreadyExistException exception, WebRequest request)
    {
        return new ExceptionDto(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(AccountNotActivateException.class)
    @ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
    public ExceptionDto handle(AccountNotActivateException exception, WebRequest request)
    {
        return new ExceptionDto(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(OperationImpossibleException.class)
    @ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
    public ExceptionDto handle(OperationImpossibleException exception, WebRequest request)
    {
        return new ExceptionDto(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(AccountAlreadyActivateException.class)
    @ResponseStatus(value=HttpStatus.CONFLICT)
    public ExceptionDto handle(AccountAlreadyActivateException exception, WebRequest request)
    {
        return new ExceptionDto(exception.getErrorcode(), exception.getMessage());
    }
}
