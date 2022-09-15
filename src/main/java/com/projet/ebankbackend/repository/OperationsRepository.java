package com.projet.ebankbackend.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.ebankbackend.entities.Operations;

public interface OperationsRepository extends JpaRepository<Operations, Long>
{
    Operations findByCodeop(String codeop);
    Collection<Operations> findByDateop(Date dateop);
}
