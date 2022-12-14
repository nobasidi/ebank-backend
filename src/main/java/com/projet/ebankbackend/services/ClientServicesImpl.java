package com.projet.ebankbackend.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.projet.ebankbackend.dtos.ClientAccountDto;
import com.projet.ebankbackend.dtos.ClientDto;
import com.projet.ebankbackend.dtos.ClientOperationDto;
import com.projet.ebankbackend.dtos.ClientVirementDto;
import com.projet.ebankbackend.dtos.VirementDto;
import com.projet.ebankbackend.entities.Account;
import com.projet.ebankbackend.entities.Client;
import com.projet.ebankbackend.exceptions.AccountNotActivateException;
import com.projet.ebankbackend.exceptions.EntityNotFoundException;
import com.projet.ebankbackend.exceptions.ErrorCodes;
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
    public List<ClientAccountDto> getAccount(String codeclient) throws EntityNotFoundException 
    {
        
        Client client=cr.findByCode(codeclient);
        if (client==null)
            throw new EntityNotFoundException("Le client specifie n existe pas", 
                                                ErrorCodes.CLIENT_NOT_FOUND);
        
        List<ClientAccountDto> info=client.getAccounts().stream()
                                          .map(Mapper::clientAccountToDto)
                                          .collect(Collectors.toList());
        return info;                                  
    }


    @Override
    public Collection<List<ClientOperationDto>> getOperation(String codeclient) throws EntityNotFoundException 
    {   
        Collection<List<ClientOperationDto>> info=new ArrayList<>();
        Client client=cr.findByCode(codeclient);
        if(client==null) 
            throw new EntityNotFoundException("Le client specifie n existe pas", 
                                                    ErrorCodes.CLIENT_NOT_FOUND);
                                                    
        Collection<Account> accounts=client.getAccounts();
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

    @Override
    public List<ClientDto> getAllClients() {
        
        List<ClientDto> clients=cr.findAll()
                                   .stream()
                                   .map(Mapper::clientDto)
                                   .collect(Collectors.toList());
        return clients;                           
    }

}
