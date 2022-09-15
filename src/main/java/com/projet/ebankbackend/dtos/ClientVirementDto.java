package com.projet.ebankbackend.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class ClientVirementDto 
{
    private String Destinataire;
    private Date date;
    private Double montant;
    private final String msg="Operation bien enregistre";
}
