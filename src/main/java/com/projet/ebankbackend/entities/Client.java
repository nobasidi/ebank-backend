package com.projet.ebankbackend.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="clients")
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Client extends MainEntity
{   
    @Column(name="identifiant", nullable=false, unique=true)
    private String code;
    
    @Column(name="nom", nullable=false)
    private String nom;

    @Column(name="prenom", nullable=false)
    private String prenom;

    @Column(name="addresse", nullable=false)
    private String adresse;

    @Column(name="datenaissance", nullable=false)
    @DateTimeFormat(pattern= "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date datenaiss;

    @Column(name="email", nullable=false, unique=true)
    private String email;

    @Column(name="telephone", nullable=false, unique=true)
    private String telephone;

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Collection<Account> accounts;

}
