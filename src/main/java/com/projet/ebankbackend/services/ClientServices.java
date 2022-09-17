package com.projet.ebankbackend.services;

import java.util.Collection;
import java.util.List;

import com.projet.ebankbackend.dtos.ClientAccountDto;
import com.projet.ebankbackend.dtos.ClientDto;
import com.projet.ebankbackend.dtos.ClientOperationDto;
import com.projet.ebankbackend.dtos.ClientVirementDto;
import com.projet.ebankbackend.dtos.VirementDto;
//import com.projet.ebankbackend.dtos.OperationsDto;
import com.projet.ebankbackend.exceptions.AccountNotActivateException;
import com.projet.ebankbackend.exceptions.EntityNotFoundException;
import com.projet.ebankbackend.exceptions.OperationImpossibleException;

public interface ClientServices 
{
    List<ClientAccountDto> getAccount(String codeclient) throws EntityNotFoundException;
    Collection<List<ClientOperationDto>> getOperation(String codeClient) throws EntityNotFoundException;
    ClientVirementDto makeOperation(VirementDto info) throws OperationImpossibleException, AccountNotActivateException, EntityNotFoundException;
    List<ClientDto> getAllClients();
}
