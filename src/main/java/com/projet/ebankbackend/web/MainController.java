package com.projet.ebankbackend.web;


import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ebankbackend.dtos.AccountDto;
import com.projet.ebankbackend.dtos.ActiveAccountDto;
import com.projet.ebankbackend.dtos.ClientAccountDto;
import com.projet.ebankbackend.dtos.ClientDto;
import com.projet.ebankbackend.dtos.ClientOperationDto;
import com.projet.ebankbackend.dtos.ClientVirementDto;
import com.projet.ebankbackend.dtos.CreateAccountDto;
import com.projet.ebankbackend.dtos.VirementDto;
import com.projet.ebankbackend.exceptions.AccountNotActivateException;
import com.projet.ebankbackend.exceptions.EntityNotFoundException;
import com.projet.ebankbackend.exceptions.OperationImpossibleException;
import com.projet.ebankbackend.services.ClientServices;
import com.projet.ebankbackend.services.bankservices.BankServices;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MainController 
{
    private ClientServices cs;
    private BankServices bs;

    @GetMapping("/accounts/{client}")
    public List<ClientAccountDto> getAccounts(@PathVariable(name="client") String codeclient)
    {
        return cs.getAccount(codeclient);
    }
    

    @GetMapping("/operations/{client}")
    public Collection<List<ClientOperationDto>> getOperations(@PathVariable(name="client") String codeclient)
    {
        return cs.getOperation(codeclient);
    }

   @PostMapping("/operations/virement")
   public ClientVirementDto virement(@RequestBody VirementDto info) throws OperationImpossibleException, AccountNotActivateException, EntityNotFoundException
    {
        return cs.makeOperation(info);
    }   


    @PostMapping("accounts/createaccount")
    public AccountDto createAccount(@RequestBody CreateAccountDto info) throws EntityNotFoundException
    {
        return bs.createAccount(info);
    }
    
    @PutMapping("accounts/activate/{account}")
    public AccountDto activeAccount(@PathVariable(name="account")String account,
                                    @RequestBody ActiveAccountDto info) throws EntityNotFoundException
    {
        return bs.activeAccount(account, info);
    }

    @GetMapping("/clients")
    public List<ClientDto>  getAllClients()
    {
        return cs.getAllClients();
    }
}
