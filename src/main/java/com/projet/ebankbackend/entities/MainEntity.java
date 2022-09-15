package com.projet.ebankbackend.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class MainEntity implements Serializable
{   
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Temporal(TemporalType.DATE)
    @Column(name="creerle", nullable=false, updatable=false)
    private Date createdat;

    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    @Column(name="modierle", nullable= false)
    private Date modifiedat;
}
