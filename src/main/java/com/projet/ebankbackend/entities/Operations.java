package com.projet.ebankbackend.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.projet.ebankbackend.enums.OperationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="operations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Operations extends MainEntity
{   
    @Column(name="codeop", nullable=false, unique=true)
    private String codeop;
    
    @Column(name="dateop", nullable=false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateop;

    @Column(name="montantop", nullable=false)
    private Double amount;

    @Column(name="typeop", nullable=false)
    @Enumerated(EnumType.STRING)
    private OperationType type;

    @ManyToOne
    @JoinColumn(name="num_account", referencedColumnName="numerocompte")
    private Account account;
}
