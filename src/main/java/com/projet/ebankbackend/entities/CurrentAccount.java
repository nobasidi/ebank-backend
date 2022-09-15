package com.projet.ebankbackend.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(value="CURRENT")
@EqualsAndHashCode(callSuper=true)

public class CurrentAccount extends Account
{
    @Column(name="decouvert", nullable=true)
    private Double decouvert;

    public CurrentAccount(Account account)
    {
        super(account.getNumcount(), account.getStatus(), account.getSolde(), 
        account.getDevise(), account.getClient(), account.getOperations());
    }
}
