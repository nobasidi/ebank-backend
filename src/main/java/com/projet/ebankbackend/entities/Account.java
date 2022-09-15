package com.projet.ebankbackend.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.projet.ebankbackend.enums.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="comptes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", length=15, discriminatorType=DiscriminatorType.STRING)
@EqualsAndHashCode(callSuper=false)
public class Account extends MainEntity
{

    @Column(name="numerocompte", nullable=false, unique=true)
    private String numcount;

    @Column(name="level", nullable=false)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(name="solde", nullable=false)
    private Double solde;

    @Column(name="devise", nullable=false, columnDefinition="varchar(20)  default 'AFRI'")
    private String devise;

    @ManyToOne
    @JoinColumn(name="code_client", referencedColumnName="identifiant")
    private Client client;

    @OneToMany(mappedBy="account")
    private Collection<Operations> operations;
}
