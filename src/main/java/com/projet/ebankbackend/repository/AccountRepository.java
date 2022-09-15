package com.projet.ebankbackend.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.ebankbackend.entities.Account;
import com.projet.ebankbackend.entities.Client;
import com.projet.ebankbackend.enums.AccountStatus;

public interface AccountRepository extends JpaRepository<Account, Long>
{
    Collection<Account>  findByStatus(AccountStatus status);
    Collection<Account>  findByClient(Client client);
    Account  findByNumcount(String numcount);
    
}
