package com.projet.ebankbackend.dtos;

import com.projet.ebankbackend.enums.AccountStatus;

import lombok.Data;


@Data
public class AccountDto 
{
    
    private String numcount;

    private AccountStatus status;

    private Double solde;

    private String devise;

    private String nomclient;

    private String prenomclient;

    private String message="Votre compte a ete cree avec succes";
}
