package com.projet.ebankbackend.exceptions;

import lombok.Getter;

public class AccountNotActivateException extends Exception
{
    @Getter
    private ErrorCodes code;

    @Getter
    private String message;

    @Getter
    private Throwable cause;

    public AccountNotActivateException(ErrorCodes code)
    {
        this.code=code;
    }

    public AccountNotActivateException(String message, ErrorCodes code)
    {
        this.message=message;
        this.code=code;
    }

    public AccountNotActivateException(String message, ErrorCodes code, Throwable cause)
    {
        this.message=message;
        this.cause=cause;
        this.code=code;
    }
}
