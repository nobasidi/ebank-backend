package com.projet.ebankbackend.exceptions;

import lombok.Getter;

public class EntityNotFoundException extends Exception
{   
    @Getter
    private ErrorCodes code;

    @Getter
    private String message;

    @Getter
    private Throwable cause;

    public EntityNotFoundException(ErrorCodes code)
    {
        this.code=code;
    }

    public EntityNotFoundException(String message, ErrorCodes code)
    {
        
        this.code=code;
        this.message=message;
    }

    public EntityNotFoundException(String message, ErrorCodes code, Throwable cause)
    {
        this.message=message;
        this.cause=cause;
        this.code=code;
    }
}
