package com.projet.ebankbackend.dtos;

import java.util.Date;

import com.projet.ebankbackend.enums.OperationType;

import lombok.Data;

@Data
public class ClientOperationDto 
{
    private String numoperation;
    private Date dateoperation;
    private OperationType type;
    private String numcompte;
    private Double montant;
}
