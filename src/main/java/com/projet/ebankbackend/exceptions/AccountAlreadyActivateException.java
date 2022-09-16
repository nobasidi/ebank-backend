package com.projet.ebankbackend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountAlreadyActivateException extends Exception
{
    private ErrorCodes errorcode;
    private String message;
    private Throwable cause;

    public AccountAlreadyActivateException(String message, ErrorCodes code)
    {
        this.errorcode=code;
        this.message=message;
    }

    public AccountAlreadyActivateException(ErrorCodes code)
    {
        this.errorcode=code;
    }
}
