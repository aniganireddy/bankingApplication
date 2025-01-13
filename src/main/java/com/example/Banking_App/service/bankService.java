package com.example.Banking_App.service;

import com.example.Banking_App.dto.AccountDto;

import java.util.Optional;

public interface bankService {
    AccountDto createbankAccount (AccountDto bankDto);
    AccountDto addAccountbalance ( String partId, double accountBalance);
    Optional<AccountDto> getAccounts(String partId);
}
