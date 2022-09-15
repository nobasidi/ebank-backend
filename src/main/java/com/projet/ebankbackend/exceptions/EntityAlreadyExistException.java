package com.projet.ebankbackend.exceptions;

import lombok.Getter;

public class EntityAlreadyExistException extends Exception
{   
    @Getter
    private ErrorCodes code;

    @Getter
    private String message;

    @Getter
    private Throwable cause;

    public EntityAlreadyExistException(ErrorCodes code)
    {
        this.code=code;
    }

    public EntityAlreadyExistException(String message, ErrorCodes code)
    {
        this.message=message;
        this.code=code;

    }

    public EntityAlreadyExistException(String message, ErrorCodes code, Throwable cause)
    {
        this.message=message;
        this.cause=cause;
        this.code=code;
    }
}
