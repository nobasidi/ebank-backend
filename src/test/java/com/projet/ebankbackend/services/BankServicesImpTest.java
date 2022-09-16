package com.projet.ebankbackend.services;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projet.ebankbackend.dtos.ActiveAccountDto;
import com.projet.ebankbackend.dtos.SimpleOperationDto;
import com.projet.ebankbackend.entities.Account;
import com.projet.ebankbackend.entities.Client;
import com.projet.ebankbackend.entities.CurrentAccount;
import com.projet.ebankbackend.entities.Operations;
import com.projet.ebankbackend.entities.SavingAccount;
import com.projet.ebankbackend.enums.AccountStatus;
import com.projet.ebankbackend.exceptions.AccountAlreadyActivateException;
import com.projet.ebankbackend.exceptions.AccountNotActivateException;
import com.projet.ebankbackend.exceptions.EntityNotFoundException;
import com.projet.ebankbackend.exceptions.OperationImpossibleException;
import com.projet.ebankbackend.repository.AccountRepository;
import com.projet.ebankbackend.repository.ClientRepository;
import com.projet.ebankbackend.repository.OperationsRepository;
import com.projet.ebankbackend.services.bankservices.BankServicesImp;

@ExtendWith(MockitoExtension.class)
public class BankServicesImpTest 
{   
    @Mock
    private ClientRepository cr;

    @Mock
    private AccountRepository ar;

    @Mock
    private OperationsRepository or;

    @InjectMocks
    private BankServicesImp services;



    @Test
    void canDoCreditOperation() throws OperationImpossibleException, AccountNotActivateException, EntityNotFoundException 
    {
        CurrentAccount account=new CurrentAccount();
        account.setNumcount("moncompte");
        account.setClient(new Client());
        account.setDecouvert(200000.00);
        account.setDevise("devise");
        account.setStatus(AccountStatus.ACTIVATED);
        account.setSolde(300000.00);

        doAnswer(invocation->{
            return account;
        }).when(ar).findByNumcount("moncompte");

        Double montant=50_000.00;

        SimpleOperationDto opinfo=new SimpleOperationDto();
        opinfo.setAccounte("moncompte");
        opinfo.setMontant(montant);

        Operations operation=services.credit(opinfo);

        Account op_account=operation.getAccount();

        assertEquals(op_account.getNumcount(), account.getNumcount());

        assertEquals(op_account.getSolde(), account.getSolde());

        verify(or).save(operation);
        
    }

    @Test
    void creditOperationThrowEntityNotFoundExceptionWhenGivingUnexistingAccount() throws AccountNotActivateException, EntityNotFoundException, OperationImpossibleException
    {
        doAnswer(invocation->{
            return null;
        }).when(ar).findByNumcount("numaccount");

        assertThrows(EntityNotFoundException.class, ()->{
            SimpleOperationDto opinfo=new SimpleOperationDto();
            opinfo.setAccounte("numaccount");
            opinfo.setMontant(200_000.00);
                        services.credit(opinfo);
        });

        verify(or, never()).save(any());
    }

    @Test
    void creditOperationThrowAccountNotActivateExceptionWhenGivingNotActivateAccount()
    {
        SavingAccount account=new SavingAccount();
        account.setNumcount("moncompte");
        account.setClient(new Client());
        account.setRate(3.5);
        account.setDevise("devise");
        account.setStatus(AccountStatus.ONCREATE);
        account.setSolde(300000.00);

        doAnswer(invocation->{
            return account;
        }).when(ar).findByNumcount("moncompte");

        assertThrows(AccountNotActivateException.class, ()->{
            SimpleOperationDto opinfo=new SimpleOperationDto();
            opinfo.setAccounte("moncompte");
            opinfo.setMontant(200_000.00);
                    services.credit(opinfo);
        });

        verify(or, never()).save(any());
    }

    @Test
    void canDoDebitOperation() throws OperationImpossibleException, AccountNotActivateException, EntityNotFoundException
    {
        CurrentAccount account=new CurrentAccount();
        account.setNumcount("moncompte");
        account.setClient(new Client());
        account.setDecouvert(200_000.00);
        account.setDevise("devise");
        account.setStatus(AccountStatus.ACTIVATED);
        account.setSolde(500_000.00);

        doAnswer(invocation->{
                return account;
        }).when(ar).findByNumcount("moncompte");

       Double montant=150_000.00;

       SimpleOperationDto opinfo=new SimpleOperationDto();
        opinfo.setAccounte("moncompte");
        opinfo.setMontant(montant);

       Double init_solde=account.getSolde();

       Operations operation=services.debit(opinfo);

       Account op_account=operation.getAccount();

       assertEquals(op_account.getNumcount(), account.getNumcount());

       Double expectedsolde=init_solde-montant;

       assertEquals(op_account.getSolde(), expectedsolde);

       assertEquals(op_account.getSolde(), account.getSolde());

       verify(or).save(operation);
    }

    @Test
    void throwAccountNotActivateExcetionWhenDoDebitOperationOnUnActivateAccount() 
    {   

        CurrentAccount account=new CurrentAccount();
        account.setNumcount("moncompte");
        account.setClient(new Client());
        account.setDecouvert(200000.00);
        account.setDevise("devise");
        account.setStatus(AccountStatus.ONCREATE);
        account.setSolde(300000.00);

        doAnswer(invocation->{
            return account;
        }).when(ar).findByNumcount("moncompte");

        assertThrows(AccountNotActivateException.class, ()->{
            SimpleOperationDto opinfo=new SimpleOperationDto();
            opinfo.setAccounte("moncompte");
            opinfo.setMontant(50_000.00);
            services.debit(opinfo);
        });

        verify(or, never()).save(any());

    }


    @Test
    void throwEntityNotFoundExceptionWhenDoDebitOperationOnUnExistingAccount() 
    {

        doAnswer(invocation->{
            return null;
        }).when(ar).findByNumcount("moncompte");

        assertThrows(EntityNotFoundException.class, ()->{
            SimpleOperationDto opinfo=new SimpleOperationDto();
            opinfo.setAccounte("moncompte");
            opinfo.setMontant(50_0000.00);
            services.debit(opinfo);
        });

        verify(or, never()).save(any());
    }


    @Test
    void throwOperationImpossibleExceptionWhenDecouvertIsNotEnoughToDoDebitOperation() 
    {
        CurrentAccount account=new CurrentAccount();
        account.setNumcount("moncompte");
        account.setClient(new Client());
        account.setDecouvert(200000.00);
        account.setDevise("devise");
        account.setStatus(AccountStatus.ACTIVATED);
        account.setSolde(0.00);

        doAnswer(invocation->{
            return account;
        }).when(ar).findByNumcount("moncompte");

        assertThrows(OperationImpossibleException.class, ()->{
            SimpleOperationDto opinfo=new SimpleOperationDto();
            opinfo.setAccounte("moncompte");
            opinfo.setMontant(300_000.00);
            services.debit(opinfo);
        });

        verify(or, never()).save(any());
    }

    @Test
    void canDoDebitOperationWhenSoldIsNotEnoughButDecouvertIsEnough() throws OperationImpossibleException, AccountNotActivateException, EntityNotFoundException 
    {
        CurrentAccount account=new CurrentAccount();
        account.setNumcount("moncompte");
        account.setClient(new Client());
        account.setDecouvert(200000.00);
        account.setDevise("devise");
        account.setStatus(AccountStatus.ACTIVATED);
        account.setSolde(0.00);

        doAnswer(invocation->{
                return account;
        }).when(ar).findByNumcount("moncompte");

       Double montant=150_000.00;

       SimpleOperationDto opinfo=new SimpleOperationDto();
       opinfo.setAccounte("moncompte");
       opinfo.setMontant(montant);

       Operations operation=services.debit(opinfo);

       Account op_account=operation.getAccount();

       assertEquals(op_account.getNumcount(), account.getNumcount());

       Double expectedsolde=-1*montant;

       assertEquals(op_account.getSolde(), expectedsolde);

       assertEquals(op_account.getSolde(), account.getSolde());

       verify(or).save(operation);
    }


    @Test
    void throwAccountAlreadyActivateExceptionWhenTryToActivateAnActivatedAccount() 
    {
        CurrentAccount account=new CurrentAccount();
        account.setNumcount("moncompte");
        account.setClient(new Client());
        account.setDecouvert(200000.00);
        account.setDevise("devise");
        account.setStatus(AccountStatus.ACTIVATED);
        account.setSolde(0.00);

        doAnswer(invocation->{
            return account;
        }).when(ar).findByNumcount("moncompte");

        assertThrows(AccountAlreadyActivateException.class, ()->{
                services.activeAccount("moncompte", new ActiveAccountDto());
        });

        verify(ar, never()).save(any());
    }
}
