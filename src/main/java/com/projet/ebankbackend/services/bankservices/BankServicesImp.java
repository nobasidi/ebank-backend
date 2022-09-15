package com.projet.ebankbackend.services.bankservices;

import java.util.Date;
import java.util.UUID;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projet.ebankbackend.dtos.AccountDto;
import com.projet.ebankbackend.dtos.ActiveAccountDto;
import com.projet.ebankbackend.dtos.ClientVirementDto;
import com.projet.ebankbackend.dtos.CreateAccountDto;
import com.projet.ebankbackend.dtos.SimpleOperationDto;
import com.projet.ebankbackend.dtos.VirementDto;
//import com.projet.ebankbackend.dtos.ClientVirementDto;
import com.projet.ebankbackend.entities.Account;
import com.projet.ebankbackend.entities.Client;
import com.projet.ebankbackend.entities.CurrentAccount;
import com.projet.ebankbackend.entities.Operations;
import com.projet.ebankbackend.entities.SavingAccount;
import com.projet.ebankbackend.enums.AccountStatus;
import com.projet.ebankbackend.enums.OperationType;
import com.projet.ebankbackend.exceptions.AccountNotActivateException;
import com.projet.ebankbackend.exceptions.EntityNotFoundException;
import com.projet.ebankbackend.exceptions.ErrorCodes;
import com.projet.ebankbackend.exceptions.OperationImpossibleException;
import com.projet.ebankbackend.mappers.Mapper;
import com.projet.ebankbackend.repository.AccountRepository;
import com.projet.ebankbackend.repository.ClientRepository;
import com.projet.ebankbackend.repository.OperationsRepository;
import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@AllArgsConstructor
public class BankServicesImp implements BankServices
{

    private AccountRepository ar;
    private OperationsRepository or;
    private ClientRepository cr;


    @Override
    public Operations debit(SimpleOperationDto opinfo) throws OperationImpossibleException, AccountNotActivateException, EntityNotFoundException
    {
        Account account=ar.findByNumcount(opinfo.getAccounte());

        if(account==null)
            throw new EntityNotFoundException("Le compte renseigne n existe pas", 
                                                ErrorCodes.ACCOUNT_NOT_FOUND);

        if(account.getStatus()!=AccountStatus.ACTIVATED)
            throw new AccountNotActivateException("Votre operation de debit ne peut pas aboutir votre compte n est pas encore active pour plus d information veillez contacter votre gestionnaire de compte", 
                                                ErrorCodes.ACCOUNT_NOT_ACTIVATE);

        if(account instanceof CurrentAccount)
        {
            CurrentAccount caccount=(CurrentAccount) account;
            Double solde=caccount.getSolde();

            if(solde<=(-1*caccount.getDecouvert()))
                        throw new OperationImpossibleException("Votre operation de debit ne peut aboutir pour plus d informations veillez contacter votre gestionnaire de compte", 
                                                                ErrorCodes.DEBIT_OPERATION_IMPOSSIBLE);
                                                                
            if(solde<opinfo.getMontant())
            {
                if(solde-opinfo.getMontant()<(-1*caccount.getDecouvert()))
                    throw new OperationImpossibleException("Votre operation de debit ne peut pas aboutir pour plus d information veillez contacter votre gestionnaire de compte", 
                                                        ErrorCodes.DEBIT_OPERATION_IMPOSSIBLE);

                caccount.setSolde(solde-opinfo.getMontant());
                Operations operation=this.saveOperation(UUID.randomUUID().toString(), new Date(), opinfo.getMontant(), OperationType.DEBIT, caccount);
                return operation;                                        
            }

            caccount.setSolde(solde-opinfo.getMontant());
            Operations operation=this.saveOperation(UUID.randomUUID().toString(), new Date(), opinfo.getMontant(), OperationType.DEBIT, caccount);
            return operation;                                                  
        }
        //implement debit for saving account dont forget it (-: ;
        return null;  
    }



    @Override
    public Operations credit(SimpleOperationDto info) throws AccountNotActivateException, EntityNotFoundException, OperationImpossibleException
    {
        Account account=ar.findByNumcount(info.getAccounte());

        if(account==null)
            throw new EntityNotFoundException("Le compte renseigne n existe pas", ErrorCodes.ACCOUNT_NOT_FOUND);
        
        if(account.getStatus()!=AccountStatus.ACTIVATED) 
            throw new AccountNotActivateException("Votre operation de credit ne peut pas aboutir votre compte n est pas encore active pour plus d information veillez contacter votre gestionnaire de compte", ErrorCodes.ACCOUNT_NOT_ACTIVATE);

        account.setSolde(account.getSolde()+info.getMontant());
        Operations operation=this.saveOperation(UUID.randomUUID().toString(), new Date(), info.getMontant(), OperationType.CREDIT, account);
        return operation;   

    }



    @Override
    public ClientVirementDto virement(VirementDto info) throws OperationImpossibleException, AccountNotActivateException, EntityNotFoundException
    {
        SimpleOperationDto simpleinfo=new SimpleOperationDto();
        simpleinfo.setAccounte(info.getSource());
        simpleinfo.setMontant(info.getMontant());
        Operations operation=this.debit(simpleinfo);

        if(operation==null)
            throw new OperationImpossibleException("Votre operation de virement ne peut pas aboutir pour plus d information veillez contacter votre gestionnaire de compte", ErrorCodes.VIREMENT_OPERATION_IMPOSSIBLE);

        simpleinfo.setAccounte(info.getDestination());
        Operations operationinfo=this.credit(simpleinfo);
        return Mapper.virementDto(operationinfo);    
    }


    @Override
    public Operations saveOperation(String code, Date date, double montant, OperationType type, Account account)throws OperationImpossibleException
    {
        if(or.findByCodeop(code)!=null)
            throw new OperationImpossibleException("Operation deja traite", 
                                                    ErrorCodes.OPERATION_ALREADY_EXIST);

        Operations operation=new Operations();
        operation.setCodeop(code);
        operation.setDateop(date);
        operation.setAmount(montant);
        operation.setType(type);
        operation.setAccount(account);
        or.save(operation);
        return operation; 
    }


    @Override
    public AccountDto createAccount(CreateAccountDto info) throws EntityNotFoundException 
    {
        Client client=cr.findByCode(info.getCodeclient());
        if(client==null)
            throw new EntityNotFoundException("Le code client renseigner n existe pas", 
                                                ErrorCodes.CLIENT_NOT_FOUND);
        
        Account account=new Account();                                      
        account.setNumcount(UUID.randomUUID().toString());
        account.setStatus(AccountStatus.CREATED);
        account.setSolde(0.00);
        account.setDevise("AFRI");
        account.setClient(client);

        if(info.getType().equals("CurrentAccount"))   
        {
            CurrentAccount current=new CurrentAccount(account);
            current.setDecouvert(info.getValue());
            return Mapper.accountToDto(ar.save(current));
        } 

        SavingAccount saving=new SavingAccount(account);
        saving.setRate(3.5);
        return Mapper.accountToDto(ar.save(saving));            
    } 



    @Override
    public AccountDto activeAccount(String numaccount, ActiveAccountDto info) throws EntityNotFoundException 
    {
       
        Account account=ar.findByNumcount(numaccount);
        if(account==null)
            throw new EntityNotFoundException("Le compte specifier n existe pas", 
                                                ErrorCodes.ACCOUNT_NOT_FOUND);
        
        if(account.getStatus()==AccountStatus.ACTIVATED)
            //handle account already activate exception  
            ;                                    
        AccountDto active;
        account.setSolde(info.getSolde());
        account.setStatus(AccountStatus.ACTIVATED);
        String message="Votre compte est active avec success";
        if(account instanceof CurrentAccount)
        {
            CurrentAccount current=(CurrentAccount) account;
            current.setDecouvert(info.getValue());
            active=Mapper.accountToDto(ar.save(current));
            active.setMessage(message);
            return active;
        } 
        SavingAccount saving=(SavingAccount) account;
        saving.setRate(info.getValue()); 
        active=Mapper.accountToDto(ar.save(saving));
        active.setMessage(message);
        return active;  
    }
}
