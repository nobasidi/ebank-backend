package com.projet.ebankbackend.mappers;

import com.projet.ebankbackend.dtos.AccountDto;
import com.projet.ebankbackend.dtos.ClientOperationDto;
import com.projet.ebankbackend.dtos.ClientVirementDto;
import com.projet.ebankbackend.entities.Account;
import com.projet.ebankbackend.entities.Operations;

public class Mapper 
{
    public static ClientVirementDto virementDto(Operations operation)
    {
        ClientVirementDto virementdto=new ClientVirementDto();
        virementdto.setDestinataire(operation.getAccount().getClient().getPrenom()+
                                    " "+operation.getAccount().getClient().getNom());
        virementdto.setDate(operation.getDateop());
        virementdto.setMontant(operation.getAmount());
        return virementdto;
    }

    public static AccountDto accountToDto(Account account)
    {
        AccountDto info=new AccountDto();
        info.setNumcount(account.getNumcount());
        info.setNomclient(account.getClient().getNom());
        info.setPrenomclient(account.getClient().getPrenom());
        info.setDevise(account.getDevise());
        info.setSolde(account.getSolde());
        info.setStatus(account.getStatus());
        return info;
    }

    public static ClientOperationDto operationToDto(Operations operation)
    {
        ClientOperationDto operationdto=new ClientOperationDto();
        operationdto.setDateoperation(operation.getDateop());
        operationdto.setType(operation.getType());
        operationdto.setNumcompte(operation.getAccount().getNumcount());
        operationdto.setNumoperation(operation.getCodeop());
        operationdto.setMontant(operation.getAmount());
        return operationdto;
    }


}
