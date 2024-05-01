package com.paulmamai.mybank.controller;


import com.paulmamai.mybank.dto.AccountDto;
import com.paulmamai.mybank.entity.Account;
import com.paulmamai.mybank.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //Add Account API
    @PostMapping

    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //Get Account Api by id

    @GetMapping("/{id}")

    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") Long id){
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);

    }

    //Deposit to account api

    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id,
                                              @RequestBody Map<String, Double> request){

        Double amount = request.get("amount");
        AccountDto accountDto = accountService.deposit(id,amount);
        return ResponseEntity.ok(accountDto);

    }

    //Withdraw from acount api
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
                                               @RequestBody Map<String, Double> request){
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    //Get all acounts api

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts =  accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }


    //delete account api

    @DeleteMapping("/{id}")

    public ResponseEntity<String> deleteAccountById(@PathVariable Long id){
        accountService.deleteAccountById(id);
        return ResponseEntity.ok("Account deleted successfully!");
    }


}
