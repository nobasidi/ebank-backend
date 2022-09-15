package com.projet.ebankbackend.exceptions;

public enum ErrorCodes 
{
    CLIENT_NOT_FOUND(1000), 
    CLIENT_ALREADY_EXIST(1100),
    ACCOUNT_NOT_FOUND(2000), 
    ACCOUNT_ALREADY_EXIST(2100), 
    ACCOUNT_NOT_ACTIVATE(2200),
    OPERATION_ALREADY_EXIST(3000),
    OPERTION_NOT_FOUND(3100),
    DEBIT_OPERATION_IMPOSSIBLE(3200), 
    CREDIT_OPERATION_IMPOSSIBLE(3300), 
    VIREMENT_OPERATION_IMPOSSIBLE(3400),
    OPERATION_ALREADY_SAVED(3500);

    private int code;
    ErrorCodes(int code)
    {
        this.code=code;
    }

    public int getCode()
    {
        return this.code;
    }
}
