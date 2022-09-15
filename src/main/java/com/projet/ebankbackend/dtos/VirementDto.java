package com.projet.ebankbackend.dtos;

import lombok.Data;

@Data
public class VirementDto
{

    private String source;
    private String destination;
    private Double montant;
    
}
