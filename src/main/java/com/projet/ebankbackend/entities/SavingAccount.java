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
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(value="SAVING")
@EqualsAndHashCode(callSuper=true)
public class SavingAccount extends Account
{
    @Column(name="tauxinteret", nullable=true)
    private Double rate;

    public SavingAccount(Account account)
    {
        super(account.getNumcount(), account.getStatus(), account.getSolde(), 
        account.getDevise(), account.getClient(), account.getOperations());
    }
}
