package com.projet.ebankbackend.exceptionhandler;

import com.projet.ebankbackend.exceptions.ErrorCodes;

import lombok.Getter;


@Getter
public class ExceptionDto 
{
    private  ErrorCodes code;
    private  String message;
    private  Throwable cause;

    public ExceptionDto(ErrorCodes code, String message)
    {
        this.code=code;
        this.message=message;
    }

    public  ExceptionDto(ErrorCodes code,  String message, Throwable cause)
    {
        this.code=code;
        this.message=message;
        this.cause=cause;
    }
}
