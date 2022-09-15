package com.projet.ebankbackend.exceptions;

import lombok.Getter;

public class OperationImpossibleException extends Exception
{
    @Getter
    private ErrorCodes code;

    @Getter
    private String message;

    @Getter
    Throwable cause;

    public OperationImpossibleException(ErrorCodes code)
    {
        this.code=code;
    }

    public OperationImpossibleException(String message, ErrorCodes code)
    {
        this.message=message;
        this.code=code;
    }

    public OperationImpossibleException(String message, ErrorCodes code, Throwable cause)
    {
        this.message=message;
        this.cause=cause;
        this.code=code;
    }
}
