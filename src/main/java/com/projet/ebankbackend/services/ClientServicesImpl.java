package com.projet.ebankbackend.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.projet.ebankbackend.dtos.ClientAccountDto;
import com.projet.ebankbackend.dtos.ClientOperationDto;
import com.projet.ebankbackend.dtos.ClientVirementDto;
import com.projet.ebankbackend.dtos.VirementDto;
//import com.projet.ebankbackend.dtos.OperationsDto;
import com.projet.ebankbackend.entities.Account;
import com.projet.ebankbackend.entities.Client;
import com.projet.ebankbackend.exceptions.AccountNotActivateException;
import com.projet.ebankbackend.exceptions.EntityNotFoundException;
import com.projet.ebankbackend.exceptions.OperationImpossibleException;
import com.projet.ebankbackend.mappers.Mapper;
import com.projet.ebankbackend.repository.ClientRepository;
import com.projet.ebankbackend.services.bankservices.BankServices;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientServicesImpl implements ClientServices
{

    private ClientRepository cr;
    private BankServices bs;

    @Override
    public List<ClientAccountDto> getAccount(String codeclient) {
        
        Client client=cr.findByCode(codeclient);
        List<ClientAccountDto> info=client.getAccounts().stream()
                                          .map(this::accountToDto)
                                          .collect(Collectors.toList());
        return info;                                  
    }

    private ClientAccountDto accountToDto(Account account)
    {
        ClientAccountDto clientaccountdto=new ClientAccountDto();
        clientaccountdto.setDatecreation(account.getCreatedat());
        clientaccountdto.setNumaccount(account.getNumcount());
        return clientaccountdto;
    }



    @Override
    public Collection<List<ClientOperationDto>> getOperation(String codeclient) 
    {
        Collection<List<ClientOperationDto>> info=new ArrayList<>();
        Collection<Account> accounts=cr.findByCode(codeclient).getAccounts();
        accounts.forEach(account->{
            if(account.getOperations().isEmpty())
                return;
            List<ClientOperationDto>operation=account.getOperations().stream()
                   .map(Mapper::operationToDto)
                   .collect(Collectors.toList());
                   info.add(operation);
        }); 
        return info;
    }

   
    @Override
    public ClientVirementDto makeOperation(VirementDto info) throws OperationImpossibleException, AccountNotActivateException, EntityNotFoundException 
    {
        ClientVirementDto operation=bs.virement(info);
        return operation;
    }

}
