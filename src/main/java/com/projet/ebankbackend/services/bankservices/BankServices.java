package com.projet.ebankbackend.services.bankservices;

import java.util.Date;

import com.projet.ebankbackend.dtos.AccountDto;
import com.projet.ebankbackend.dtos.ActiveAccountDto;
import com.projet.ebankbackend.dtos.ClientVirementDto;
import com.projet.ebankbackend.dtos.CreateAccountDto;
import com.projet.ebankbackend.dtos.SimpleOperationDto;
import com.projet.ebankbackend.dtos.VirementDto;
import com.projet.ebankbackend.entities.Account;
import com.projet.ebankbackend.entities.Operations;
import com.projet.ebankbackend.enums.OperationType;
import com.projet.ebankbackend.exceptions.AccountNotActivateException;
import com.projet.ebankbackend.exceptions.EntityAlreadyExistException;
import com.projet.ebankbackend.exceptions.EntityNotFoundException;
import com.projet.ebankbackend.exceptions.OperationImpossibleException;

public interface BankServices 
{
   
    Operations saveOperation(String code, Date date, double montant, OperationType type, Account account)throws EntityAlreadyExistException, OperationImpossibleException;

    Operations debit(SimpleOperationDto info)throws OperationImpossibleException, AccountNotActivateException, EntityNotFoundException;

    Operations credit(SimpleOperationDto info)throws AccountNotActivateException, EntityNotFoundException, OperationImpossibleException;
   
    ClientVirementDto virement(VirementDto nfo)throws OperationImpossibleException, AccountNotActivateException, EntityNotFoundException;

    AccountDto createAccount(CreateAccountDto info) throws EntityNotFoundException;

    AccountDto activeAccount(String numaccount, ActiveAccountDto info)throws EntityNotFoundException;
}
     