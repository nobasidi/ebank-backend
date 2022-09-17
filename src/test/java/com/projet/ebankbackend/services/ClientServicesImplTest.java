package com.projet.ebankbackend.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projet.ebankbackend.dtos.ClientAccountDto;
import com.projet.ebankbackend.entities.Account;
import com.projet.ebankbackend.entities.Client;
import com.projet.ebankbackend.exceptions.EntityNotFoundException;
import com.projet.ebankbackend.repository.AccountRepository;
import com.projet.ebankbackend.repository.ClientRepository;
import com.projet.ebankbackend.repository.OperationsRepository;

@ExtendWith(MockitoExtension.class)
public class ClientServicesImplTest 
{

    @Mock
    private ClientRepository cr;

    @Mock
    private AccountRepository ar;

    @Mock
    private OperationsRepository or;

    @Mock
    private Client client;

    @InjectMocks
    private ClientServicesImpl clientservice;


    @Test
    @Disabled
    void getAccountthrowEntityNotFoundExceptionIfClientDoesntExistetes() 
    {
        doAnswer(invocation->{
            return null;
        }).when(cr).findByCode("codeclient");

        assertThrows(EntityNotFoundException.class, ()->{
                clientservice.getAccount("codeclient");
        });

    }

    @Test
    @Disabled
    void getAccountReturnListOfClientAccountIfClientExiste() throws EntityNotFoundException 
    {
        client.setCode("codeclient");
        Account account1=new Account();
        account1.setNumcount("account1");
        Account account2=new Account();
        account2.setNumcount("account2");
        List<Account> accounts=new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        client.setAccounts(accounts);

        doAnswer(invocation->{
            return accounts;
        }).when(client).getAccounts();
        
        
        List<ClientAccountDto> rs=clientservice.getAccount("codeclient");

        assertEquals(rs.get(1).getNumaccount(), account2.getNumcount());
        assertEquals(rs.get(0).getNumaccount(), account1.getNumcount());
       
    }

    @Test
    void testGetOperation() 
    {
        //S il vous qui doit rediger ce teste?????
    }

    @Test
    void testMakeOperation() 
    {
        //Et ce teste aussi on a du retard
    }
}
