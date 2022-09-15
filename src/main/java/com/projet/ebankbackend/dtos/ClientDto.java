package com.projet.ebankbackend.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class ClientDto 
{
  
    //private String code;
    
    private String nom;

    private String prenom;

    private String adresse;

    private Date datenaiss;

    private String email;

    private String telephone;

}
