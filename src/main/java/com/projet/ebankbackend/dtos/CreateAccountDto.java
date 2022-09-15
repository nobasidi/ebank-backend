package com.projet.ebankbackend.dtos;

import lombok.Data;

@Data
public class CreateAccountDto 
{
    private String codeclient;
    private String type;
    private Double value;
}
