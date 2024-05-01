package com.paulmamai.mybank.service.impl;

import com.paulmamai.mybank.dto.AccountDto;
import com.paulmamai.mybank.mapper.AccountMapper;
import com.paulmamai.mybank.repository.AccountRepository;
import org.springframework.stereotype.Service;
import com.paulmamai.mybank.entity.Account;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AccountServiceImpl implements com.paulmamai.mybank.service.AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto){

        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount) ;
    }

    @Override
    public AccountDto getAccountById(Long id){
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account doesn't exist!!"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount){
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account doesn't exist!!")); //checks the existence of the account in the database

        double total = account.getBalance() + amount; //add total deposit and already amount in the account
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(account);
        }

    @Override
    public AccountDto withdraw(Long id, double amount){
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account doesn't exist!!"));

        if(account.getBalance() < amount){
            throw new RuntimeException("You do not have sufficient amount!!");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(account -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());
    }


    @Override
    public void deleteAccountById(Long id){
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account doesn't exist!!"));

        accountRepository.deleteById(id);


    }

}


