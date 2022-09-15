package com.projet.ebankbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.ebankbackend.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long>
{
    Client findByCode(String code);
    Client findByTelephone(String telephone);
    Client findByEmail(String email);
}
